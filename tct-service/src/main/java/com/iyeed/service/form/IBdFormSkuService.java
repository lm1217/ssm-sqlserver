package com.iyeed.service.form;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormSku;

import java.util.List;

public interface IBdFormSkuService {

    /**
     * 根据id取得表单-SKU子表 
     * @param  bdFormSkuId
     * @return
     */
    ServiceResult<BdFormSku> getBdFormSkuById(Integer bdFormSkuId);

    ServiceResult<List<BdFormSku>> getBdFormSkuListByApplyNo(String applyNo);

    ServiceResult<List<BdFormSku>> getBdFormSkuListForBack(String applyNo, String storeNo);
    
    /**
     * 保存表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    ServiceResult<Integer> saveBdFormSku(BdFormSku bdFormSku);
     
    /**
     * 更新表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    ServiceResult<Integer> updateBdFormSku(BdFormSku bdFormSku);
}