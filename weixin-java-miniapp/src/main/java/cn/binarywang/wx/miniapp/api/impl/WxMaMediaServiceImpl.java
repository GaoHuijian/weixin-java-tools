package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaMediaService;
import cn.binarywang.wx.miniapp.api.WxMaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.bean.result.WxMediaUploadResult;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.fs.FileUtils;
import com.cico.modules.weixin.common.util.http.MediaDownloadRequestExecutor;
import com.cico.modules.weixin.common.util.http.MediaUploadRequestExecutor;
import com.cico.modules.weixin.common.util.http.RequestExecutor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaMediaServiceImpl implements WxMaMediaService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private WxMaService wxMaService;

  public WxMaMediaServiceImpl(WxMaService wxMaService) {
    this.wxMaService = wxMaService;
  }

  @Override
  public WxMediaUploadResult uploadMedia(String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
    try {
      return this.uploadMedia(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    } catch (IOException e) {
      e.printStackTrace();
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build());
    }
  }

  @Override
  public WxMediaUploadResult uploadMedia(String mediaType, File file) throws WxErrorException {
    String url = String.format(MEDIA_UPLOAD_URL, mediaType);
    return this.wxMaService.execute(MediaUploadRequestExecutor.create(this.wxMaService.getRequestHttp()), url, file);
  }

  @Override
  public File getMedia(String mediaId) throws WxErrorException {
    try {
      RequestExecutor<File, String> executor = MediaDownloadRequestExecutor
        .create(this.wxMaService.getRequestHttp(), Files.createTempDirectory("wxma").toFile());
      return this.wxMaService.execute(executor, MEDIA_GET_URL, "media_id=" + mediaId);
    } catch (IOException e) {
      this.log.error(e.getMessage(), e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build());
    }
  }

}
