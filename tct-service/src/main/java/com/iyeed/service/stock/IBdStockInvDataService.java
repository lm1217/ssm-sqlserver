package com.iyeed.service.stock;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInvData;

public interface IBdStockInvDataService {

    /**
     * 根据id取得业务-库存表
     * @param  bdStockInvDataId
     * @return
     */
    ServiceResult<BdStockInvData> getBdStockInvDataById(Integer bdStockInvDataId);
    
    /**
     * 保存业务-库存表
     * @param  bdStockInvData
     * @return
     */
    ServiceResult<Integer> saveBdStockInvData(BdStockInvData bdStockInvData);
     
    /**
     * 更新业务-库存表
     * @param  bdStockInvData
     * @return
     */
    ServiceResult<Integer> updateBdStockInvData(BdStockInvData bdStockInvData);

    /**
     * 备份库存表
     * @return
     */
    ServiceResult<Integer> insertForBackup();
}