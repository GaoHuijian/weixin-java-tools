package me.chanjar.weixin.mp.bean.device;

import com.cico.modules.weixin.common.util.json.WxGsonBuilder;

/**
 * Created by keungtung on 14/12/2016.
 */
public abstract class AbstractDeviceBean {
  public String toJson() {
    return WxGsonBuilder.create().toJson(this);
  }
}
