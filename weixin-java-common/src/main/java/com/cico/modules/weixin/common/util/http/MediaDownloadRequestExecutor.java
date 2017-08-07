package com.cico.modules.weixin.common.util.http;

import java.io.File;

import com.cico.modules.weixin.common.util.http.apache.ApacheMediaDownloadRequestExecutor;
import com.cico.modules.weixin.common.util.http.jodd.JoddHttpMediaDownloadRequestExecutor;
import com.cico.modules.weixin.common.util.http.okhttp.OkHttpMediaDownloadRequestExecutor;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 *
 * @author Daniel Qian
 */
public abstract class MediaDownloadRequestExecutor<H, P> implements RequestExecutor<File, String> {

  protected RequestHttp<H, P> requestHttp;
  protected File tmpDirFile;
  public MediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
    this.requestHttp = requestHttp;
    this.tmpDirFile = tmpDirFile;
  }

  public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case JODD_HTTP:
        return new JoddHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case OK_HTTP:
        return new OkHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      default:
        return null;
    }
  }

}
