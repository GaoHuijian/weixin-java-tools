package com.cico.modules.weixin.common.cp.message;

import java.util.Map;

import com.cico.modules.weixin.common.cp.api.WxCpService;
import com.cico.modules.weixin.common.cp.bean.WxCpXmlMessage;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.session.WxSessionManager;

/**
 * 微信消息拦截器，可以用来做验证
 *
 * @author Daniel Qian
 */
public interface WxCpMessageInterceptor {

  /**
   * 拦截微信消息
   *
   * @param wxMessage
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param wxCpService
   * @param sessionManager
   * @return true代表OK，false代表不OK
   */
  boolean intercept(WxCpXmlMessage wxMessage,
                    Map<String, Object> context,
                    WxCpService wxCpService,
                    WxSessionManager sessionManager) throws WxErrorException;

}
