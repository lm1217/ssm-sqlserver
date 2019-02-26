package com.iyeed.service.system.impl;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemUserStoreBean;
import com.iyeed.service.BaseService;
import com.iyeed.service.system.ISystemUserStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "systemUserStoreService")
public class SystemUserStoreServiceImpl extends BaseService implements ISystemUserStoreService {
    private static Logger log = LoggerFactory.getLogger(SystemUserStoreServiceImpl.class);

    @Override
    public ServiceResult<SystemUserStore> getSystemUserStoreByUserNo(String userNo) {
        ServiceResult<SystemUserStore> result = new ServiceResult<>();
        try {
            result.setResult(systemUserStoreModel.getSystemUserStoreByUserNo(userNo));
        } catch (Exception e) {
            log.error("根据userNo[" + userNo + "]取得数据时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据userNo[" + userNo + "]取得数据时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemUserStoreBean>> getSystemUserStoreListByStoreNo(String storeNo) {
        ServiceResult<List<SystemUserStoreBean>> result = new ServiceResult<>();
        try {
            result.setResult(systemUserStoreModel.getSystemUserStoreListByStoreNo(storeNo));
        } catch (Exception e) {
            log.error("根据storeNo[" + storeNo + "]取得数据时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据storeNo[" + storeNo + "]取得数据时出现未知异常");
        }
        return result;
    }
}
