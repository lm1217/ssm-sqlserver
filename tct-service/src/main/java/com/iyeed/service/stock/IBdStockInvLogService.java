package com.iyeed.service.stock;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInvLog;

import java.util.List;
import java.util.Map;

public interface IBdStockInvLogService {

    /**
     * 根据id取得库存日志表
     * @param  bdStockInvLogId
     * @return
     */
    ServiceResult<BdStockInvLog> getBdStockInvLogById(Integer bdStockInvLogId);
    
    /**
     * 保存库存日志表
     * @param  bdStockInvLog
     * @return
     */
    ServiceResult<Integer> saveBdStockInvLog(BdStockInvLog bdStockInvLog);
     
    /**
     * 更新库存日志表
     * @param  bdStockInvLog
     * @return
     */
    ServiceResult<Integer> updateBdStockInvLog(BdStockInvLog bdStockInvLog);


    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    ServiceResult<List<BdStockInvLog>> getBdStockInvLogList(Map<String, Object> queryMap, PagerInfo pager);

}

