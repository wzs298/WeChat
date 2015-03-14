package demo.process;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 菜单的控制
 * Created by TNT on 2015/3/11 0011.
 */
public class MenuOperationProcess {
    /*
     * 创建自定义菜单
     * @param token 验证的密钥
     * @param url   请求的地址
     * @param data  菜单的数据
     * @return
     */
    public String CreateMenu(String token,String url, String data){
        String result = new HttpRequestUtilProcess().HttpRequest(url+token,data,"POST");
        if(result != null && !"".equals(result)){
            try {
                //将返回的数据转成json格式
                JSONObject obj = new JSONObject(result);
                //提取errmsg字段的数据
                int errcode = obj.getInt("errcode");
                if(errcode == 0){
                    result = "自定义菜单创建成功";
                } else if(errcode == -1){
                    result = "系统繁忙，稍候再试";
                } else {
                    result = "自定义菜单创建失败，错误指令："+result;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 获取自定义菜单
     * @param token 验证的密钥
     * @param url   请求的地址
     * @return
     */
    public String getMenu(String token,String url){
        String result = new HttpRequestUtilProcess().HttpRequest(url+token,"","");
        if(result == null || "".equals(result)){
            result = "自定义菜单为空";
        }
        return result;
    }

    /*
     * 删除自定义菜单
     * @param token 验证的密钥
     * @param url   请求的地址
     * @return
     */
    public String delMenu(String token,String url){
        String result = new HttpRequestUtilProcess().HttpRequest(url+token,"","");
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
        return result;
    }
}
