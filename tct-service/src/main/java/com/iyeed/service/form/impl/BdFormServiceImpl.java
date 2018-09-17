package com.iyeed.service.form.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.ExecDisposeForm;
import com.iyeed.core.entity.form.vo.GetDisposeFormListBean;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.form.BdFormModel;
import com.iyeed.service.form.IBdFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "bdFormService")
public class BdFormServiceImpl implements IBdFormService {
	private static final Logger log = LoggerFactory.getLogger(BdFormServiceImpl.class);

	@Resource
    private BdFormModel bdFormModel;

    @Override
    public ServiceResult<Integer> getBdFormListCount(Map<String, String> queryMap) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.getBdFormListCount(queryMap));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("处理申请表单时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("处理申请表单时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 根据id取得表单-总表
     * @param  bdFormId
     * @return
     */
    @Override
    public ServiceResult<BdForm> getBdFormById(Integer bdFormId) {
        ServiceResult<BdForm> result = new ServiceResult<BdForm>();
        try {
            result.setResult(bdFormModel.getBdFormById(bdFormId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormService][getBdFormById]根据id["+bdFormId+"]取得表单-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormService][getBdFormById]根据id["+bdFormId+"]取得表单-总表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> execDisposeFormDetails(BdForm bdForm, ExecDisposeForm form) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.execDisposeFormDetails(bdForm, form));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("处理申请表单时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("处理申请表单时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetDisposeFormListBean>> getBdFormList(Map<String, String> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<GetDisposeFormListBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getBdFormListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getBdFormList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IBdReceivingService][getBdFormList]根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdReceivingService][getBdFormList]根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：", e);
        }
        return serviceResult;
    }

    /**
     * 保存表单-总表
     * @param  bdForm
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdForm(BdForm bdForm) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.saveBdForm(bdForm));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormService][saveBdForm]保存表单-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormService][saveBdForm]保存表单-总表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveForm(SaveApplyForm form) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.saveForm(form));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormService][saveBdForm]保存表单-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormService][saveBdForm]保存表单-总表时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 更新表单-总表
     * @param  bdForm
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdForm(BdForm bdForm) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.updateBdForm(bdForm));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormService][updateBdForm]更新表单-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormService][updateBdForm]更新表单-总表时出现未知异常：",
                e);
        }
        return result;
    }
}