package com.iyeed.core.entity.receive.vo;

import java.io.Serializable;

public class ReceiveTesterBean implements Serializable {
    private String brandName;//品牌
    private String storeId;//店号
    private String storeNo;//门店号
    private String storeName;//门店名称
    private String storeAttr;//门店属性
    private String skuCode;//SKU_CODE
    private String skuName;//SKU_NAME
    private Integer rcTotal;//日常试用装
    private Integer vmTotal;//VM试用装
    private Integer newTotal;//新开柜
    private Integer hdTotal;//活动装
    private Integer mrTotal;//美容坊
    private Integer arTotal;//Airen
    private Integer total;//总试用装
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

    public Integer getRcTotal() {
        return rcTotal;
    }

    public void setRcTotal(Integer rcTotal) {
        this.rcTotal = rcTotal;
    }

    public Integer getVmTotal() {
        return vmTotal;
    }

    public void setVmTotal(Integer vmTotal) {
        this.vmTotal = vmTotal;
    }

    public Integer getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(Integer newTotal) {
        this.newTotal = newTotal;
    }

    public Integer getHdTotal() {
        return hdTotal;
    }

    public void setHdTotal(Integer hdTotal) {
        this.hdTotal = hdTotal;
    }

    public Integer getMrTotal() {
        return mrTotal;
    }

    public void setMrTotal(Integer mrTotal) {
        this.mrTotal = mrTotal;
    }

    public Integer getArTotal() {
        return arTotal;
    }

    public void setArTotal(Integer arTotal) {
        this.arTotal = arTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
