package com.iyeed.model.stock;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.stock.BdStockInvLog;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvLogWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class BdStockInvLogModel {
    
    @Resource
    private BdStockInvLogWriteDao bdStockInvLogWriteDao;
    
    /**
     * 根据id取得库存日志表
     * @param  bdStockInvLogId
     * @return
     */
    public BdStockInvLog getBdStockInvLogById(Integer bdStockInvLogId) throws Exception {
    	return bdStockInvLogWriteDao.get(bdStockInvLogId);
    }

    public List<BdStockInvLog> getBdStockInvLogList(Map<String, Object> queryMap, Integer start, Integer size) throws Exception {
        return bdStockInvLogWriteDao.getBdStockInvLogList(queryMap, start, size);
    }

    public Integer getBdStockInvLogListCount(Map<String, Object> queryMap) throws Exception {
        return bdStockInvLogWriteDao.getBdStockInvLogListCount(queryMap);
    }


    /**
     * 保存库存日志表
     * @param  bdStockInvLog
     * @return
     */
    public Integer saveBdStockInvLog(BdStockInvLog bdStockInvLog) throws Exception {
     	this.dbConstrains(bdStockInvLog);
     	return bdStockInvLogWriteDao.insert(bdStockInvLog);
    }
     
    /**
     * 更新库存日志表
     * @param  bdStockInvLog
     * @return
     */
    public Integer updateBdStockInvLog(BdStockInvLog bdStockInvLog) throws Exception {
        this.dbConstrains(bdStockInvLog);
     	return bdStockInvLogWriteDao.update(bdStockInvLog);
    }
     
    private void dbConstrains(BdStockInvLog bdStockInvLog) throws Exception {
		bdStockInvLog.setStoreNo(StringUtil.dbSafeString(bdStockInvLog.getStoreNo(), true, 40));
		bdStockInvLog.setStoreName(StringUtil.dbSafeString(bdStockInvLog.getStoreName(), true, 40));
		bdStockInvLog.setSkuCode(StringUtil.dbSafeString(bdStockInvLog.getSkuCode(), true, 40));
		bdStockInvLog.setSkuName(StringUtil.dbSafeString(bdStockInvLog.getSkuName(), true, 200));
    }
}