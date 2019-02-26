package com.iyeed.service.form.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.form.IBdFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "bdFormService")
public class BdFormServiceImpl extends BaseService implements IBdFormService {
	private static final Logger log = LoggerFactory.getLogger(BdFormServiceImpl.class);

    @Override
    public ServiceResult<Integer> getBdFormListCount(Map<String, Object> queryMap) {
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
    public ServiceResult<List<GetDestroyFormListBean>> getDestroyFormList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<GetDestroyFormListBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getDestroyFormListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getDestroyFormList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getDestroyFormListCount(Map<String, Object> queryMap) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.getDestroyFormListCount(queryMap));
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
    public ServiceResult<List<GetDestroyTheftFormListBean>> getDestroyTheftFormList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<GetDestroyTheftFormListBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getDestroyTheftFormListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getDestroyTheftFormList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getDestroyTheftFormListCount(Map<String, Object> queryMap) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.getDestroyTheftFormListCount(queryMap));
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
    public ServiceResult<List<GetDSIFormListBean>> getDSIFormList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<GetDSIFormListBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getDSIFormListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getDSIFormList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据queryMap[" + queryMap.toString() + "]取得收货表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getDSIFormListCount(Map<String, Object> queryMap) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.getDSIFormListCount(queryMap));
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
    public ServiceResult<List<GetDestroyTheftFormListBean>> exportDestroyTheftReportExcel(Map<String, Object> queryMap) {
        ServiceResult<List<GetDestroyTheftFormListBean>> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(bdFormModel.getDestroyTheftFormList(queryMap, null, null));
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

    @Override
    public ServiceResult<List<GetDSIFormListBean>> exportDSIReportExcel(Map<String, Object> queryMap) {
        ServiceResult<List<GetDSIFormListBean>> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(bdFormModel.getDSIFormList(queryMap, null, null));
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

    @Override
    public ServiceResult<List<StockReportBean>> getStockReportList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<StockReportBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getStockReportListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getStockReportList(queryMap, start, size));
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

    @Override
    public ServiceResult<List<StockReportBean>> exportStockReportExcel(Map<String, Object> queryMap) {
        ServiceResult<List<StockReportBean>> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(bdFormModel.getStockReportList(queryMap, null, null));
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

    @Override
    public ServiceResult<BdForm> getBdFormByApplyNo(String applyNo) {
        ServiceResult<BdForm> result = new ServiceResult<BdForm>();
        try {
            result.setResult(bdFormModel.getBdFormByApplyNo(applyNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormService][getBdFormById]根据id["+applyNo+"]取得表单-总表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormService][getBdFormById]根据id["+applyNo+"]取得表单-总表时出现未知异常：",
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
    public ServiceResult<List<GetDisposeFormListBean>> getBdFormList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
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

    @Override
    public ServiceResult<List<ExceptionReportBean>> getExceptionReportList(Map<String, Object> queryMap, PagerInfo pagerInfo) {
        ServiceResult<List<ExceptionReportBean>> serviceResult = new ServiceResult<>();
        serviceResult.setPager(pagerInfo);
        try {
            Integer start = 0, size = 0;
            if (pagerInfo != null) {
                pagerInfo.setRowsCount(bdFormModel.getExceptionReportListCount(queryMap));
                start = pagerInfo.getStart();
                size = pagerInfo.getPageSize();
            }
            serviceResult.setResult(bdFormModel.getExceptionReportList(queryMap, start, size));
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

    @Override
    public ServiceResult<List<ExceptionReportBean>> exportExceptionReportExcel(Map<String, Object> queryMap) {
        ServiceResult<List<ExceptionReportBean>> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(bdFormModel.getExceptionReportList(queryMap, null, null));
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

    @Override
    public ServiceResult<List<GetDestroyFormListBean>> exportDestroyReportExcel(Map<String, Object> queryMap) {
        ServiceResult<List<GetDestroyFormListBean>> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(bdFormModel.getDestroyFormList(queryMap, null, null));
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
    public ServiceResult<Integer> backFormDispose(BdForm bdForm, ExecDisposeForm form) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.backFormDispose(bdForm, form));
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
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_FORM_SYSERROR);
            log.error("[IBdFormService][saveBdForm]保存表单-总表时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveLocalForm(SaveApplyForm form) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.saveLocalForm(form));
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

    @Override
    public ServiceResult<Integer> sendFormMail() {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.sendFormMail());
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

    @Override
    public ServiceResult<Integer> changeAudit() {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.changeAudit());
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

    @Override
    public ServiceResult<Integer> delBdForm(BdForm bdForm) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormModel.delBdForm(bdForm));
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
}