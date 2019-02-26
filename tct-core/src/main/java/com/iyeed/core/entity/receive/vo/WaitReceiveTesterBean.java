package com.iyeed.core.entity.receive.vo;

import java.io.Serializable;
import java.util.Date;

public class WaitReceiveTesterBean implements Serializable {
    private String pkgNo;//PKG_NO
    private String brandName;//品牌
    private String sendType;//发货类型
    private String sendStoreNo;//发货方店号
    private String sendStoreName;//发货方门店名称
    private String receiveStoreId;//收货方店号
    private String receiveStoreNo;//收货方门店号
    private String receiveStoreName;//收货方门店名称
    private String storeAttr;//收货方门店属性
    private String sendTotal;//发货总件数
    private Date sendDate;//发货时间

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendStoreNo() {
        return sendStoreNo;
    }

    public void setSendStoreNo(String sendStoreNo) {
        this.sendStoreNo = sendStoreNo;
    }

    public String getSendStoreName() {
        return sendStoreName;
    }

    public void setSendStoreName(String sendStoreName) {
        this.sendStoreName = sendStoreName;
    }

    public String getReceiveStoreId() {
        return receiveStoreId;
    }

    public void setReceiveStoreId(String receiveStoreId) {
        this.receiveStoreId = receiveStoreId;
    }

    public String getReceiveStoreNo() {
        return receiveStoreNo;
    }

    public void setReceiveStoreNo(String receiveStoreNo) {
        this.receiveStoreNo = receiveStoreNo;
    }

    public String getReceiveStoreName() {
        return receiveStoreName;
    }

    public void setReceiveStoreName(String receiveStoreName) {
        this.receiveStoreName = receiveStoreName;
    }

    public String getStoreAttr() {
        return storeAttr;
    }

    public void setStoreAttr(String storeAttr) {
        this.storeAttr = storeAttr;
    }

    public String getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(String sendTotal) {
        this.sendTotal = sendTotal;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
