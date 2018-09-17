package com.iyeed.service.form;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.ExecDisposeForm;
import com.iyeed.core.entity.form.vo.GetDisposeFormListBean;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
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

    /**
     * 分页获取申请表单列表
     * @param  queryMap
     * @param  pagerInfo
     * @return
     */
    ServiceResult<List<GetDisposeFormListBean>> getBdFormList(Map<String, String> queryMap, PagerInfo pagerInfo);

    ServiceResult<Integer> getBdFormListCount(Map<String, String> queryMap);
    /**
     * 保存表单-总表
     * @param  bdForm
     * @return
     */
    ServiceResult<Integer> saveBdForm(BdForm bdForm);

    ServiceResult<Integer> saveForm(SaveApplyForm form);

    ServiceResult<Integer> execDisposeFormDetails(BdForm bdForm, ExecDisposeForm form);
     
    /**
     * 更新表单-总表
     * @param  bdForm
     * @return
     */
    ServiceResult<Integer> updateBdForm(BdForm bdForm);
}