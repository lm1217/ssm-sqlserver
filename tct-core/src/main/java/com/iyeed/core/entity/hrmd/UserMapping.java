package com.iyeed.core.entity.hrmd;

import java.io.Serializable;

public class UserMapping implements Serializable {
    private String userName;
    private String userNameEn;
    private String userNtaccount;
    private String userEmail;
    private String userId;
    private String userPid;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPid() {
        return userPid;
    }

    public void setUserPid(String userPid) {
        this.userPid = userPid;
    }
}
