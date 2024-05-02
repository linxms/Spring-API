package org.example.wechat.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.wechat.commonIO.Message;
import org.example.wechat.service.ExercitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
计算微信加密签名：
    1. 微信签名的三个参数：timestamp，nonce，token
    2. 对数据的属性进行拼接，后进行sha1加密
    3. 加密出来的内容就是signature
隧道号：170255426906
*/
@XStreamAlias("xml")
@RestController
public class WxController {
    //private Exercitation exercitation;

    @Autowired
    private ExercitationService exercitationService;

    @Autowired
    private OfferController offerController;
    @Autowired
    private UserController userController;




//    @Lazy
//    @Autowired
//    private WxController wxController;


    @PostMapping("/")
    public String receiveMessage(HttpServletRequest httpServletRequest) throws IOException {
        //System.out.println("receive message");
        Map<String, String> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();
        Message message = new Message();
        ServletInputStream servletInputStream = httpServletRequest.getInputStream();


        try {
            Document document = saxReader.read(servletInputStream); //遍历
            Element root = document.getRootElement(); // 获取根节点

            List<Element> elements = root.elements();

            for (Element element : elements){
                map.put(element.getName(), element.getStringValue());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
//        System.out.println("has received message");
//        System.out.println(map);

        String[] strings = map.get("Content").split(" ");

        switch (strings[0]){
            case "插入岗位":{
                return offerController.addOffer(map);
            }
            case "删除岗位":{
                return offerController.deleteOffer(map);
            }
            case "修改岗位":{
                return offerController.updateOffer(map);
            }
            case "查询岗位":{
                return offerController.findOffer(map);
            }
            case "创建用户":{
                return userController.addUser(map);
            }
            case "删除用户":{
                return userController.deleteUser(map);
            }
            case "修改用户":{
                return userController.updateUser(map);
            }
            default: return message.TextMsgReturn(map);
        }


    }




}
/*
微信的xml文件结构：

<xml>
  <ToUserName><![CDATA[toUser]]></ToUserName>
  <FromUserName><![CDATA[fromUser]]></FromUserName>
  <CreateTime>1348831860</CreateTime>
  <MsgType><![CDATA[text]]></MsgType>
  <Content><![CDATA[this is a test]]></Content>
  <MsgId>1234567890123456</MsgId>
  <MsgDataId>xxxx</MsgDataId>
  <Idx>xxxx</Idx>
</xml>

回复xml文本消息
<xml>
  <ToUserName><![CDATA[toUser]]></ToUserName>
  <FromUserName><![CDATA[fromUser]]></FromUserName>
  <CreateTime>12345678</CreateTime>
  <MsgType><![CDATA[text]]></MsgType>
  <Content><![CDATA[你好]]></Content>
</xml>
 */