package com.iyeed.model.receive;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.core.entity.receive.vo.UpdateReceiveForm;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.model.BaseModel;
import com.iyeed.model.form.BdFormModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BdReceivingModel extends BaseModel {
    
    /**
     * 根据id取得收货表-总表
     * @param  bdReceivingId
     * @return
     */
    public BdReceiving getBdReceivingById(Integer bdReceivingId) throws Exception {
    	return bdReceivingWriteDao.get(bdReceivingId);
    }

    /**
     * 获取收货列表
     * @param  queryMap
     * @param  start
     * @param  size
     * @return
     */
    public List<BdReceiving> getBdReceivingList(Map<String, Object> queryMap,
                                   Integer start, Integer size) throws Exception {
        return bdReceivingWriteDao.getBdReceivingList(queryMap, start, size);
    }

    /**
     * 获取收货列表总数
     * @param  queryMap
     * @return
     */
    public Integer getBdReceivingListCount(Map<String, Object> queryMap) throws Exception{
        return bdReceivingWriteDao.getBdReceivingListCount(queryMap);
    }
    
    /**
     * 保存收货表-总表
     * @param  bdReceiving
     * @return
     */
    public Integer saveBdReceiving(BdReceiving bdReceiving) throws Exception {
     	this.dbConstrains(bdReceiving);
     	return bdReceivingWriteDao.insert(bdReceiving);
    }
     
    /**
     * 更新收货表-总表
     * @param  form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBdReceiving(UpdateReceiveForm form) throws Exception {
        BdReceiving bdReceiving = new BdReceiving();
        bdReceiving = bdReceivingWriteDao.get(form.getId());
        MdStore store = mdStoreWriteDao.getStoreByStoreNo(bdReceiving.getReceiveStoreNo());
        for (int i = 0; i < form.getBdReceivingSkuList().size(); i++) {
            BdReceivingSku sku = form.getBdReceivingSkuList().get(i);
            BdReceivingSku bdReceivingSku = bdReceivingSkuWriteDao.get(sku.getId());
            if (bdReceiving.getActSendTotal() == null) {
                bdReceiving.setActSendTotal(sku.getActSendTotal());
            } else {
                bdReceiving.setActSendTotal(bdReceiving.getActSendTotal() + sku.getActSendTotal());
            }
            bdReceivingSkuWriteDao.update(sku);

            BdStockInv stockInv = new BdStockInv();
            stockInv.setStoreNo(store.getStoreNo());
            stockInv.setStoreName(store.getStoreName());
            stockInv.setSkuCode(bdReceivingSku.getSkuCode());
            stockInv.setSkuName(bdReceivingSku.getSkuName());
            stockInv.setStockDepot(sku.getActSendTotal());
            stockInv.setStockDepotEnabled(sku.getActSendTotal());
            changeData(stockInv);
            bdStockInvWriteDao.insertOrUpdate(stockInv);
        }
        bdReceiving.setReceiveDate(new Date());
        bdReceiving.setStatus(form.getStatus());
     	return bdReceivingWriteDao.update(bdReceiving);
    }


     
    private void dbConstrains(BdReceiving bdReceiving) throws Exception {
		bdReceiving.setErpNo(StringUtil.dbSafeString(bdReceiving.getErpNo(), true, 40));
		bdReceiving.setOrderNo(StringUtil.dbSafeString(bdReceiving.getOrderNo(), true, 40));
		bdReceiving.setExpressCode(StringUtil.dbSafeString(bdReceiving.getExpressCode(), true, 40));
		bdReceiving.setSendStoreNo(StringUtil.dbSafeString(bdReceiving.getSendStoreNo(), true, 40));
		bdReceiving.setReceiveStoreNo(StringUtil.dbSafeString(bdReceiving.getReceiveStoreNo(), true, 40));
    }
}