package com.iyeed.core.entity.form.vo;

import java.io.Serializable;

public class GetDestroyFormListBean implements Serializable {
    private String brandName;//品牌
    private String storeId;//店号
    private String storeNo;//门店号
    private String storeName;//门店名称
    private String storeAttr;//门店属性
    private String skuCode;//SKU_CODE
    private String skuName;//SKU_NAME
    private Integer qtl;//当月销毁件数
    private Integer cytdQtl;//CYTD销毁件数
    private Integer fytdQtl;//FYTD销毁件数
    private String hz;//当月主管销毁频率
    private String cytdHz;//CYTD主管销毁频率
    private String fytdHz;//FYTD主管销毁频率
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

    public String getHz() {
        return hz;
    }

    public void setHz(String hz) {
        this.hz = hz;
    }

    public String getCytdHz() {
        return cytdHz;
    }

    public void setCytdHz(String cytdHz) {
        this.cytdHz = cytdHz;
    }

    public String getFytdHz() {
        return fytdHz;
    }

    public void setFytdHz(String fytdHz) {
        this.fytdHz = fytdHz;
    }

    public Integer getCytdQtl() {
        return cytdQtl;
    }

    public void setCytdQtl(Integer cytdQtl) {
        this.cytdQtl = cytdQtl;
    }

    public Integer getFytdQtl() {
        return fytdQtl;
    }

    public void setFytdQtl(Integer fytdQtl) {
        this.fytdQtl = fytdQtl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
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

    public Integer getQtl() {
        return qtl;
    }

    public void setQtl(Integer qtl) {
        this.qtl = qtl;
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

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }
}
