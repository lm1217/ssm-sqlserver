package com.iyeed.service.receive;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.vo.UpdateReceiveForm;

import java.util.List;
import java.util.Map;

public interface IBdReceivingService {

    /**
     * 根据id取得收货表-总表
     * @param  bdReceivingId
     * @return
     */
    ServiceResult<BdReceiving> getBdReceivingById(Integer bdReceivingId);

    /**
     * 分页获取收货列表
     * @param  queryMap
     * @param  pagerInfo
     * @return
     */
    ServiceResult<List<BdReceiving>> getBdReceivingList(Map<String, Object> queryMap, PagerInfo pagerInfo);

    /**
     * 获取收货列表总数
     * @param  queryMap
     * @return
     */
    ServiceResult<Integer> getBdReceivingListCount(Map<String, Object> queryMap);

    /**
     * 保存收货表-总表
     * @param  bdReceiving
     * @return
     */
    ServiceResult<Integer> saveBdReceiving(BdReceiving bdReceiving);
     
    /**
     * 更新收货表-总表
     * @param  form
     * @return
     */
    ServiceResult<Integer> updateBdReceiving(UpdateReceiveForm form);
}