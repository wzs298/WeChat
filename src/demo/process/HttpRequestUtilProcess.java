package demo.process;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TNT on 2015/3/11 0011.
 * HTTP请求数据的公共类
 */
public class HttpRequestUtilProcess {
    public String HttpRequest(String reqUrl, String data, String mothod){
        String result = "";
        try {
            URL url = new URL(reqUrl);
            URLConnection Conn = url.openConnection();
            HttpURLConnection httpUrlConn = (HttpURLConnection) Conn;

            //提交POST请求的数据
            if(mothod == "POST") {
                httpUrlConn.setDoOutput(true);
                httpUrlConn.setRequestMethod("POST");
                OutputStreamWriter out = new OutputStreamWriter(httpUrlConn.getOutputStream(), "UTF-8");
                out.write(data);
                out.flush();
                out.close();
            }

            //获取返回的数据
            InputStream inResultStr = httpUrlConn.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inResultStr, "UTF-8"));

            String lines = "";
            while ((lines = buffer.readLine()) != null) {
                result += lines;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
