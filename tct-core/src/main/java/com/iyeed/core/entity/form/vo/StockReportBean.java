package com.iyeed.core.entity.form.vo;

import java.io.Serializable;

public class StockReportBean implements Serializable {
    private String storeNo;//门店号
//    private String storeName;//门店名称
//    private String storeUserNo;//门店执行号
    private Integer stockStart;//期初库存
    private Integer shDc;//收货-大仓
    private Integer shDb;//收货-调拨
    private Integer wshDc;//未收货-大仓
    private Integer wshDb;//未收货-调拨
    private Integer xhZc;//销毁-正常
    private Integer xhPs;//销毁-破损
    private Integer xhGq;//销毁-过期
    private Integer xhSq;//销毁-失窃
    private Integer allot;//调拨
    private Integer exceptionA;//异常调整-增加
    private Integer exceptionB;//异常调整-减少
    private Integer stockEnd;//期末库存
    private String region;//区域
    private String city;//城市
    private String channel;//渠道
    private String doorType;//门店类型
    private String asm;//ASM
    private String ac;//AC

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public Integer getStockStart() {
        return stockStart;
    }

    public void setStockStart(Integer stockStart) {
        this.stockStart = stockStart;
    }

    public Integer getShDc() {
        return shDc;
    }

    public void setShDc(Integer shDc) {
        this.shDc = shDc;
    }

    public Integer getShDb() {
        return shDb;
    }

    public void setShDb(Integer shDb) {
        this.shDb = shDb;
    }

    public Integer getWshDc() {
        return wshDc;
    }

    public void setWshDc(Integer wshDc) {
        this.wshDc = wshDc;
    }

    public Integer getWshDb() {
        return wshDb;
    }

    public void setWshDb(Integer wshDb) {
        this.wshDb = wshDb;
    }

    public Integer getXhZc() {
        return xhZc;
    }

    public void setXhZc(Integer xhZc) {
        this.xhZc = xhZc;
    }

    public Integer getXhPs() {
        return xhPs;
    }

    public void setXhPs(Integer xhPs) {
        this.xhPs = xhPs;
    }

    public Integer getXhGq() {
        return xhGq;
    }

    public void setXhGq(Integer xhGq) {
        this.xhGq = xhGq;
    }

    public Integer getXhSq() {
        return xhSq;
    }

    public void setXhSq(Integer xhSq) {
        this.xhSq = xhSq;
    }

    public Integer getAllot() {
        return allot;
    }

    public void setAllot(Integer allot) {
        this.allot = allot;
    }

    public Integer getExceptionA() {
        return exceptionA;
    }

    public void setExceptionA(Integer exceptionA) {
        this.exceptionA = exceptionA;
    }

    public Integer getExceptionB() {
        return exceptionB;
    }

    public void setExceptionB(Integer exceptionB) {
        this.exceptionB = exceptionB;
    }

    public Integer getStockEnd() {
        return stockEnd;
    }

    public void setStockEnd(Integer stockEnd) {
        this.stockEnd = stockEnd;
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
