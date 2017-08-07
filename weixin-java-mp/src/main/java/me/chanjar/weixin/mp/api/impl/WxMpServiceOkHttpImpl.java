package me.chanjar.weixin.mp.api.impl;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import com.cico.modules.weixin.common.bean.WxAccessToken;
import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.exception.WxErrorException;
import com.cico.modules.weixin.common.util.http.HttpType;
import com.cico.modules.weixin.common.util.http.okhttp.OkHttpProxyInfo;

public class WxMpServiceOkHttpImpl extends AbstractWxMpServiceImpl<ConnectionPool, OkHttpProxyInfo> {
  private ConnectionPool httpClient;
  private OkHttpProxyInfo httpProxy;

  @Override
  public ConnectionPool getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public OkHttpProxyInfo getRequestHttpProxy() {
    return httpProxy;
  }

  @Override
  public HttpType getRequestType() {
    return HttpType.OK_HTTP;
  }

  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
    try {
      lock.lock();

      if (forceRefresh) {
        this.getWxMpConfigStorage().expireAccessToken();
      }

      if (this.getWxMpConfigStorage().isAccessTokenExpired()) {
        String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL,
          this.getWxMpConfigStorage().getAppId(), this.getWxMpConfigStorage().getSecret());

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectionPool(httpClient);
        //设置代理
        if (httpProxy != null) {
          clientBuilder.proxy(getRequestHttpProxy().getProxy());
        }
        //设置授权
        clientBuilder.authenticator(new Authenticator() {
          @Override
          public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
            return response.request().newBuilder()
              .header("Authorization", credential)
              .build();
          }
        });
        //得到httpClient
        OkHttpClient client = clientBuilder.build();

        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        String resultContent = response.body().string();
        WxError error = WxError.fromJson(resultContent);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
          accessToken.getExpiresIn());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return this.getWxMpConfigStorage().getAccessToken();
  }

  @Override
  public void initHttp() {
    WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

    if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
      httpProxy = new OkHttpProxyInfo(OkHttpProxyInfo.ProxyType.SOCKS5, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
    }

    httpClient = new ConnectionPool();
  }

}
