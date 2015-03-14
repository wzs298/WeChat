package demo.process;

import java.util.Date;
/**
 * 封装最终的xml格式结果
 * @author pamchen-1
 *
 */
public class FormatXmlProcess {
	/**
	 * 封装文字类的发送消息，正常
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatTextXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + content + "]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 封装图片类的发送消息，正常
	 * @param to
	 * @param from
	 * @param mediaId
	 * @return
	 */
	public String formatImageXmlAnswer(String to, String from, String mediaId) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[image]]></MsgType>");
		sb.append("<Image>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("</Image>");
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 封装语音类的发送消息，正常
	 * @param to
	 * @param from
	 * @param mediaId
	 * @return
	 */
	public String formatVoiceXmlAnswer(String to, String from, String mediaId) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[voice]]></MsgType>");
		sb.append("<Voice>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("</Voice>");
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 封装视频类的发送消息，有问题
	 * @param to
	 * @param from
	 * @param mediaId
	 * @param title			视频消息标题，非必需项
	 * @param description	视频消息的描述，非必须项
	 * @return
	 */
	public String formatVideoXmlAnswer(String to, String from, String mediaId, String title, String description) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[video]]></MsgType>");
		sb.append("<Video>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("<Title><![CDATA[" + title + "]]></Title>");
		sb.append("<Description><![CDATA[" + description + "]]></Description>");
		sb.append("</Video>");
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 封装音乐类的发送消息,正常
	 * @param to			接收方id
	 * @param from			发送方id
	 * @param musicurl		音乐链接，非必需
	 * @param hqurl			高质量音乐链接，非必需
	 * @param mediaid		缩略图id
	 * @param title			音乐标题，非必需
	 * @param description	音乐描述，非必需
	 * @return
	 */
	public String formatMusicXmlAnswer(String to, String from, String musicurl, String hqurl, String mediaid, String title, String description) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[music]]></MsgType>");
		sb.append("<Music>");
		sb.append("<Title><![CDATA[" + title + "]]></Title>");
		sb.append("<Description><![CDATA[" + description + "]]></Description>");
		sb.append("<MusicUrl><![CDATA[" + musicurl + "]]></MusicUrl>");
		sb.append("<HQMusicUrl><![CDATA[" + hqurl + "]]></HQMusicUrl>");
// ThumbMediaId参数，必须是通过微信认证的服务号才能得到，普通的服务号与订阅号可以忽略该参数，也就是说，在回复给微信服务器的XML中可以不包含ThumbMediaId参数
//		sb.append("<ThumbMediaId><![CDATA[" + mediaid + "]]></ThumbMediaId>");
		sb.append("</Music>");
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 封装图文类的发送消息,正常
	 * @param to			接收方id
	 * @param from			发送方id
	 * @param articlecount	要发送的图文个数
	 * @param title			图文标题，非必须
	 * @param description	图文描述，非必须
	 * @param picurl		图片地址，非必须
	 * @param url			图文跳转地址，非必须
	 * @return
	 */
	public String formatNewsXmlAnswer(String to, String from, int articlecount, String title[], String description[], String picurl[], String url[]) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[" + to + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + from + "]]></FromUserName>");
		sb.append("<CreateTime>" + date.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType><ArticleCount>" + articlecount + "</ArticleCount>");
		sb.append("<Articles>");
		for (int i = 0; i < articlecount; i++){
			sb.append("<item>");
			sb.append("<Title><![CDATA[" + title[i] + "]]></Title>");
			sb.append("<Description><![CDATA[" + description[i] + "]]></Description>");
			sb.append("<PicUrl><![CDATA[" + picurl[i] + "]]></PicUrl>");
			sb.append("<Url><![CDATA[" + url[i] + "]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles></xml>");
		System.out.println(sb.toString());
		return sb.toString();
	}
}
