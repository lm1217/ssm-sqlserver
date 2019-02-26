package com.iyeed.core.entity.form.vo;

import java.io.Serializable;

public class GetDSIFormListBean implements Serializable {
    private String brandName;//品牌
    private String applyNo;//单号
    private String storeId;//店号
    private String storeNo;//门店号
    private String storeName;//门店名称
    private String storeAttr;//门店属性
    private String skuCode;//SKU_CODE
    private String skuName;//SKU_NAME
    private Integer qtl;//销毁件数
    private Integer dsi;//周转天数
    private String region;//区域
    private String city;//城市
    private String channel;//渠道
    private String doorType;//门店类型
    private String asm;//ASM
    private String ac;//AC

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreAttr() {
        return storeAttr;
    }

    public void setStoreAttr(String storeAttr) {
        this.storeAttr = storeAttr;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getQtl() {
        return qtl;
    }

    public void setQtl(Integer qtl) {
        this.qtl = qtl;
    }

    public Integer getDsi() {
        return dsi;
    }

    public void setDsi(Integer dsi) {
        this.dsi = dsi;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }
}
