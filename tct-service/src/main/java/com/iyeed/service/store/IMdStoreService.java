package com.iyeed.service.store;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;

import java.util.List;

public interface IMdStoreService {

    /**
     * 根据id取得门店表
     * @param  mdStoreId
     * @return
     */
    ServiceResult<MdStore> getMdStoreById(Integer mdStoreId);

    ServiceResult<MdStore> getMdStoreByStoreNo(String storeNo);

    /**
     * 根据id取得门店表
     * @param
     * @return
     */
    ServiceResult<List<GetStoreListBean>> getStoreList();

    ServiceResult<List<GetStoreListBean>> getStoreListByBrandNo(String brandNo);
    
    /**
     * 保存门店表
     * @param  mdStore
     * @return
     */
    ServiceResult<Integer> saveMdStore(MdStore mdStore);
     
    /**
     * 更新门店表
     * @param  mdStore
     * @return
     */
    ServiceResult<Integer> updateMdStore(MdStore mdStore);
}