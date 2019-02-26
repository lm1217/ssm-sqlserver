package com.iyeed.service.system;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemUserStoreBean;

import java.util.List;

public interface ISystemUserStoreService {
    ServiceResult<SystemUserStore> getSystemUserStoreByUserNo(String userNo);

    ServiceResult<List<SystemUserStoreBean>> getSystemUserStoreListByStoreNo(String storeNo);
}
