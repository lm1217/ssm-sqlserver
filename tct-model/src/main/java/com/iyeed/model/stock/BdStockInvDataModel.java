package com.iyeed.model.stock;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.stock.BdStockInvData;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

@Component
public class BdStockInvDataModel extends BaseModel {
    
    /**
     * 根据id取得业务-库存表
     * @param  bdStockInvDataId
     * @return
     */
    public BdStockInvData getBdStockInvDataById(Integer bdStockInvDataId) throws Exception {
    	return bdStockInvDataWriteDao.get(bdStockInvDataId);
    }
    
    /**
     * 保存业务-库存表
     * @param  bdStockInvData
     * @return
     */
    public Integer saveBdStockInvData(BdStockInvData bdStockInvData) throws Exception {
     	this.dbConstrains(bdStockInvData);
     	return bdStockInvDataWriteDao.insert(bdStockInvData);
    }
     
    /**
     * 更新业务-库存表
     * @param  bdStockInvData
     * @return
     */
    public Integer updateBdStockInvData(BdStockInvData bdStockInvData) throws Exception {
        this.dbConstrains(bdStockInvData);
     	return bdStockInvDataWriteDao.update(bdStockInvData);
    }

    /**
     * 备份库存表
     * @return
     */
    public Integer insertForBackup() throws Exception {
        return bdStockInvDataWriteDao.insertForBackup();
    }
    private void dbConstrains(BdStockInvData bdStockInvData) throws Exception {
		bdStockInvData.setStoreNo(StringUtil.dbSafeString(bdStockInvData.getStoreNo(), true, 40));
		bdStockInvData.setStoreName(StringUtil.dbSafeString(bdStockInvData.getStoreName(), true, 40));
		bdStockInvData.setSkuCode(StringUtil.dbSafeString(bdStockInvData.getSkuCode(), true, 40));
		bdStockInvData.setSkuName(StringUtil.dbSafeString(bdStockInvData.getSkuName(), true, 200));
		bdStockInvData.setBrandNo(StringUtil.dbSafeString(bdStockInvData.getBrandNo(), true, 40));
		bdStockInvData.setUserNo(StringUtil.dbSafeString(bdStockInvData.getUserNo(), true, 255));
    }
}