package com.iyeed.service.form;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.entity.receive.BdReceiving;

import java.util.List;
import java.util.Map;

public interface IBdFormService {

    /**
     * 根据id取得表单-总表
     * @param  bdFormId
     * @return
     */
    ServiceResult<BdForm> getBdFormById(Integer bdFormId);

    ServiceResult<BdForm> getBdFormByApplyNo(String applyNo);

    /**
     * 分页获取申请表单列表
     * @param  queryMap
     * @param  pagerInfo
     * @return
     */
    ServiceResult<List<GetDisposeFormListBean>> getBdFormList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<Integer> getBdFormListCount(Map<String, Object> queryMap);

    ServiceResult<List<GetDestroyFormListBean>> getDestroyFormList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<Integer> getDestroyFormListCount(Map<String, Object> queryMap);

    ServiceResult<List<GetDestroyTheftFormListBean>> getDestroyTheftFormList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<Integer> getDestroyTheftFormListCount(Map<String, Object> queryMap);

    ServiceResult<List<GetDSIFormListBean>> getDSIFormList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<Integer> getDSIFormListCount(Map<String, Object> queryMap);

    ServiceResult<List<ExceptionReportBean>> getExceptionReportList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<List<ExceptionReportBean>> exportExceptionReportExcel(Map<String, Object> queryMap);

    ServiceResult<List<GetDestroyFormListBean>> exportDestroyReportExcel(Map<String, Object> queryMap);

    ServiceResult<List<GetDestroyTheftFormListBean>> exportDestroyTheftReportExcel(Map<String, Object> queryMap);

    ServiceResult<List<GetDSIFormListBean>> exportDSIReportExcel(Map<String, Object> queryMap);

    ServiceResult<List<StockReportBean>> getStockReportList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    ServiceResult<List<StockReportBean>> exportStockReportExcel(Map<String, Object> queryMap);

    /**
     * 保存表单-总表
     * @param  bdForm
     * @return
     */
    ServiceResult<Integer> saveBdForm(BdForm bdForm);

    ServiceResult<Integer> saveForm(SaveApplyForm form);

    ServiceResult<Integer> saveLocalForm(SaveApplyForm form);

    ServiceResult<Integer> sendFormMail();

    ServiceResult<Integer> changeAudit();

    ServiceResult<Integer> execDisposeFormDetails(BdForm bdForm, ExecDisposeForm form);
     
    /**
     * 更新表单-总表
     * @param  bdForm
     * @return
     */
    ServiceResult<Integer> updateBdForm(BdForm bdForm);

    ServiceResult<Integer> backFormDispose(BdForm bdForm, ExecDisposeForm form);

    ServiceResult<Integer> delBdForm(BdForm bdForm);
}