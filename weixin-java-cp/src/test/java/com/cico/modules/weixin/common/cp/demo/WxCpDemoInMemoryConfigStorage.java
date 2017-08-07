package com.cico.modules.weixin.common.cp.demo;

import com.cico.modules.weixin.common.cp.config.WxCpInMemoryConfigStorage;
import com.cico.modules.weixin.common.util.xml.XStreamInitializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XStreamAlias("xml")
class WxCpDemoInMemoryConfigStorage extends WxCpInMemoryConfigStorage {

  public static WxCpDemoInMemoryConfigStorage fromXml(InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxCpDemoInMemoryConfigStorage.class);
    return (WxCpDemoInMemoryConfigStorage) xstream.fromXML(is);
  }

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appidOrCorpid=" + this.corpId + ", corpSecret=" + this.corpSecret + ", accessToken=" + this.accessToken
      + ", expiresTime=" + this.expiresTime + ", token=" + this.token + ", aesKey=" + this.aesKey + "]";
  }

}
