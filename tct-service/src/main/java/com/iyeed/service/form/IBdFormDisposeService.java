package com.iyeed.service.form;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.vo.GetDisposeListBean;

import java.util.List;

public interface IBdFormDisposeService {

    /**
     * 根据id取得bd_form_dispose对象
     * @param  bdFormDisposeId
     * @return
     */
    ServiceResult<BdFormDispose> getBdFormDisposeById(Integer bdFormDisposeId);

    ServiceResult<List<GetDisposeListBean>> getDisposeListByApplyNo(String applyNo);

    /**
     * 保存bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    ServiceResult<Integer> saveBdFormDispose(BdFormDispose bdFormDispose);
     
    /**
     * 更新bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    ServiceResult<Integer> updateBdFormDispose(BdFormDispose bdFormDispose);
}