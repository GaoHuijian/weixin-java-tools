package cn.binarywang.wx.miniapp.util.http;

import cn.binarywang.wx.miniapp.bean.WxMaQrcode;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.fs.FileUtils;
import com.cico.modules.weixin.common.util.http.RequestExecutor;
import com.cico.modules.weixin.common.util.http.RequestHttp;
import com.cico.modules.weixin.common.util.http.apache.InputStreamResponseHandler;
import com.cico.modules.weixin.common.util.http.apache.Utf8ResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, WxMaQrcode> {
  protected RequestHttp<CloseableHttpClient, HttpHost> requestHttp;

  public QrCodeRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public File execute(String uri, WxMaQrcode ticket) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      httpPost
        .setConfig(RequestConfig.custom()
          .setProxy(requestHttp.getRequestHttpProxy())
          .build()
        );
    }
    httpPost.setEntity(new StringEntity(ticket.toString()));

    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
         InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0
        && ContentType.TEXT_PLAIN.getMimeType().equals(contentTypeHeader[0].getValue())) {
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        throw new WxErrorException(WxError.fromJson(responseContent));
      }

      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    } finally {
      httpPost.releaseConnection();
    }
  }
}
