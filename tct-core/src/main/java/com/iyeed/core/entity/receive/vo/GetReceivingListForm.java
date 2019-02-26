package com.iyeed.core.entity.receive.vo;

import java.io.Serializable;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/15 15:20
 */
public class GetReceivingListForm implements Serializable {
    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    private Integer totalPage;
    private String starttime;
    private String endtime;
    private Integer status = 1;
    private String erpNo;
    private String orderNo;
    private String storeNo;
    private String brandNo;
    private String receiveUserNo;

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getReceiveUserNo() {
        return receiveUserNo;
    }

    public void setReceiveUserNo(String receiveUserNo) {
        this.receiveUserNo = receiveUserNo;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErpNo() {
        return erpNo;
    }

    public void setErpNo(String erpNo) {
        this.erpNo = erpNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }
}
