package com.iyeed.model.receive;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BdReceivingSkuModel extends BaseModel {
    
    /**
     * 根据id取得收货表-SKU子表
     * @param  bdReceivingSkuId
     * @return
     */
    public BdReceivingSku getBdReceivingSkuById(Integer bdReceivingSkuId) throws Exception {
    	return bdReceivingSkuWriteDao.get(bdReceivingSkuId);
    }

    /**
     * 根据erpNo取得收货表-SKU子表列表
     * @param  erpNo
     * @return
     */
    public List<BdReceivingSku> getBdReceivingSkuListByErpNo(String erpNo) throws Exception {
        return bdReceivingSkuWriteDao.getBdReceivingSkuListByErpNo(erpNo);
    }
    
    /**
     * 保存收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    public Integer saveBdReceivingSku(BdReceivingSku bdReceivingSku) throws Exception {
     	this.dbConstrains(bdReceivingSku);
     	return bdReceivingSkuWriteDao.insert(bdReceivingSku);
    }
     
    /**
     * 更新收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    public Integer updateBdReceivingSku(BdReceivingSku bdReceivingSku) throws Exception {
        this.dbConstrains(bdReceivingSku);
     	return bdReceivingSkuWriteDao.update(bdReceivingSku);
    }
     
    private void dbConstrains(BdReceivingSku bdReceivingSku) throws Exception {
		bdReceivingSku.setErpNo(StringUtil.dbSafeString(bdReceivingSku.getErpNo(), true, 40));
		bdReceivingSku.setSkuCode(StringUtil.dbSafeString(bdReceivingSku.getSkuCode(), true, 40));
		bdReceivingSku.setSkuName(StringUtil.dbSafeString(bdReceivingSku.getSkuName(), true, 40));
    }
}