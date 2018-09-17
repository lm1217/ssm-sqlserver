package com.iyeed.service.stock.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvSkuListBean;
import com.iyeed.core.entity.stock.vo.StockInvSkuForm;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.stock.BdStockInvModel;
import com.iyeed.service.stock.IBdStockInvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "bdStockInvService")
public class BdStockInvServiceImpl implements IBdStockInvService {
	private static final Logger log = LoggerFactory.getLogger(BdStockInvServiceImpl.class);

	@Resource
    private BdStockInvModel bdStockInvModel;

    /**
     * 根据id取得库存表
     * @param  bdStockInvId
     * @return
     */
    @Override
    public ServiceResult<BdStockInv> getBdStockInvById(Integer bdStockInvId) {
        ServiceResult<BdStockInv> result = new ServiceResult<BdStockInv>();
        try {
            result.setResult(bdStockInvModel.getBdStockInvById(bdStockInvId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][getBdStockInvById]根据id["+bdStockInvId+"]取得库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][getBdStockInvById]根据id["+bdStockInvId+"]取得库存表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetStockInvSkuListBean>> getStockInvSkuListByStoreNo(String storeNo) {
        ServiceResult<List<GetStockInvSkuListBean>> result = new ServiceResult<>();
        try {
            result.setResult(bdStockInvModel.getStockInvSkuListByStoreNo(storeNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][getBdStockInvById]根据id["+storeNo+"]取得库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][getBdStockInvById]根据id["+storeNo+"]取得库存表时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetStockInvReportListBean>> getStockInvReportList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<GetStockInvReportListBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdStockInvModel.getStockInvReportListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdStockInvModel.getStockInvReportList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("根据queryMap[" + queryMap.toString() + "]获取数据时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据queryMap[" + queryMap.toString() + "]获取数据时出现未知异常：", e);
        }
        return serviceResult;
    }

    /**
     * 保存库存表
     * @param  bdStockInv
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdStockInv(BdStockInv bdStockInv) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvModel.saveBdStockInv(bdStockInv));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][saveBdStockInv]保存库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][saveBdStockInv]保存库存表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新库存表
     * @param  bdStockInv
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdStockInv(BdStockInv bdStockInv) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvModel.updateBdStockInv(bdStockInv));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateDepotToCounter(StockInvSkuForm skuForm) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvModel.updateDepotToCounter(skuForm));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateCounterToDepot(StockInvSkuForm skuForm) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvModel.updateCounterToDepot(skuForm));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdStockInvService][updateBdStockInv]更新库存表时出现未知异常：",
                    e);
        }
        return result;
    }
}