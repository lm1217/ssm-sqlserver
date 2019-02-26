package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemUserStoreBean;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemUserStoreModel extends BaseModel {
    public SystemUserStore getSystemUserStoreByUserNo(String userNo) throws Exception {
        return systemUserStoreWriteDao.get(userNo);
    }

    public List<SystemUserStoreBean> getSystemUserStoreListByStoreNo(String storeNo) throws Exception {
        return systemUserStoreWriteDao.getSystemUserStoreListByStoreNo(storeNo);
    }

}
