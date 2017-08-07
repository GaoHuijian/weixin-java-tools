package com.cico.modules.weixin.common.api;

import com.cico.modules.weixin.common.exception.WxErrorException;

/**
 * WxErrorException处理器
 */
public interface WxErrorExceptionHandler {

  void handle(WxErrorException e);

}
