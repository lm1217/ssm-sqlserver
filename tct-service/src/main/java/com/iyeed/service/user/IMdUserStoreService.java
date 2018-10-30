package com.iyeed.service.user;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.user.MdUserStore;

import java.util.List;

public interface IMdUserStoreService {

    /**
     * 根据id取得md_user_store对象
     * @param  mdUserStoreId
     * @return
     */
    ServiceResult<MdUserStore> getMdUserStoreById(Integer mdUserStoreId);

    ServiceResult<List<MdUserStore>> getMdUserStoreListByUserNo(String userNo);

    ServiceResult<List<MdUserStore>> getMdUserStoreListByUserNoArr(String[] userNoArr);
    
    /**
     * 保存md_user_store对象
     * @param  mdUserStore
     * @return
     */
    ServiceResult<Integer> saveMdUserStore(MdUserStore mdUserStore);
     
    /**
     * 更新md_user_store对象
     * @param  mdUserStore
     * @return
     */
    ServiceResult<Integer> updateMdUserStore(MdUserStore mdUserStore);
}