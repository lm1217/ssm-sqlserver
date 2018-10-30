package com.iyeed.service.form.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.form.IBdFormSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "bdFormSkuService")
public class BdFormSkuServiceImpl extends BaseService implements IBdFormSkuService {
	private static final Logger log = LoggerFactory.getLogger(BdFormSkuServiceImpl.class);

    @Override
    public ServiceResult<List<BdFormSku>> getBdFormSkuListByApplyNo(String applyNo) {
        ServiceResult<List<BdFormSku>> result = new ServiceResult<>();
        try {
            result.setResult(bdFormSkuModel.getBdFormSkuListByApplyNo(applyNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormSkuService][getBdFormSkuById]根据id["+applyNo+"]取得表单-SKU子表 时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormSkuService][getBdFormSkuById]根据id["+applyNo+"]取得表单-SKU子表 时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<BdFormSku>> getBdFormSkuListForBack(String applyNo, String storeNo) {
        ServiceResult<List<BdFormSku>> result = new ServiceResult<>();
        try {
            result.setResult(bdFormSkuModel.getBdFormSkuListForBack(applyNo, storeNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 根据id取得表单-SKU子表 
     * @param  bdFormSkuId
     * @return
     */
    @Override
    public ServiceResult<BdFormSku> getBdFormSkuById(Integer bdFormSkuId) {
        ServiceResult<BdFormSku> result = new ServiceResult<BdFormSku>();
        try {
            result.setResult(bdFormSkuModel.getBdFormSkuById(bdFormSkuId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormSkuService][getBdFormSkuById]根据id["+bdFormSkuId+"]取得表单-SKU子表 时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormSkuService][getBdFormSkuById]根据id["+bdFormSkuId+"]取得表单-SKU子表 时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdFormSku(BdFormSku bdFormSku) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormSkuModel.saveBdFormSku(bdFormSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormSkuService][saveBdFormSku]保存表单-SKU子表 时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormSkuService][saveBdFormSku]保存表单-SKU子表 时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdFormSku(BdFormSku bdFormSku) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormSkuModel.updateBdFormSku(bdFormSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormSkuService][updateBdFormSku]更新表单-SKU子表 时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormSkuService][updateBdFormSku]更新表单-SKU子表 时出现未知异常：",
                e);
        }
        return result;
    }
}