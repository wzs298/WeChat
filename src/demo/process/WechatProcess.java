package demo.process;

import demo.entity.AppKeyDataEntity;
import demo.entity.MediaUpdataEntity;
import demo.entity.ReceiveXmlEntity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信xml消息处理流程逻辑类
 * @author pamchen-1
 *
 */
public class WechatProcess {
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
			AppKeyDataEntity app = new AppKeyDataEntity();
			System.out.println("TOKEN: " +app.getTOKEN());
//			new FormatMassJsonProcess().massNews(true,"2","d3sbVDlk23DScew72OngGWQ", "video");

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
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";
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
		AppKeyDataEntity app = new AppKeyDataEntity();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + app.getTOKEN();
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
		AppKeyDataEntity app = new AppKeyDataEntity();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + app.getTOKEN();
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
		AppKeyDataEntity app = new AppKeyDataEntity();
		//url后期需要从数据库获取
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=" + app.getTOKEN();
		JSONObject data = new JSONObject();
		try {
			data.put("msg_id",12321);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String result = new HttpRequestUtilProcess().HttpRequest(url, data.toString(), "POST");
		String msg_status = "";
		try {
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
	public  void priviewMessage(){
		AppKeyDataEntity app = new AppKeyDataEntity();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + app.getTOKEN();
		String data = new FormatMassJsonProcess().massNews(true, "", "", "");
		String result = new HttpRequestUtilProcess().HttpRequest(url, data, "POST");
		String errmsg = "";
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
}
