package com.iyeed.core.entity.user;

import java.io.Serializable;

public class MdUserData implements Serializable {
    private String username;
    private String userId;
    private String userName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
