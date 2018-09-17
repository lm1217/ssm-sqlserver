package com.iyeed.model.store;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;
import com.iyeed.dao.db.write.xzn.store.MdStoreWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MdStoreModel {
    
    @Resource
    private MdStoreWriteDao mdStoreWriteDao;
    
    /**
     * 根据id取得门店表
     * @param  mdStoreId
     * @return
     */
    public MdStore getMdStoreById(Integer mdStoreId) throws Exception {
    	return mdStoreWriteDao.get(mdStoreId);
    }

    public MdStore getMdStoreByStoreNo(String storeNo) throws Exception {
        return mdStoreWriteDao.getStoreByStoreNo(storeNo);
    }

    public List<GetStoreListBean> getStoreList() throws Exception {
        return mdStoreWriteDao.getStoreList();
    }
    
    /**
     * 保存门店表
     * @param  mdStore
     * @return
     */
    public Integer saveMdStore(MdStore mdStore) throws Exception {
     	this.dbConstrains(mdStore);
     	return mdStoreWriteDao.insert(mdStore);
    }
     
    /**
     * 更新门店表
     * @param  mdStore
     * @return
     */
    public Integer updateMdStore(MdStore mdStore) throws Exception {
        this.dbConstrains(mdStore);
     	return mdStoreWriteDao.update(mdStore);
    }
     
    private void dbConstrains(MdStore mdStore) throws Exception {
		mdStore.setStoreNo(StringUtil.dbSafeString(mdStore.getStoreNo(), true, 40));
		mdStore.setStoreName(StringUtil.dbSafeString(mdStore.getStoreName(), true, 40));
		mdStore.setBrandNo(StringUtil.dbSafeString(mdStore.getBrandNo(), true, 40));
		mdStore.setBrandName(StringUtil.dbSafeString(mdStore.getBrandName(), true, 40));
		mdStore.setBrandLogo(StringUtil.dbSafeString(mdStore.getBrandLogo(), true, 100));
    }
}