package org.example.wechat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private String username;
    private String userid;
    private String password;

    public User(String userName, String userID, String password){
        this.userid = userID;
        this.username = userName;
        this.password = password;
    }

    public User(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", userID='" + userid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
