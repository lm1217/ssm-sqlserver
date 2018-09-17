package com.iyeed.service.sku;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.sku.MdSku;

import java.util.List;
import java.util.Map;

public interface IMdSkuService {

    /**
     * 根据id取得SKU表
     * @param  mdSkuId
     * @return
     */
    ServiceResult<MdSku> getMdSkuById(Integer mdSkuId);

    ServiceResult<List<MdSku>> getSkuList(Map<String, Object> queryMap);
    
    /**
     * 保存SKU表
     * @param  mdSku
     * @return
     */
    ServiceResult<Integer> saveMdSku(MdSku mdSku);
     
    /**
     * 更新SKU表
     * @param  mdSku
     * @return
     */
    ServiceResult<Integer> updateMdSku(MdSku mdSku);
}