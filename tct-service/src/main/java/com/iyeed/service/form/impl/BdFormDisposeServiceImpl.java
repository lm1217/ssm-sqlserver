package com.iyeed.service.form.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.vo.GetDisposeListBean;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.form.BdFormDisposeModel;
import com.iyeed.service.form.IBdFormDisposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "bdFormDisposeService")
public class BdFormDisposeServiceImpl implements IBdFormDisposeService {
	private static final Logger log = LoggerFactory.getLogger(BdFormDisposeServiceImpl.class);

	@Resource
    private BdFormDisposeModel bdFormDisposeModel;

    /**
     * 根据id取得bd_form_dispose对象
     * @param  bdFormDisposeId
     * @return
     */
    @Override
    public ServiceResult<BdFormDispose> getBdFormDisposeById(Integer bdFormDisposeId) {
        ServiceResult<BdFormDispose> result = new ServiceResult<BdFormDispose>();
        try {
            result.setResult(bdFormDisposeModel.getBdFormDisposeById(bdFormDisposeId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormDisposeService][getBdFormDisposeById]根据id["+bdFormDisposeId+"]取得bd_form_dispose对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormDisposeService][getBdFormDisposeById]根据id["+bdFormDisposeId+"]取得bd_form_dispose对象时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetDisposeListBean>> getDisposeListByApplyNo(String applyNo) {
        ServiceResult<List<GetDisposeListBean>> result = new ServiceResult<>();
        try {
            result.setResult(bdFormDisposeModel.getDisposeListByApplyNo(applyNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormDisposeService][getDisposeListByApplyNo]根据id["+applyNo+"]取得bd_form_dispose对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormDisposeService][getDisposeListByApplyNo]根据id["+applyNo+"]取得bd_form_dispose对象时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 保存bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdFormDispose(BdFormDispose bdFormDispose) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormDisposeModel.saveBdFormDispose(bdFormDispose));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormDisposeService][saveBdFormDispose]保存bd_form_dispose对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormDisposeService][saveBdFormDispose]保存bd_form_dispose对象时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdFormDispose(BdFormDispose bdFormDispose) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormDisposeModel.updateBdFormDispose(bdFormDispose));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormDisposeService][updateBdFormDispose]更新bd_form_dispose对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormDisposeService][updateBdFormDispose]更新bd_form_dispose对象时出现未知异常：",
                e);
        }
        return result;
    }
}