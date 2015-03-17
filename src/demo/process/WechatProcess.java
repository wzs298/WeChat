package demo.process;

import demo.entity.MediaUpdataEntity;
import demo.entity.ReceiveXmlEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信xml消息处理流程逻辑类
 * @author pamchen-1
 *
 */
public class WechatProcess {

	/* 用于接收实时更新的token值 */
	private String TOKEN = "";

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN(String TOKEN) {
		this.TOKEN = TOKEN;
	}

	/**
	 * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
	 * @param xml 接收到的微信数据
	 * @return	最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(String xml){
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
		
		/** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
		String result = "";
		if("text".endsWith(xmlEntity.getMsgType())){
			//测试用的语句
//			AppKeyDataEntity app = new AppKeyDataEntity();
//			System.out.println("TOKEN: " +app.getTOKEN());
//			new FormatMassJsonProcess().massNews(true,"2","d3sbVDlk23DScew72OngGWQ", "video");
//			System.out.println("TOKEN: " + this.getTOKEN());
			this.setTOKEN("d2VC4vBvWT-6pgpBc6y7P6bRFZQbX9gS7oUuy1qAA9PktdSqIkkUoaz6OKAY6zcCURNmT4IFTD9w9Q58VjxkEm0dPaadNczfjL6WqdxyDfE");
//			try {
//				JSONObject json = new HttpRequestUtilProcess().send("image", "D:\\11.jpg", this.getTOKEN());
//				System.out.println(json.toString());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//调用图灵接口
			result = new TulingApiProcess().getTulingResult(xmlEntity);
		} else if("image".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatImageXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					xmlEntity.getMediaId());
		} else if("voice".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatVoiceXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					xmlEntity.getMediaId());
		} else if("video".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatVideoXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					xmlEntity.getMediaId(),
					"", "");
		} else if("music".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatMusicXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					"http://121.199.4.61/music/zxmzf.mp3",
					"http://121.199.4.61/music/zxmzf.mp3",
					"","最炫民族风","歌手：凤凰传奇");
		} else if("news".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatNewsXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					2,new String[]{"1","2"},
					new String[]{"11","22"},
					new String[]{"http://discuz.comli.com/weixin/weather/icon/cartoon.jpg","http://d.hiphotos.bdimg.com/wisegame/pic/item/f3529822720e0cf3ac9f1ada0846f21fbe09aaa3.jpg"},
					new String[]{"http://m.cnblogs.com/?u=txw1958","http://m.cnblogs.com/?u=txw1958"});
		} else if("location".endsWith(xmlEntity.getMsgType())){
			result = new FormatXmlProcess().formatTextXmlAnswer(
					xmlEntity.getFromUserName(),
					xmlEntity.getToUserName(),
					"上传位置：纬度 " + xmlEntity.getLocation_Y() + ";经度 " + xmlEntity.getLocation_Y());
		}
//		else if("event".endsWith(xmlEntity.getMsgType())){
//			result = eventMsg(xmlEntity);
//		}

		return result;
	}

	/*
	 * 事件消息处理
	 */
	public String eventMsg(ReceiveXmlEntity re){
		String result = null;
		/*
		 * @param subscribe		订阅
		 * @param unsubscribe	取消订阅
		 * @param LOCATION		地理位置,接收到的getMsgType不属于event
		 * @param CLICK			自定义菜单
		 * @param VIEW			菜单跳转
		 */
		if("subscribe".endsWith(re.getEvent())){
			result = new FormatXmlProcess().formatTextXmlAnswer(
					re.getFromUserName(),
					re.getToUserName(),
					"欢迎订阅\"我的朋友是逗比\"");
		} else if("unsubscribe".endsWith(re.getEvent())){
			result = new FormatXmlProcess().formatTextXmlAnswer(
					re.getFromUserName(),
					re.getToUserName(),
					"您已取消订阅\"我的朋友是逗比\",期待您的再次关注！");
		} else if("LOCATION".endsWith(re.getEvent())){
			result = new FormatXmlProcess().formatTextXmlAnswer(
					re.getFromUserName(),
					re.getToUserName(),
					"上传位置：纬度 " + re.getLocation_Y() + ";经度 " + re.getLocation_Y());
		} else if("CLICK".endsWith(re.getEvent())){
			if("关于我们".endsWith(re.getEventKey())) {
				result = new FormatXmlProcess().formatTextXmlAnswer(
						re.getFromUserName(),
						re.getToUserName(),
						"点击[关于我们]");
			}
		} else if("VIEW".endsWith(re.getEvent())){

		}
		return result;
	}

