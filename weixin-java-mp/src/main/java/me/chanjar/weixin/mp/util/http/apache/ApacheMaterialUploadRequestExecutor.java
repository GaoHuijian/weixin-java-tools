package me.chanjar.weixin.mp.util.http.apache;

import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.util.http.MaterialUploadRequestExecutor;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.http.RequestHttp;
import com.cico.modules.weixin.common.util.http.apache.Utf8ResponseHandler;
import com.cico.modules.weixin.common.util.json.WxGsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class ApacheMaterialUploadRequestExecutor extends MaterialUploadRequestExecutor<CloseableHttpClient, HttpHost> {
  public ApacheMaterialUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMpMaterialUploadResult execute(String uri, WxMpMaterial material) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      RequestConfig response = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
      httpPost.setConfig(response);
    }

    if (material == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("非法请求，material参数为空").build());
    }

    File file = material.getFile();
    if (file == null || !file.exists()) {
      throw new FileNotFoundException();
    }

    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder
      .addBinaryBody("media", file)
      .setMode(HttpMultipartMode.RFC6532);
    Map<String, String> form = material.getForm();
    if (material.getForm() != null) {
      multipartEntityBuilder.addTextBody("description", WxGsonBuilder.create().toJson(form));
    }

    httpPost.setEntity(multipartEntityBuilder.build());
    httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      } else {
        return WxMpMaterialUploadResult.fromJson(responseContent);
      }
    } finally {
      httpPost.releaseConnection();
    }
  }
}
