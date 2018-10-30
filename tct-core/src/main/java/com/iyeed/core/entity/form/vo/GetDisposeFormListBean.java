package com.iyeed.core.entity.form.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/21 17:15
 */
public class GetDisposeFormListBean implements Serializable {
    private Integer id;
    private String storeNo;
    private String storeName;
    private String applyNo;
    private java.util.Date applyDate;
    private Integer formType;
    private java.util.Date inputDate;
    private Integer disposeStatus;
    private String disposeStatusDesc;
    private Integer isBack;
    private String applyUserNo;

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

    public String getDisposeStatusDesc() {
        return disposeStatusDesc;
    }

    public void setDisposeStatusDesc(String disposeStatusDesc) {
        this.disposeStatusDesc = disposeStatusDesc;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Integer getDisposeStatus() {
        return disposeStatus;
    }

    public void setDisposeStatus(Integer disposeStatus) {
        this.disposeStatus = disposeStatus;
    }
}