	/*
	 * 上传图文素材
	 */
	public String updataNews(){
		MediaUpdataEntity[] mue = new MediaUpdataEntity[2];
		for (int i = 0; i < mue.length; i++) {
			mue[i] = new MediaUpdataEntity();
			mue[i].setAuthor("1a" + i);
			mue[i].setContent("1c" + i);
			mue[i].setContent_source_url("1co" + i);
			mue[i].setDigest("1d" + i);
			mue[i].setShow_cover_pic("1s" + i);
			mue[i].setThumb_media_id("1t" + i);
			mue[i].setTitle("1ti" + i);
		}
		String result = new FormatMassJsonProcess().mediaUpdata(mue);
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + this.getTOKEN();
		result = new HttpRequestUtilProcess().HttpRequest(url,result,"POST");
		String type = "";
		String media_id = "";
		try {
			JSONObject json = new JSONObject(result);
			type = json.getString("type");
			media_id = json.getString("media_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//返回上传后得到的media_id
		return media_id;
	}

	/*
	 * 消息群发
	 */
	public void  messageMass(){
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + this.getTOKEN();
		String data = new FormatMassJsonProcess().massNews(true, "", "", "");
		String result = new HttpRequestUtilProcess().HttpRequest(url, data, "POST");
		int errcode = 0;
		int msg_id = 0;//记录群发消息后返回的消息编号
		try {
			JSONObject json = new JSONObject(result);
			errcode = json.getInt("errcode");
			msg_id = json.getInt("msd_id"); //用于删除消息
			if (errcode == 0){
				System.out.println("群发成功");
			} else {
				System.out.println("群发失败，出错代码：" + errcode);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 删除群发消息
	 */
	public void deleMass(){
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + this.getTOKEN();
		JSONObject data = new JSONObject();
		try {
			data.put("msg_id",12321);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
		int errcode = 0;
		try {
			JSONObject json = new JSONObject(result);
			errcode = json.getInt("errcode");
			if (errcode == 0){
				System.out.println("删除成功");
			} else {
				System.out.println("删除失败，错误代码：" + errcode);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 查询群发消息状态
	 */
	public void stateMass(){
		//url
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=" + this.getTOKEN();
		JSONObject data = new JSONObject();
		String result = "";//获取返回的json数据
		String msg_status = "";//提取状态信息
		try {
			data.put("msg_id",12321);
			result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			msg_status = json.getString("msg_status");
			if ("SEND_SUCCESS".equals(msg_status)){
				System.out.println("发送成功");
			} else {
				System.out.println("发送失败");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 预览接口，给指定用户发送消息
	 */
	public void priviewMessage(){
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + this.getTOKEN();
		String data = new FormatMassJsonProcess().massNews(true, "", "", "");
		String result = new HttpRequestUtilProcess().HttpRequest(url, data, "POST");
		int errcode = 0;
		try {
			JSONObject json = new JSONObject(result);
			errcode = json.getInt("errcode");
			if(errcode == 0){
				System.out.println("发送成功");
			} else {
				System.out.println("发送失败，错误代码：" + errcode + ",错误信息：" + errcode);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     * 创建自定义菜单
     */
	public void CreateMenu(){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + getTOKEN();
		String data = "";//从前端获取菜单的数据
		String result = new HttpRequestUtilProcess().HttpRequest(url,data,"POST");
		if(result != null && !"".equals(result)){
			try {
				//将返回的数据转成json格式
				JSONObject obj = new JSONObject(result);
				//提取errmsg字段的数据
				int errcode = obj.getInt("errcode");
				if(errcode == 0){
					System.out.println("自定义菜单创建成功");
				}  else {
					System.out.println("自定义菜单创建出错，错误代码：" + errcode);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/*
     * 获取自定义菜单
     */
	public void getMenu(){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + this.getTOKEN();
		String result = new HttpRequestUtilProcess().HttpRequest(url,"","");
		try {
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("获取自定义菜单出错，错误代码：" + json.getInt("errcode"));
			} else {
				System.out.println("获取自定义菜单成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     * 删除自定义菜单
     */
	public void delMenu(){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + this.getTOKEN();
		String result = new HttpRequestUtilProcess().HttpRequest(url,"","");
		if(result != null && !"".equals(result)){
			try {
				JSONObject obj = new JSONObject(result);
				String errmsg = obj.getString("errmsg");
				if(errmsg == "ok"){
					result = "自定义菜单删除成功";
				} else {
					result = "自定义菜单删除失败，错误指令："+result;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 创建分组
	 * @param token
	 * @param url
	 * @param data
	 */
	public void createGroup(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + this.getTOKEN();
		//数据的请求格式：{"group":{"name":"test"}}
		JSONObject data = new JSONObject();
		JSONObject node = new JSONObject();
		try {
			node.put("name","");//分组名由前端传进来
			data.put("group",node);
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("创建分组出错，错误代码:" + json.getInt("errcode"));
			} else {
				System.out.println("创建分组成功");
			}
			//正确的返回：{"group": {"id": 107,"name": "test"}}
			//错误的返回：{"errcode":40013,"errmsg":"invalid appid"}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     * 查询所有分组
     */
	public void getAllGroup(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=" + this.getTOKEN();
		String result = new HttpRequestUtilProcess().HttpRequest(url, "", "");
		try {
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("查询分组出错，错误代码：" + json.getInt("errcode"));
			} else {
				System.out.println("查询分组成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     *查询用户所在分组
     */
	public void userAtGroup(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=" + this.getTOKEN();
		JSONObject data = new JSONObject();
		try {
			//提交的数据格式：{"openid":"od8XIjsmk6QdVTETa9jLtGWA6KBc"}
			data.put("openid","");
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("查询用户所在分组出错，错误代码：" + json.getInt("errcode"));
			} else {
				System.out.println("查询用户所在分组成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     *修改分组名称
     */
	public void editGroupName(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + this.getTOKEN();
		//{"group":{"id":108,"name":"test2_modify2"}}
		JSONObject data = new JSONObject();
		JSONObject node = new JSONObject();
		try {
			node.put("id" , "");
			node.put("name" , "");
			data.put("group" , node);
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(0 != json.getInt("errcode")){
				System.out.println("修改分组名称出错，错误代码: " + json.getInt("errcode"));
			} else {
				System.out.println("修改分组名称成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     *移动分组
     */
	public void moveGroup(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + this.getTOKEN();
		//{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
		JSONObject data = new JSONObject();
		try {
			data.put("openid", "");
			data.put("to_groupid", 111);
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(json.getInt("errcode") == 0){
				System.out.println("移动分组成功");
			} else {
				System.out.println("移动分组出错，错误代码：" + json.getInt("errcode"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     *批量移动分组
     */
	public void moveGroups(){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=" + this.getTOKEN();
		//{"openid_list":["oDF3iYx0ro3_7jD4HFRDfrjdCM58","oDF3iY9FGSSRHom3B-0w5j4jlEyY"],"to_groupid":108}
		JSONObject data = new JSONObject();
		JSONArray node = new JSONArray();
		try {
			node.put("1");
			node.put("2");
			data.put("openid_list",node);
			data.put("to_groupid", 111);
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(json.getInt("errcode") == 0){
				System.out.println("批量移动分组成功");
			} else {
				System.out.println("批量移动分组出错，错误代码：" + json.getInt("errcode"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     *设置备注名
     */
	public void setUserNoteName(){
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=" + this.getTOKEN();
		//{"openid":"oDF3iY9ffA-hqb2vVvbr7qxf6A0Q","remark":"pangzi"}
		JSONObject data = new JSONObject();
		try {
			data.put("openid", "1231231231");
			data.put("remark", "12312");
			String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
			JSONObject json = new JSONObject(result);
			if(json.getInt("errcode") == 0){
				System.out.println("备注成功");
			} else {
				System.out.println("备注出错，错误代码：" + json.getInt("errcode"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     * 获取用户信息
     * @param nextOpenId    关注者列表串号
     */
	public void getUserMassage(){
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
		url = url + this.getTOKEN() + "&openid=" + "openid" + "&lang=zh_CN";
		String result = new HttpRequestUtilProcess().HttpRequest(url, "", "");
		try {
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("获取用户信息出错，错误代码：" + json.getInt("errcode"));
			} else {
				System.out.println("获取用户信息成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
     * 获取用户列表；
     */
	public void getUserList(){
		//next_openid : 第一个拉取的OPENID，不填默认从头开始拉取
		//当公众号关注者数量超过10000时，可通过填写next_openid的值，从而多次拉取列表的方式来满足需求。
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + this.getTOKEN() + "&next_openid=" + "NEXT_OPENID";
		String result = new HttpRequestUtilProcess().HttpRequest(url, "", "");
		//将返回的数据转成json格式
		try {
			JSONObject json = new JSONObject(result);
			if(json.has("errcode")){
				System.out.println("获取用户列表出错，错误代码：" + json.getInt("errcode"));
			} else {
				System.out.println("获取用户列表成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
