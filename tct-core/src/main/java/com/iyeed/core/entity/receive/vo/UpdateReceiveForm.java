package com.iyeed.core.entity.receive.vo;

import com.iyeed.core.entity.receive.BdReceivingSku;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/15 13:47
 */
public class UpdateReceiveForm implements Serializable {
    private Integer id;
    private Integer actSendTotal;
    private Integer status;
    private List<BdReceivingSku> bdReceivingSkuList;
    private String userNo;
    private Integer userType;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActSendTotal() {
        return actSendTotal;
    }

    public void setActSendTotal(Integer actSendTotal) {
        this.actSendTotal = actSendTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BdReceivingSku> getBdReceivingSkuList() {
        return bdReceivingSkuList;
    }

    public void setBdReceivingSkuList(List<BdReceivingSku> bdReceivingSkuList) {
        this.bdReceivingSkuList = bdReceivingSkuList;
    }
}
