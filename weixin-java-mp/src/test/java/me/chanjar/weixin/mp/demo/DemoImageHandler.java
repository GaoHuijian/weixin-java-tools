package me.chanjar.weixin.mp.demo;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.test.TestConstants;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

import com.cico.modules.weixin.common.api.WxConsts;
import com.cico.modules.weixin.common.bean.result.WxMediaUploadResult;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.session.WxSessionManager;

public class DemoImageHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                  WxMpService wxMpService, WxSessionManager sessionManager) {
    try {
      WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService()
        .mediaUpload(WxConsts.MEDIA_IMAGE, TestConstants.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
      WxMpXmlOutImageMessage m
        = WxMpXmlOutMessage
        .IMAGE()
        .mediaId(wxMediaUploadResult.getMediaId())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .build();
      return m;
    } catch (WxErrorException e) {
      e.printStackTrace();
    }

    return null;
  }
}
