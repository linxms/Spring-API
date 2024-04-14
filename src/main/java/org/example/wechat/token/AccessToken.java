package org.example.wechat.token;

public class AccessToken {
    private String token;
    private long expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireIn) {
        this.expireTime = expireIn * 1000 + System.currentTimeMillis();
    }

    public boolean IsExpired(){
        return System.currentTimeMillis() > this.expireTime;
    }
}
