package com.cico.modules.weixin.common.util.http.okhttp;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.bean.result.WxMediaUploadResult;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.http.MediaUploadRequestExecutor;
import com.cico.modules.weixin.common.util.http.RequestHttp;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class OkHttpMediaUploadRequestExecutor extends MediaUploadRequestExecutor<ConnectionPool, OkHttpProxyInfo> {

  public OkHttpMediaUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMediaUploadResult execute(String uri, File file) throws WxErrorException, IOException {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectionPool(requestHttp.getRequestHttpClient());
    //设置代理
    if (requestHttp.getRequestHttpProxy() != null) {
      clientBuilder.proxy(requestHttp.getRequestHttpProxy().getProxy());
    }
    //设置授权
    clientBuilder.authenticator(new Authenticator() {
      @Override
      public Request authenticate(Route route, Response response) throws IOException {
        String credential = Credentials.basic(requestHttp.getRequestHttpProxy().getProxyUsername(), requestHttp.getRequestHttpProxy().getProxyPassword());
        return response.request().newBuilder()
          .header("Authorization", credential)
          .build();
      }
    });
    //得到httpClient
    OkHttpClient client = clientBuilder.build();

    RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    RequestBody body = new MultipartBody.Builder().addFormDataPart("media", null, fileBody).build();
    Request request = new Request.Builder().url(uri).post(body).build();

    Response response = client.newCall(request).execute();
    String responseContent = response.body().string();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return WxMediaUploadResult.fromJson(responseContent);
  }

}
