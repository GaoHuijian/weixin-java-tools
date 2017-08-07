package cn.binarywang.wx.miniapp.message;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;

import java.util.Map;

import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.session.WxSessionManager;

/**
 * 处理小程序推送消息的处理器接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxMaMessageHandler {

  void handle(WxMaMessage message, Map<String, Object> context,
              WxMaService service, WxSessionManager sessionManager) throws WxErrorException;

}
