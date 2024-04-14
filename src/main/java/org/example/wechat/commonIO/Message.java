package org.example.wechat.commonIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import org.example.wechat.Util.WordUtil;
import org.example.wechat.web.WxController;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


@Data
@AllArgsConstructor
@XStreamAlias("xml")
public class Message<T> {
    private Map<String, String> map;

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    // Map map =
    @Getter
    @Setter
    private String ToUserName;
    @Setter
    private String FromUserName;
    @Getter
    @Setter
    private long CreateTime;
    @Getter
    @Setter
    private String MsgType;
    @Getter
    @Setter
    private String Content;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "map=" + map +
                ", ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }

    public String TextMsgReturn(Map<String, String> map){

//        String toUserName = map.get("FromUserName");
//        String fromUserName = map.get("ToUserName");
//        String msgType = map.get("MsgType");
//        long createTime = System.currentTimeMillis()/1000;
//        String content = "欢迎关注本公众号";


        Message textMessage = new Message();
        textMessage.setContent("您好，欢迎关注本公众号！");
        textMessage.setCreateTime(System.currentTimeMillis()/1000);
        textMessage.setFromUserName(map.get("ToUserName"));
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setMsgType(map.get("MsgType"));

        //System.out.println(textMessage.toString());
        // XStream将java对象转化为xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(Message.class);
        String xml = xStream.toXML(textMessage);
        return xml;
    }


    public String WordMsgReturn(Map<String, String> map){

//        String toUserName = map.get("FromUserName");
//        String fromUserName = map.get("ToUserName");
//        String msgType = map.get("MsgType");
//        long createTime = System.currentTimeMillis()/1000;
//        String content = "欢迎关注本公众号";


        Message textMessage = new Message();
        textMessage.setContent(WordUtil.getWord(map.get("Content")));
        textMessage.setCreateTime(System.currentTimeMillis()/1000);
        textMessage.setFromUserName(map.get("ToUserName"));
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setMsgType(map.get("MsgType"));

        //System.out.println(textMessage.toString());
        // XStream将java对象转化为xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(Message.class);
        String xml = xStream.toXML(textMessage);
        return xml;
    }


}

/*
回复xml文本消息
<xml>
  <ToUserName><![CDATA[toUser]]></ToUserName>
  <FromUserName><![CDATA[fromUser]]></FromUserName>
  <CreateTime>12345678</CreateTime>
  <MsgType><![CDATA[text]]></MsgType>
  <Content><![CDATA[你好]]></Content>
</xml>
 */