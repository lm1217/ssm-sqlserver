package com.iyeed.service.stock;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvSkuListBean;
import com.iyeed.core.entity.stock.vo.StockInvSkuForm;

import java.util.List;
import java.util.Map;

public interface IBdStockInvService {

    /**
     * 根据id取得库存表
     * @param  bdStockInvId
     * @return
     */
    ServiceResult<BdStockInv> getBdStockInvById(Integer bdStockInvId);

    ServiceResult<List<GetStockInvSkuListBean>> getStockInvSkuListByStoreNo(String storeNo);

    ServiceResult<List<GetStockInvSkuListBean>> getStockInvSkuList(Map<String, Object> queryMap);

    ServiceResult<List<GetStockInvSkuListBean>> getStockInvExceptionSkuList(Map<String, Object> queryMap);

    ServiceResult<List<GetStockInvReportListBean>> getStockInvReportList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<List<GetStockInvReportListBean>> exportStockInvReportExcel(Map<String, Object> queryMap);

    /**
     * 保存库存表
     * @param  bdStockInv
     * @return
     */
    ServiceResult<Integer> saveBdStockInv(BdStockInv bdStockInv);
     
    /**
     * 更新库存表
     * @param  bdStockInv
     * @return
     */
    ServiceResult<Integer> updateBdStockInv(BdStockInv bdStockInv);

    ServiceResult<Integer> updateDepotToCounter(StockInvSkuForm skuForm);

    ServiceResult<Integer> updateCounterToDepot(StockInvSkuForm skuForm);
}