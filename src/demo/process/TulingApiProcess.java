package demo.process;

import demo.entity.ReceiveXmlEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * @author pamchen-1
 *
 */
public class TulingApiProcess {
	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param rex
	 * @return
	 */
	public String getTulingResult(ReceiveXmlEntity rex){
		/** 此处为图灵api接口，参数key需要自己去注册申请
		 *  每个key每天可调用5000次
		 * 317629006 -> 17cb85c501ac5b57fac0078100781a2a
		 * wzs -> ccba87390d46fe2d890109408114c8ce
		 * */
		String apiUrl = "http://www.tuling123.com/openapi/api?key=17cb85c501ac5b57fac0078100781a2a&info=";
		String param = "";
		try {
			param = apiUrl+URLEncoder.encode(rex.getContent(),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} //将参数转为url编码

		/*
		 * 调用公用的http请求
		 */
		String result = "";
		result = new HttpRequestUtilProcess().HttpRequest(param,"","");

		/** 发送httpget请求 */
//		HttpGet request = new HttpGet(param);
//
//		try {
//			HttpResponse response = HttpClients.createDefault().execute(request);
//			if(response.getStatusLine().getStatusCode()==200){
//				result = EntityUtils.toString(response.getEntity());
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		/** 请求失败处理 */
		if(null==result){
			return new FormatXmlProcess().formatTextXmlAnswer(
					rex.getFromUserName(),
					rex.getToUserName(),
					"对不起，你说的话真是太高深了……");
		}

		try {
			JSONObject json = new JSONObject(result);
			if(100000==json.getInt("code")){//文本类消息
				result = json.getString("text");
				result = new FormatXmlProcess().formatTextXmlAnswer(
						rex.getFromUserName(),
						rex.getToUserName(),
						result);
			} else if(200000==json.getInt("code")){//链接类消息
				result = json.getString("text") + "\n点击链接：" + json.getString("url");
				result = new FormatXmlProcess().formatTextXmlAnswer(
						rex.getFromUserName(),
						rex.getToUserName(),
						result);
			} else if(302000==json.getInt("code")){//新闻类消息
				String list = json.getString("list");
				//获取、处理list里面的json数组信息
				JSONArray listnews = new JSONArray(list);
				int size = listnews.length();
				String [] article = new String[size];
				String [] detailurl = new String[size];
				String [] icon = new String[size];
				for(int i = 0; i < size; i++){
					article[i] = listnews.getJSONObject(i).getString("article");
					detailurl[i] = listnews.getJSONObject(i).getString("detailurl");
					icon[i] = listnews.getJSONObject(i).getString("icon");
				}
				//将数据格式化成xml并返回
				result = new FormatXmlProcess().formatNewsXmlAnswer(
						rex.getFromUserName(),
						rex.getToUserName(),
						size,
						article,
						article,
						icon,
						detailurl);
			} else {//未识别的消息处理
				result = json.getString("text");
				result = new FormatXmlProcess().formatTextXmlAnswer(
						rex.getFromUserName(),
						rex.getToUserName(),
						result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}
}
