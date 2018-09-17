package com.iyeed.core.entity.stock.vo;

import java.io.Serializable;

public class GetStockInvReportListBean implements Serializable {
    private Integer id;
    private String storeNo;
    private String storeName;
    private String skuName;
    private String skuCode;
    private String brandName;
    private Integer stockDepot;
    private Integer stockDepotEnabled;
    private Integer stockCounter;
    private Integer stockCounterEnabled;
    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
