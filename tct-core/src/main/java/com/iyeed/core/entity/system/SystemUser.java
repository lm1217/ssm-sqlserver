package com.iyeed.core.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/5 11:01
 */
public class SystemUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private String email;
    private String tel;
    private Integer status;
    private Integer userType;
    private String userNo;
    private Date createTime;
    private Integer logins;

    // 额外属性
    private String storeNo;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public Integer getLogins() {
        return logins;
    }

    public void setLogins(Integer logins) {
        this.logins = logins;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
