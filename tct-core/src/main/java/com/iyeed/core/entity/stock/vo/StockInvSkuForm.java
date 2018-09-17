package com.iyeed.core.entity.stock.vo;

import java.io.Serializable;
import java.util.List;

public class StockInvSkuForm implements Serializable {
    private List<Sku> skuList;
    public static class Sku {
        private Integer id;
        private Integer moveNum;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMoveNum() {
            return moveNum;
        }

        public void setMoveNum(Integer moveNum) {
            this.moveNum = moveNum;
        }
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
