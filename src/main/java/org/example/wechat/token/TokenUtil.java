package org.example.wechat.token;

import net.sf.json.JSONObject;
import org.example.wechat.Util.HttpUtil;

import static org.example.wechat.Util.WordUtil.doGet;

public class TokenUtil {


    private static final String appID = "wx5b320762e1d2d219";
    private static final String appSecret = "e72032147d72c75275a726ce00fe970e";
    private static AccessToken accessToken = new AccessToken();

    public static void main(String[] args){
        //getAccessToken();
        System.out.println(getAccessToken());
        System.out.println(getAccessToken());
    }
    private static void getToken(){
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appID,
                appSecret);
        String token_result = HttpUtil.doGet(url);

        // System.out.println(token_result);
        JSONObject jsonObject = JSONObject.fromObject(token_result);
        String token = jsonObject.getString("access_token");
        long expireIn = jsonObject.getLong("expires_in");

        accessToken.setToken(token);
        accessToken.setExpireTime(expireIn);


        System.out.println(token_result);

    }

    /**
     * 获取能使用的AccessToken
     * @return
     */
    public static String getAccessToken(){
        if (accessToken == null || accessToken.IsExpired()){
            getToken();
        }
        return accessToken.getToken();
    }

}
