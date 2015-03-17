package demo.servlet;

import demo.entity.AppKeyDataEntity;
import demo.process.HttpRequestUtilProcess;
import demo.process.WechatProcess;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 微信服务端收发消息接口
 * 
 * @author pamchen-1
 * 
 */
public class WechatServlet extends HttpServlet {

	static String ACCESS_TOKEN = "";
	public void init(ServletConfig config) throws ServletException {
		String APPID = "wx709923cfd11f614e";//应用ID
		String APPSECRET = "e1c5de479029d6827fe2782b81172923";//应用密钥
		int EXPIRES = 7200;//定时器时间，单位：秒
		final String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
		String createUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + ACCESS_TOKEN;
		String getMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + ACCESS_TOKEN;
		String delMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + ACCESS_TOKEN;

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				//获取ACCESS_TOKEN,其有效期为7200秒
				String access_token = new HttpRequestUtilProcess().HttpRequest(getTokenUrl,"","");
				if(access_token != null && !"".equals(access_token)){
					try {
						JSONObject obj = new JSONObject(access_token);
						ACCESS_TOKEN = obj.getString("access_token");
						AppKeyDataEntity app = new AppKeyDataEntity();
						app.setTOKEN(ACCESS_TOKEN);
						System.out.println(new Date() + ":" +ACCESS_TOKEN);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("获取ACCESS_TOKEN失败");
				}
			}
		}, 1000, EXPIRES*1000);

	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		/** 读取接收到的xml消息 */
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();//获取POST数据，取得数据是二进制
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");//字节流通向字符流的桥梁，一次读取一个一个字符，以文本格式输出，可以指定编码格式
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		//逐行读取文件
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString();	//次即为接收到微信端发送过来的xml数据

		String result = "";
		/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 1) {
			result = echostr;
		} else {
			//正常的微信处理流程
			result = new WechatProcess().processWechatMag(xml);
		}

		try {
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
