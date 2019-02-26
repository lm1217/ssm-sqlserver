package com.iyeed.model.stock;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvSkuListBean;
import com.iyeed.core.entity.stock.vo.StockInvSkuForm;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BdStockInvModel extends BaseModel {
    
    /**
     * 根据id取得库存表
     * @param  bdStockInvId
     * @return
     */
    public BdStockInv getBdStockInvById(Integer bdStockInvId) throws Exception {
    	return bdStockInvWriteDao.get(bdStockInvId);
    }

    public List<GetStockInvSkuListBean> getStockInvSkuListByStoreNo(String storeNo) throws Exception {
        return bdStockInvWriteDao.getStockInvSkuListByStoreNo(storeNo);
    }

    public List<GetStockInvSkuListBean> getStockInvSkuList(Map<String, Object> queryMap) throws Exception {
        return bdStockInvWriteDao.getStockInvSkuList(queryMap);
    }

    public List<GetStockInvSkuListBean> getStockInvExceptionSkuList(Map<String, Object> queryMap) throws Exception {
        return bdStockInvWriteDao.getStockInvExceptionSkuList(queryMap);
    }

    /**
     * 获取库存报表列表
     * @param  queryMap
     * @param  start
     * @param  size
     * @return
     */
    public List<GetStockInvReportListBean> getStockInvReportList(Map<String, Object> queryMap,
                                                         Integer start, Integer size) throws Exception {
        return bdStockInvWriteDao.getStockInvReportList(queryMap, start, size);
    }

    /**
     * 获取库存报表列表总数
     * @param  queryMap
     * @return
     */
    public Integer getStockInvReportListCount(Map<String, Object> queryMap) throws Exception{
        return bdStockInvWriteDao.getStockInvReportListCount(queryMap);
    }

    public List<GetStockInvReportListBean> exportStockInvReportExcel(Map<String, Object> queryMap) throws Exception {
        return bdStockInvWriteDao.exportStockInvReportExcel(queryMap);
    }

    /**
     * 保存库存表
     * @param  bdStockInv
     * @return
     */
    public Integer saveBdStockInv(BdStockInv bdStockInv) throws Exception {
     	this.dbConstrains(bdStockInv);
     	return bdStockInvWriteDao.insert(bdStockInv);
    }

    public Integer saveUpdateBdStockInv(BdStockInv bdStockInv) throws Exception {
        this.dbConstrains(bdStockInv);
        return bdStockInvWriteDao.insertOrUpdate(bdStockInv);
    }
     
    /**
     * 更新库存表
     * @param  bdStockInv
     * @return
     */
    public Integer updateBdStockInv(BdStockInv bdStockInv) throws Exception {
        this.dbConstrains(bdStockInv);
     	return bdStockInvWriteDao.update(bdStockInv);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateDepotToCounter(StockInvSkuForm skuForm) throws Exception {
        Integer update = 0;
        for (StockInvSkuForm.Sku sku : skuForm.getSkuList()) {
            bdStockInvLogWriteDao.insertForStockInvDepotToCounter(sku.getId(), 1, sku.getMoveNum());
            update = bdStockInvWriteDao.updateDepotToCounter(sku.getId(), sku.getMoveNum(), new Date());
        }
        return update;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateCounterToDepot(StockInvSkuForm skuForm) throws Exception {
        Integer update = 0;
        for (StockInvSkuForm.Sku sku : skuForm.getSkuList()) {
            bdStockInvLogWriteDao.insertForStockInvCounterToDepot(sku.getId(), 2, sku.getMoveNum());
            update = bdStockInvWriteDao.updateCounterToDepot(sku.getId(), sku.getMoveNum(), new Date());
        }
        return update;
    }
     
    private void dbConstrains(BdStockInv bdStockInv) throws Exception {
		bdStockInv.setStoreNo(StringUtil.dbSafeString(bdStockInv.getStoreNo(), true, 40));
		bdStockInv.setStoreName(StringUtil.dbSafeString(bdStockInv.getStoreName(), true, 40));
		bdStockInv.setSkuCode(StringUtil.dbSafeString(bdStockInv.getSkuCode(), true, 40));
		bdStockInv.setSkuName(StringUtil.dbSafeString(bdStockInv.getSkuName(), true, 200));
    }
}