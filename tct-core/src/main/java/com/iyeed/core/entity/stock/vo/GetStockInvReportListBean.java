package com.iyeed.core.entity.stock.vo;

import java.io.Serializable;

public class GetStockInvReportListBean implements Serializable {
    private String brandName;//品牌
    private String storeId;//店号
    private String storeNo;//门店号
    private String storeName;//门店名称
    private String storeAttr;//门店属性
    private String skuCode;//SKU_CODE
    private String skuName;//SKU_NAME
    private String region;//区域
    private String city;//城市
    private String channel;//渠道
    private String doorType;//门店类型
    private String asm;//ASM
    private String ac;//AC
    private String userNo;//门店帐号类型
    private Integer stockCounter;//柜面总库存
    private Integer stockCounterEnabled;//柜面可用库存
    private Integer stockCounterFreeze;//柜面冻结数量
    private Integer stockDepot;//店仓总库存
    private Integer stockDepotEnabled;//店仓可用库存
    private Integer stockDepotFreeze;//店仓冻结数量
    private Integer total;//总库存
    private Integer enabledTotal;//总可用库存
    private Integer freezeTotal;//总冻结数量

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

    public Integer getEnabledTotal() {
        return enabledTotal;
    }

    public void setEnabledTotal(Integer enabledTotal) {
        this.enabledTotal = enabledTotal;
    }

    public Integer getFreezeTotal() {
        return freezeTotal;
    }

    public void setFreezeTotal(Integer freezeTotal) {
        this.freezeTotal = freezeTotal;
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

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getStockDepotFreeze() {
        return stockDepotFreeze;
    }

    public void setStockDepotFreeze(Integer stockDepotFreeze) {
        this.stockDepotFreeze = stockDepotFreeze;
    }

    public Integer getStockCounterFreeze() {
        return stockCounterFreeze;
    }

    public void setStockCounterFreeze(Integer stockCounterFreeze) {
        this.stockCounterFreeze = stockCounterFreeze;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getStockDepot() {
        return stockDepot;
    }

    public void setStockDepot(Integer stockDepot) {
        this.stockDepot = stockDepot;
    }

    public Integer getStockDepotEnabled() {
        return stockDepotEnabled;
    }

    public void setStockDepotEnabled(Integer stockDepotEnabled) {
        this.stockDepotEnabled = stockDepotEnabled;
    }

    public Integer getStockCounter() {
        return stockCounter;
    }

    public void setStockCounter(Integer stockCounter) {
        this.stockCounter = stockCounter;
    }

    public Integer getStockCounterEnabled() {
        return stockCounterEnabled;
    }

    public void setStockCounterEnabled(Integer stockCounterEnabled) {
        this.stockCounterEnabled = stockCounterEnabled;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
