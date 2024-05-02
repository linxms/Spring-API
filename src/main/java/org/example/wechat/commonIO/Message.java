package org.example.wechat.commonIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

        Message textMessage = new Message();
        textMessage.setContent("您好，欢迎关注本公众号！" + "\n" +
                "如要插入Offer/User，请输入: 插入岗位/创建用户 公司名 岗位 薪水 岗位数量" + "\n" +
                "如要删除Offer/User，请输入: 删除岗位/用户 公司名 岗位 薪水 岗位数量" + "\n" +
                "如要修改Offer/User，请输入: 修改岗位/用户 公司名 岗位 薪水 岗位数量" + "\n" +
                "如要查询Offer，请输入: 查询 公司名 页码 页面数据大小");
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

    public String ActionMsgReturn(int state, Map<String, String> map){
        Message actMessage = new Message();
        actMessage.setCreateTime(System.currentTimeMillis()/1000);
        actMessage.setFromUserName(map.get("ToUserName"));
        actMessage.setToUserName(map.get("FromUserName"));
        actMessage.setMsgType(map.get("MsgType"));

        switch (state){
            case 1:
                actMessage.setContent("插入成功！");
                break;
            case 2:
                actMessage.setContent("删除成功");
                break;
            case 3:
                actMessage.setContent("修改成功");
                break;
            default:
        }
        //System.out.println(textMessage.toString());
        // XStream将java对象转化为xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(Message.class);
        String xml = xStream.toXML(actMessage);

        return xml;
    }

    public String findPageMsg(Map<String, String> map){

        Message textMessage = new Message();
        textMessage.setContent(map.get("Content"));
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

    public String failMsgReturn(Map<String, String> map){
        Message failMessage = new Message();
        failMessage.setCreateTime(System.currentTimeMillis()/1000);
        failMessage.setFromUserName(map.get("ToUserName"));
        failMessage.setToUserName(map.get("FromUserName"));
        failMessage.setMsgType(map.get("MsgType"));
        failMessage.setContent("本次操作失败，请重新输入");

        //System.out.println(textMessage.toString());
        // XStream将java对象转化为xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(Message.class);
        String xml = xStream.toXML(failMessage);

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