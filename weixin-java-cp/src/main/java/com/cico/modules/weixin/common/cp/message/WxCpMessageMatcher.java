package com.cico.modules.weixin.common.cp.message;

import com.cico.modules.weixin.common.cp.bean.WxCpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxCpMessageMatcher {

  /**
   * 消息是否匹配某种模式
   */
  boolean match(WxCpXmlMessage message);

}
