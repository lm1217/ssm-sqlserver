package com.iyeed.service.receive.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.receive.IBdReceivingSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "bdReceivingSkuService")
public class BdReceivingSkuServiceImpl extends BaseService implements IBdReceivingSkuService {
	private static final Logger log = LoggerFactory.getLogger(BdReceivingSkuServiceImpl.class);

    /**
     * 根据id取得收货表-SKU子表
     * @param  bdReceivingSkuId
     * @return
     */
    @Override
    public ServiceResult<BdReceivingSku> getBdReceivingSkuById(Integer bdReceivingSkuId) {
        ServiceResult<BdReceivingSku> result = new ServiceResult<BdReceivingSku>();
        try {
            result.setResult(bdReceivingSkuModel.getBdReceivingSkuById(bdReceivingSkuId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingSkuService][getBdReceivingSkuById]根据id["+bdReceivingSkuId+"]取得收货表-SKU子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingSkuService][getBdReceivingSkuById]根据id["+bdReceivingSkuId+"]取得收货表-SKU子表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 根据erpNo取得收货表-SKU子表列表
     * @param  erpNo
     * @return
     */
    @Override
    public ServiceResult<List<BdReceivingSku>> getBdReceivingSkuListByErpNo(String erpNo) {
        ServiceResult<List<BdReceivingSku>> result = new ServiceResult<>();
        try {
            result.setResult(bdReceivingSkuModel.getBdReceivingSkuListByErpNo(erpNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingSkuService][getBdReceivingSkuListByErpNo]根据erpNo["+erpNo+"]取得收货表-SKU子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingSkuService][getBdReceivingSkuListByErpNo]根据erpNo["+erpNo+"]取得收货表-SKU子表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdReceivingSku(BdReceivingSku bdReceivingSku) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdReceivingSkuModel.saveBdReceivingSku(bdReceivingSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingSkuService][saveBdReceivingSku]保存收货表-SKU子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingSkuService][saveBdReceivingSku]保存收货表-SKU子表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新收货表-SKU子表
     * @param  bdReceivingSku
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdReceivingSku(BdReceivingSku bdReceivingSku) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdReceivingSkuModel.updateBdReceivingSku(bdReceivingSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingSkuService][updateBdReceivingSku]更新收货表-SKU子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingSkuService][updateBdReceivingSku]更新收货表-SKU子表时出现未知异常：",
                e);
        }
        return result;
    }
}