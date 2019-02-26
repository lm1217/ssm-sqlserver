package com.iyeed.core.entity.excel;

import java.io.Serializable;

public class ReceivedExcel implements Serializable {
    private String pkgNo;

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }
}
