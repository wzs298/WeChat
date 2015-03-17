package demo.process;

import demo.entity.MediaUpdataEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息群发
 * Created by TNT on 2015/3/12 0012.
 */
public class FormatMassJsonProcess {
    /*
     * 上传、下载图文消息
     */
    public String mediaUpdata(MediaUpdataEntity[] ob){
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();

        try {
            int size = ob.length;
            for(int i = 0; i < size; i++){
                JSONObject node = new JSONObject();
                node.put("thumb_media_id", ob[i].getThumb_media_id());
                node.put("author", ob[i].getAuthor());
                node.put("title", ob[i].getTitle());
                node.put("content_source_url", ob[i].getContent_source_url());
                node.put("content", ob[i].getContent());
                node.put("digest", ob[i].getDigest());
                node.put("show_cover_pic", ob[i].getShow_cover_pic());
                ja.put(node);
            }

            json.put("articles",ja);            //图文消息，一个图文消息支持1到10条图文
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("updata:" + json.toString());
        return json.toString();
    }

    /*
     * 群发消息,类型：文本、图文、语音、图片、视频
     * @param bl            用于设定是否向全部用户发送
     * @param group_id      群发到的分组的group_id
     * @param media_id      用于群发的消息的media_id；如果是发送文本，则media_id为文本内容
     * @param submitType    群发类型
     * @return
     */
    public String massNews(boolean bl, String group_id, String media_id, String submitType){
        if (!"text".equals(submitType) ||
            !"mpnews".equals(submitType) ||
            !"voice".equals(submitType) ||
            !"image".equals(submitType) ||
            !"mpvideo".equals(submitType)){
            return "";
        }

        JSONObject json = new JSONObject();
        JSONObject filternode = new JSONObject();
        JSONObject mpnewsnode = new JSONObject();
        try {
            filternode.put("is_to_all", false);
            filternode.put("group_id", group_id);

            if("text".equals(submitType)){
                mpnewsnode.put("content",media_id);
            } else {
                mpnewsnode.put("media_id", media_id);
            }
            json.put("filter",filternode);
            json.put(submitType,mpnewsnode);
            json.put("msgtype",submitType);

            System.out.println("jsonObject:" + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  json.toString();
    }

    /*
     * 预览接口，给指定ID发送消息,类型：文本、图文、语音、图片、视频
     * @param bl            用于设定是否向全部用户发送
     * @param group_id      群发到的分组的group_id
     * @param media_id      用于群发的消息的media_id；如果是发送文本，则media_id为文本内容
     * @param submitType    群发类型
     * @return
     */
    public String previewMessage(boolean bl, String touser, String media_id, String submitType){
        if (!"text".equals(submitType) ||
            !"mpnews".equals(submitType) ||
            !"voice".equals(submitType) ||
            !"image".equals(submitType) ||
            !"mpvideo".equals(submitType)){
            return "";
        }

        JSONObject json = new JSONObject();
        JSONObject node = new JSONObject();
        try {
            if ("text".equals(submitType)){
                node.put("content", media_id);
            } else {
                node.put("media_id",media_id);
            }
            json.put("touser",touser);
            json.put(submitType,node);
            json.put("msgtype",submitType);
            System.out.println("jsonObject:" + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  json.toString();
    }
}
