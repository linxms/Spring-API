package org.example.wechat.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.wechat.commonIO.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    int state = 0;
    Message message = new Message();
    @GetMapping("/")
    public String check(String signature,String timestamp,String nonce,String echostr) {
        //（1）将token、timestamp、nonce三个参数进行字典序排序
        String token = "java";
        List<String> list = (List<String>) Arrays.asList(token, timestamp, nonce);
        //排序
        Collections.sort(list);
        //（2）将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
        }
        //加密
        try {
            MessageDigest instance = MessageDigest.getInstance("sha1");
            //使用sha1进行加密，获得byte数组
            byte[] digest = instance.digest(stringBuilder.toString().getBytes());
            StringBuilder sum = new StringBuilder();
            for (byte b : digest) {
                sum.append(Integer.toHexString((b >> 4) & 15));
                sum.append(Integer.toHexString(b & 15));
            }
            System.out.println("signature:" + signature);
            System.out.println("sum:" + sum);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //（3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return echostr;
    }
//    @GetMapping("/hello")
//    public String hello(){
//        return "hello world";
//    }

    @PostMapping("/")
    public String receiveMessage(HttpServletRequest httpServletRequest) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();
        Message message1 = new Message();
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
        //System.out.println("has received message");
        System.out.println(map);
        String message = message1.WordMsgReturn(map);
        //System.out.println(message);
        return message;
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