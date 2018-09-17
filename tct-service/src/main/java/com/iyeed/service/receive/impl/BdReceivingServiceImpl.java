package com.iyeed.service.receive.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.vo.UpdateReceiveForm;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.receive.BdReceivingModel;
import com.iyeed.service.receive.IBdReceivingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service(value = "bdReceivingService")
public class BdReceivingServiceImpl implements IBdReceivingService {
	private static final Logger log = LoggerFactory.getLogger(BdReceivingServiceImpl.class);

	@Resource
    private BdReceivingModel bdReceivingModel;

    /**
     * 根据id取得收货表-总表
     * @param  bdReceivingId
     * @return
     */
    @Override
    public ServiceResult<BdReceiving> getBdReceivingById(Integer bdReceivingId) {
        ServiceResult<BdReceiving> result = new ServiceResult<BdReceiving>();
        try {
            result.setResult(bdReceivingModel.getBdReceivingById(bdReceivingId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingService][getBdReceivingById]根据id["+bdReceivingId+"]取得收货表-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][getBdReceivingById]根据id["+bdReceivingId+"]取得收货表-总表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<BdReceiving>> getBdReceivingList(Map<String, String> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<BdReceiving>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdReceivingModel.getBdReceivingListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdReceivingModel.getBdReceivingList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IBdReceivingService][getBdReceivingList]根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][getBdReceivingList]根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getBdReceivingListCount(Map<String, String> queryMap) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(bdReceivingModel.getBdReceivingListCount(queryMap));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IBdReceivingService][getBdReceivingListCount]获取总数时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][getBdReceivingListCount]获取总数时出现未知异常：", e);
        }
        return serviceResult;
    }

    /**
     * 保存收货表-总表
     * @param  bdReceiving
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdReceiving(BdReceiving bdReceiving) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdReceivingModel.saveBdReceiving(bdReceiving));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingService][saveBdReceiving]保存收货表-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][saveBdReceiving]保存收货表-总表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新收货表-总表
     * @param  form
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdReceiving(UpdateReceiveForm form) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdReceivingModel.updateBdReceiving(form));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdReceivingService][updateBdReceiving]更新收货表-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][updateBdReceiving]更新收货表-总表时出现未知异常：",
                e);
        }
        return result;
    }
}