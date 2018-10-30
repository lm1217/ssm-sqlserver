package com.iyeed.service.stock.impl;

import com.alibaba.fastjson.JSON;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInvLog;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.stock.IBdStockInvLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "bdStockInvLogService")
public class BdStockInvLogServiceImpl extends BaseService implements IBdStockInvLogService {
	private static final Logger log = LoggerFactory.getLogger(BdStockInvLogServiceImpl.class);

    /**
     * 根据id取得库存日志表
     * @param  bdStockInvLogId
     * @return
     */
    @Override
    public ServiceResult<BdStockInvLog> getBdStockInvLogById(Integer bdStockInvLogId) {
        ServiceResult<BdStockInvLog> result = new ServiceResult<BdStockInvLog>();
        try {
            result.setResult(bdStockInvLogModel.getBdStockInvLogById(bdStockInvLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + bdStockInvLogId + "]取得库存日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + bdStockInvLogId + "]取得库存日志表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存库存日志表
     * @param  bdStockInvLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdStockInvLog(BdStockInvLog bdStockInvLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvLogModel.saveBdStockInvLog(bdStockInvLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存库存日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存库存日志表时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新库存日志表
     * @param  bdStockInvLog
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdStockInvLog(BdStockInvLog bdStockInvLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvLogModel.updateBdStockInvLog(bdStockInvLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新库存日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新库存日志表时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<BdStockInvLog>> getBdStockInvLogList(Map<String, Object> queryMap, PagerInfo pager) {
        ServiceResult<List<BdStockInvLog>> serviceResult = new ServiceResult<>();
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(bdStockInvLogModel.getBdStockInvLogListCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<BdStockInvLog> list = bdStockInvLogModel.getBdStockInvLogList(queryMap, start, size);

            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BdStockInvLogServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                    + " &param2:" + JSON.toJSONString(pager));
            log.error("[BdStockInvLogServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}