package org.example.wechat.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.wechat.commonIO.Message;
import org.example.wechat.entity.User;
import org.example.wechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@XStreamAlias("xml")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public String addUser(Map<String, String> map){
        int state = 1;
        Message message1 = new Message();
        String[] strings = map.get("Content").split(" ");

        System.out.println(strings[2]);
        if (strings[1] == null || strings[2] == null ){
            throw new RuntimeException("输入不能有空");
        }
        User user = new User(strings[1], map.get("FromUserName"), strings[2]);
        System.out.println(user.toString());
        try {
            userService.addUser(user);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Map<String, String> map){

        int state = 2;
        Message message1 = new Message();
        String userid = map.get("FromUserName");
        String[] strings = map.get("Content").split(" ");
//        System.out.println(userid);
        User user = new User(strings[1], userid, strings[2]);

        Map<String, Object> deleMap = new HashMap<>();
        deleMap.put("username", strings[1]);
        deleMap.put("userid", userid);
        deleMap.put("password", strings[2]);
        try {
            userService.delUser(deleMap);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(Map<String, String>map){
        int state = 3;
        Message message1 = new Message();
        String userid = map.get("FromUserName");
        String[] strings = map.get("Content").split(" ");
//        System.out.println(userid);
        User user = new User(strings[1], userid, strings[2]);
        try {
            userService.updateUser(user);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }

    }
}
