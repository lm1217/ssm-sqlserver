package com.iyeed.core.entity.system;

import java.io.Serializable;

public class SystemUserBrand implements Serializable {
    private Integer id;
    private String username;
    private String brandNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }
}
