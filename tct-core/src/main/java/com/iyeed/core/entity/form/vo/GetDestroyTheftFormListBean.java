package com.iyeed.core.entity.form.vo;

import java.io.Serializable;

public class GetDestroyTheftFormListBean implements Serializable {
    private String brandName;//品牌
    private String storeId;//店号
    private String storeNo;//门店号
    private String storeName;//门店名称
    private String storeAttr;//门店属性
    private String skuCode;//SKU_CODE
    private String skuName;//SKU_NAME
    private Integer qtl;//当月盗损件数
    private Integer stockQtl;//当月库存件数
    private Integer cytdQtl;//CYTD盗损件数
    private Integer cytdStockQtl;//CYTD库存件数
    private Integer fytdQtl;//FYTD盗损件数
    private Integer fytdStockQtl;//FYTD库存件数
    private String rate;//当月盗损率
    private String cytdRate;//CYTD盗损率
    private String fytdRate;//FYTD盗损率
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

    public Integer getStockQtl() {
        return stockQtl;
    }

    public void setStockQtl(Integer stockQtl) {
        this.stockQtl = stockQtl;
    }

    public Integer getCytdStockQtl() {
        return cytdStockQtl;
    }

    public void setCytdStockQtl(Integer cytdStockQtl) {
        this.cytdStockQtl = cytdStockQtl;
    }

    public Integer getFytdStockQtl() {
        return fytdStockQtl;
    }

    public void setFytdStockQtl(Integer fytdStockQtl) {
        this.fytdStockQtl = fytdStockQtl;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCytdRate() {
        return cytdRate;
    }

    public void setCytdRate(String cytdRate) {
        this.cytdRate = cytdRate;
    }

    public String getFytdRate() {
        return fytdRate;
    }

    public void setFytdRate(String fytdRate) {
        this.fytdRate = fytdRate;
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
