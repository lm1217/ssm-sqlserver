package com.iyeed.core.entity.excel;

import java.io.Serializable;

public class ReceiveTypeExcel implements Serializable {
    private String pkgNo;
    private String receiveType;

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }
}
