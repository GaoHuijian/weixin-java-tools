package com.cico.modules.weixin.common.cp.message;

import java.util.Map;

import com.cico.modules.weixin.common.cp.api.WxCpService;
import com.cico.modules.weixin.common.cp.bean.WxCpXmlMessage;
import com.cico.modules.weixin.common.cp.bean.WxCpXmlOutMessage;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.session.WxSessionManager;

/**
 * 处理微信推送消息的处理器接口
 *
 * @author Daniel Qian
 */
public interface WxCpMessageHandler {

  /**
   * @param wxMessage
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param wxCpService
   * @param sessionManager
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                           Map<String, Object> context,
                           WxCpService wxCpService,
                           WxSessionManager sessionManager) throws WxErrorException;

}
