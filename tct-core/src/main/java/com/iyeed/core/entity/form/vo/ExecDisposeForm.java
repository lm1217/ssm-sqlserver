package com.iyeed.core.entity.form.vo;

import com.iyeed.core.entity.form.BdFormImage;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/22 10:00
 */
public class ExecDisposeForm implements Serializable {
    private Integer id;
    private String userNo;
    private Integer type;
    private String remark;
    private List<BdFormImage> formImageList;

    public List<BdFormImage> getFormImageList() {
        return formImageList;
    }

    public void setFormImageList(List<BdFormImage> formImageList) {
        this.formImageList = formImageList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
