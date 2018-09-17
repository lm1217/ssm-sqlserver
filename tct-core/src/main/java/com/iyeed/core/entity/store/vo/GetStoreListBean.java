package com.iyeed.core.entity.store.vo;

import java.io.Serializable;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 16:34
 */
public class GetStoreListBean implements Serializable {
    private String storeNo;
    private String storeName;

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
}
