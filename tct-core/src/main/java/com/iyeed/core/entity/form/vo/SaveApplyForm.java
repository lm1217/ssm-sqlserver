package com.iyeed.core.entity.form.vo;

import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 16:12
 */
public class SaveApplyForm implements Serializable {
    private Integer id;
    private String storeNo;
    private String storeName;
    private String applyNo;
    private String applicant;
    private String applicantTel;
    private String remark;
    private String applyDate;
    private Integer destroyType;
    private String receiveStoreNo;
    private String receiveStoreName;
    private String expressCode;
    private String expressName;
    private String orderNo;
    private String expressDate;
    private Integer allotType;
    private Integer exceptionType;
    private Integer formType;
    private String inputDate;
    private String applyUserNo;
    private List<BdFormImage> formImageList;
    private List<BdFormSku> formSkuList;
    private String userName;
    private String userNo;
    private Integer isBack;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantTel() {
        return applicantTel;
    }

    public void setApplicantTel(String applicantTel) {
        this.applicantTel = applicantTel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getDestroyType() {
        return destroyType;
    }

    public void setDestroyType(Integer destroyType) {
        this.destroyType = destroyType;
    }

    public String getReceiveStoreNo() {
        return receiveStoreNo;
    }

    public void setReceiveStoreNo(String receiveStoreNo) {
        this.receiveStoreNo = receiveStoreNo;
    }

    public String getReceiveStoreName() {
        return receiveStoreName;
    }

    public void setReceiveStoreName(String receiveStoreName) {
        this.receiveStoreName = receiveStoreName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExpressDate() {
        return expressDate;
    }

    public void setExpressDate(String expressDate) {
        this.expressDate = expressDate;
    }

    public Integer getAllotType() {
        return allotType;
    }

    public void setAllotType(Integer allotType) {
        this.allotType = allotType;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Integer exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getApplyUserNo() {
        return applyUserNo;
    }

    public void setApplyUserNo(String applyUserNo) {
        this.applyUserNo = applyUserNo;
    }

    public List<BdFormImage> getFormImageList() {
        return formImageList;
    }

    public void setFormImageList(List<BdFormImage> formImageList) {
        this.formImageList = formImageList;
    }

    public List<BdFormSku> getFormSkuList() {
        return formSkuList;
    }

    public void setFormSkuList(List<BdFormSku> formSkuList) {
        this.formSkuList = formSkuList;
    }
}
