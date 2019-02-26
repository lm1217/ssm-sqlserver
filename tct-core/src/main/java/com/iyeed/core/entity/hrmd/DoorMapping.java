package com.iyeed.core.entity.hrmd;

import java.io.Serializable;

public class DoorMapping implements Serializable {
    private String brandCode;
    private String storeNo;
    private String storeName;
    private String userId;
    private String userName;
    private String userNameEn;
    private String userNtaccount;

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn;
    }

    public String getUserNtaccount() {
        return userNtaccount;
    }

    public void setUserNtaccount(String userNtaccount) {
        this.userNtaccount = userNtaccount;
    }
}

