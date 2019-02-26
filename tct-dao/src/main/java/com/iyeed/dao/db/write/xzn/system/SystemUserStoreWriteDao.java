package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemUserStoreBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserStoreWriteDao {
    SystemUserStore get(String userNo) throws Exception;

    List<SystemUserStoreBean> getSystemUserStoreListByStoreNo(String storeNo) throws Exception;

    Integer save(SystemUserStore systemUserStore) throws Exception;

    Integer del(String userNo) throws Exception;
}
