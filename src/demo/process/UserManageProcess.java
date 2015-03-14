package demo.process;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户管理
 * Created by TNT on 2015/3/12 0012.
 */
public class UserManageProcess {
    /*
     * 创建分组
     * @param token
     * @param url
     * @param data
     */
    public String createGroup(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN
        //{"group":{"name":"test"}}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, data, "POST");
        return result;
    }

    /*
     * 查询所有分组
    */
    public String getAllGroup(String token, String url){
        //https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, "", "");
        return result;
    }

    /*
     *查询用户所在分组
     */
    public String userAtGroup(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN
        //{"openid":"od8XIjsmk6QdVTETa9jLtGWA6KBc"}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, data, "POST");
        return result;
    }

    /*
     *修改分组名称
     */
    public String editGroupName(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN
        //{"group":{"id":108,"name":"test2_modify2"}}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, data, "POST");
        return result;
    }

    /*
     *移动分组
     */
    public String moveGroup(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN
        //{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token,data,"POST");
        return result;
    }

    /*
     *批量移动分组
     */
    public String moveGroups(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN
        //{"openid_list":["oDF3iYx0ro3_7jD4HFRDfrjdCM58","oDF3iY9FGSSRHom3B-0w5j4jlEyY"],"to_groupid":108}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, data, "POST");
        return result;
    }

    /*
     *设置备注名
     */
    public String setUserNoteName(String token, String url, String data){
        //https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN
        //{"openid":"oDF3iY9ffA-hqb2vVvbr7qxf6A0Q","remark":"pangzi"}
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, data, "POST");
        return result;
    }

    /*
     * 获取用户信息
     * @param token
     * @param url
     * @param nextOpenId    关注者列表串号
     */
    public String getUserMassage(String token, String url, String openid){
        //https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        url = url + token + "&openid=" + openid + "&lang=zh_CN";
        String result = new HttpRequestUtilProcess().HttpRequest(url+token, "", "");
        return result;
    }

    /*
     * 获取用户列表,一次最多拉取10000个关注者；
     * 可配合nextOpenId值取多次得到全部用户；
     * nextOpenId不填表示从0开始拉取；
     * @param token
     * @param url
     * @param nextOpenId    关注者列表串号
     */
    public String getUserList(String token, String url, String nextOpenId){
        //https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
        String result = new HttpRequestUtilProcess().HttpRequest(url + token + "&next_openid=" + nextOpenId, "", "");
        //将返回的数据转成json格式
        try {
            JSONObject obj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
