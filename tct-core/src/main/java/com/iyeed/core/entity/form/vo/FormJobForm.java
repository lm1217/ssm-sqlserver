package com.iyeed.core.entity.form.vo;

import java.io.Serializable;
import java.util.Date;

public class FormJobForm implements Serializable {
    private Integer id;
    private String disposeUserNo;
    private Integer disposeGrade;
    private Integer disposeStatus;
    private String disposeStatusDesc;
    private java.util.Date updateDate;
    private java.util.Date sendMailDate;
    private Integer[] idArr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisposeUserNo() {
        return disposeUserNo;
    }

    public void setDisposeUserNo(String disposeUserNo) {
        this.disposeUserNo = disposeUserNo;
    }

    public Integer getDisposeGrade() {
        return disposeGrade;
    }

    public void setDisposeGrade(Integer disposeGrade) {
        this.disposeGrade = disposeGrade;
    }

    public Integer getDisposeStatus() {
        return disposeStatus;
    }

    public void setDisposeStatus(Integer disposeStatus) {
        this.disposeStatus = disposeStatus;
    }

    public String getDisposeStatusDesc() {
        return disposeStatusDesc;
    }

    public void setDisposeStatusDesc(String disposeStatusDesc) {
        this.disposeStatusDesc = disposeStatusDesc;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getSendMailDate() {
        return sendMailDate;
    }

    public void setSendMailDate(Date sendMailDate) {
        this.sendMailDate = sendMailDate;
    }

    public Integer[] getIdArr() {
        return idArr;
    }

    public void setIdArr(Integer[] idArr) {
        this.idArr = idArr;
    }
}
