package com.iyeed.core.entity.form.vo;

import java.io.Serializable;

public class FormJobBean implements Serializable {
    private Integer id;
    private String storeNo;
    private String storeName;
    private String applyNo;
    private String applyUserNo;
    private Integer formType;
    private String disposeUserNo;
    private Integer disposeGrade;
    private Integer disposeStatus;
    private String email;
    private Integer isBack;

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }

    public String getApplyUserNo() {
        return applyUserNo;
    }

    public void setApplyUserNo(String applyUserNo) {
        this.applyUserNo = applyUserNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
