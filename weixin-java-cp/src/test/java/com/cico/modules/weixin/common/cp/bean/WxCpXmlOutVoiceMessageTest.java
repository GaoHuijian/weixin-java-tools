package com.cico.modules.weixin.common.cp.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cico.modules.weixin.common.cp.bean.WxCpXmlOutMessage;
import com.cico.modules.weixin.common.cp.bean.WxCpXmlOutVoiceMessage;

@Test
public class WxCpXmlOutVoiceMessageTest {

  public void test() {
    WxCpXmlOutVoiceMessage m = new WxCpXmlOutVoiceMessage();
    m.setMediaId("ddfefesfsdfef");
    m.setCreateTime(1122l);
    m.setFromUserName("from");
    m.setToUserName("to");

    String expected = "<xml>"
      + "<ToUserName><![CDATA[to]]></ToUserName>"
      + "<FromUserName><![CDATA[from]]></FromUserName>"
      + "<CreateTime>1122</CreateTime>"
      + "<MsgType><![CDATA[voice]]></MsgType>"
      + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
      + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }

  public void testBuild() {
    WxCpXmlOutVoiceMessage m = WxCpXmlOutMessage.VOICE().mediaId("ddfefesfsdfef").fromUser("from").toUser("to").build();
    String expected = "<xml>"
      + "<ToUserName><![CDATA[to]]></ToUserName>"
      + "<FromUserName><![CDATA[from]]></FromUserName>"
      + "<CreateTime>1122</CreateTime>"
      + "<MsgType><![CDATA[voice]]></MsgType>"
      + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
      + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(
      m
        .toXml()
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", ""),
      expected
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", "")
    );
  }

}
