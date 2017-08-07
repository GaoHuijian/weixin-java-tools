package me.chanjar.weixin.mp.bean.message;

import com.cico.modules.weixin.common.util.ToStringUtils;
import com.cico.modules.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * <pre>
 *  Created by BinaryWang on 2017/5/4.
 * </pre>
 *
 * @author Binary Wang
 */
@XStreamAlias("ScanCodeInfo")
public class ScanCodeInfo {
  @XStreamAlias("ScanType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String scanType;
  @XStreamAlias("ScanResult")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String scanResult;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  /**
   * 扫描类型，一般是qrcode
   */
  public String getScanType() {

    return this.scanType;
  }

  public void setScanType(String scanType) {
    this.scanType = scanType;
  }

  /**
   * 扫描结果，即二维码对应的字符串信息
   */
  public String getScanResult() {
    return this.scanResult;
  }

  public void setScanResult(String scanResult) {
    this.scanResult = scanResult;
  }

}
