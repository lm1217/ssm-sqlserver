package com.iyeed.core.entity.stock.vo;

import java.io.Serializable;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 15:21
 */
public class GetStockInvSkuListBean implements Serializable {
    private Integer id;
    private String skuCode;
    private String skuName;
    private Integer stockDepot;
    private Integer stockDepotEnabled;
    private Integer stockCounter;
    private Integer stockCounterEnabled;
    private Integer stockDepotFreeze;
    private Integer stockCounterFreeze;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
