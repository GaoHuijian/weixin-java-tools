package me.chanjar.weixin.mp.bean.store;

import com.cico.modules.weixin.common.util.ToStringUtils;
import com.google.gson.annotations.SerializedName;

public class WxMpStoreInfo {
  @SerializedName("base_info")
  private WxMpStoreBaseInfo baseInfo;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public WxMpStoreBaseInfo getBaseInfo() {
    return this.baseInfo;
  }

  public void setBaseInfo(WxMpStoreBaseInfo baseInfo) {
    this.baseInfo = baseInfo;
  }

}
