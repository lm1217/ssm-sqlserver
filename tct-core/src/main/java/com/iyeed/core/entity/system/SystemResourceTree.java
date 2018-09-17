package com.iyeed.core.entity.system;

import java.io.Serializable;
import java.util.List;

public class SystemResourceTree implements Serializable {
    private Integer            id;
    private String             text;
    private Boolean            checked;
    private String             state;
    private List<SystemResourceTree> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SystemResourceTree> getChildren() {
        return children;
    }

    public void setChildren(List<SystemResourceTree> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
