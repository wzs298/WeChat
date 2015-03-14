package demo.entity;

/**
 * 本实体类存放各种请求的URL
 * Created by TNT on 2015/3/11 0011.
 */
public class RequestUrlEntity {
    private String URLNAME; //连接名字
    private String URL;     //链接地址

    public String getURLNAME() {
        return URLNAME;
    }

    public void setURLNAME(String URLNAME) {
        this.URLNAME = URLNAME;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
