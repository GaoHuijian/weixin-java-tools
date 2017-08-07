package com.cico.modules.weixin.common.cp.util.json;

import com.cico.modules.weixin.common.bean.menu.WxMenu;
import com.cico.modules.weixin.common.bean.result.WxError;
import com.cico.modules.weixin.common.cp.bean.WxCpDepart;
import com.cico.modules.weixin.common.cp.bean.WxCpMessage;
import com.cico.modules.weixin.common.cp.bean.WxCpTag;
import com.cico.modules.weixin.common.cp.bean.WxCpUser;
import com.cico.modules.weixin.common.util.json.WxErrorAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxCpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCpMessage.class, new WxCpMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxCpMenuGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
