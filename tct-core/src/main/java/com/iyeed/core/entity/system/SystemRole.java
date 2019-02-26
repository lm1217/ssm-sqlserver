package com.iyeed.core.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/5 11:04
 */
public class SystemRole implements Serializable {
    private Integer id;
    private String roleName;
    private String desc;
    private String roleCode;
    private Integer status;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String rolesCode) {
        this.roleCode = rolesCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SystemRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", desc='" + desc + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
