package com.iyeed.service.stock.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.stock.BdStockInvData;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.stock.BdStockInvDataModel;
import com.iyeed.service.BaseService;
import com.iyeed.service.stock.IBdStockInvDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "bdStockInvDataService")
public class BdStockInvDataServiceImpl extends BaseService implements IBdStockInvDataService {
	private static final Logger log = LoggerFactory.getLogger(BdStockInvDataServiceImpl.class);

    /**
     * 根据id取得业务-库存表
     * @param  bdStockInvDataId
     * @return
     */
    @Override
    public ServiceResult<BdStockInvData> getBdStockInvDataById(Integer bdStockInvDataId) {
        ServiceResult<BdStockInvData> result = new ServiceResult<BdStockInvData>();
        try {
            result.setResult(bdStockInvDataModel.getBdStockInvDataById(bdStockInvDataId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + bdStockInvDataId + "]取得业务-库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + bdStockInvDataId + "]取得业务-库存表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存业务-库存表
     * @param  bdStockInvData
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdStockInvData(BdStockInvData bdStockInvData) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvDataModel.saveBdStockInvData(bdStockInvData));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存业务-库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存业务-库存表时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新业务-库存表
     * @param  bdStockInvData
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdStockInvData(BdStockInvData bdStockInvData) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvDataModel.updateBdStockInvData(bdStockInvData));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新业务-库存表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新业务-库存表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 备份库存表
     * @return
     */
    @Override
    public ServiceResult<Integer> insertForBackup() {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdStockInvDataModel.insertForBackup());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("备份库存表时出现未知异常：{}" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("备份库存表时出现未知异常：", e);
        }
        return result;
    }
}