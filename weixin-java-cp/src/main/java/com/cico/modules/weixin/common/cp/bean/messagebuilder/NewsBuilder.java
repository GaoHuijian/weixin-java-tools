package com.cico.modules.weixin.common.cp.bean.messagebuilder;

import java.util.ArrayList;
import java.util.List;

import com.cico.modules.weixin.common.api.WxConsts;
import com.cico.modules.weixin.common.cp.bean.WxCpMessage;
import com.cico.modules.weixin.common.cp.bean.article.NewArticle;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 *
 * @author Daniel Qian
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<NewArticle> articles = new ArrayList<>();

  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(NewArticle article) {
    this.articles.add(article);
    return this;
  }

  @Override
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
