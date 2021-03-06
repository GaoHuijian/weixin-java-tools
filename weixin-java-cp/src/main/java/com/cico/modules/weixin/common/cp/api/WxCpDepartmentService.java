package com.cico.modules.weixin.common.cp.api;

import java.util.List;

import com.cico.modules.weixin.common.cp.bean.WxCpDepart;
import com.cico.modules.weixin.common.exception.WxErrorException;

/**
 * <pre>
 *  部门管理接口
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxCpDepartmentService {

  /**
   * <pre>
   * 部门管理接口 - 创建部门
   * 最多支持创建500个部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   *
   * @param depart 部门
   * @return 部门id
   */
  Integer create(WxCpDepart depart) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 查询所有部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   */
  List<WxCpDepart> listAll() throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 修改部门名
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * 如果id为0(未部门),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   *
   * @param group 要更新的group，group的id,name必须设置
   */
  void update(WxCpDepart group) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 删除部门
   * </pre>
   *
   * @param departId 部门id
   */
  void delete(Integer departId) throws WxErrorException;
}
