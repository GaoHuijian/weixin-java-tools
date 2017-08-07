package me.chanjar.weixin.mp.util.http.apache;

import me.chanjar.weixin.mp.util.http.MaterialDeleteRequestExecutor;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.http.RequestHttp;
import com.cico.modules.weixin.common.util.http.apache.Utf8ResponseHandler;
import com.cico.modules.weixin.common.util.json.WxGsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class ApacheMaterialDeleteRequestExecutor extends MaterialDeleteRequestExecutor<CloseableHttpClient, HttpHost> {
  public ApacheMaterialDeleteRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public Boolean execute(String uri, String materialId) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
      httpPost.setConfig(config);
    }

    Map<String, String> params = new HashMap<>();
    params.put("media_id", materialId);
    httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      } else {
        return true;
      }
    } finally {
      httpPost.releaseConnection();
    }
  }
}
