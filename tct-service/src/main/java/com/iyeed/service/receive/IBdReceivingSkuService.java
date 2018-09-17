package com.iyeed.service.receive;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.receive.BdReceivingSku;

import java.util.List;

public interface IBdReceivingSkuService {

    /**
     * 根据id取得收货表-SKU子表
     * @param  bdReceivingSkuId
     * @return
     */
    ServiceResult<BdReceivingSku> getBdReceivingSkuById(Integer bdReceivingSkuId);

    /**
     * 根据erpNo取得收货表-SKU子表列表
     * @param  erpNo
     * @return
     */
    ServiceResult<List<BdReceivingSku>> getBdReceivingSkuListByErpNo(String erpNo);
    
    /**
     * 保存收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    ServiceResult<Integer> saveBdReceivingSku(BdReceivingSku bdReceivingSku);
     
    /**
     * 更新收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    ServiceResult<Integer> updateBdReceivingSku(BdReceivingSku bdReceivingSku);
}