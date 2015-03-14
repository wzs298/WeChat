package demo.entity;

/**
 * 应用密钥的实体类（放到数据库）
 * Created by TNT on 2015/3/11 0011.
 */
public class AppKeyDataEntity {
    private String APPID;       //应用ID
    private String APPSECRET;   //应用密钥
    private String APPNAME;     //公众号名字
    private String TOKEN;       //接口凭证

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getAPPNAME() {
        return APPNAME;
    }

    public void setAPPNAME(String APPNAME) {
        this.APPNAME = APPNAME;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getAPPSECRET() {
        return APPSECRET;
    }

    public void setAPPSECRET(String APPSECRET) {
        this.APPSECRET = APPSECRET;
    }
}
