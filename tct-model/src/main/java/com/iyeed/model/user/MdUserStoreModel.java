package com.iyeed.model.user;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MdUserStoreModel extends BaseModel {

    /**
     * 根据id取得md_user_store对象
     * @param  mdUserStoreId
     * @return
     */
    public MdUserStore getMdUserStoreById(Integer mdUserStoreId) throws Exception {
    	return mdUserStoreWriteDao.get(mdUserStoreId);
    }

    public List<MdUserStore> getMdUserStoreListByUserNo(String userNo) throws Exception {
        return mdUserStoreWriteDao.getUserStoreListByUserNo(userNo);
    }

    public List<MdUserStore> getMdUserStoreListByUserNoArr(String[] userNoArr) throws Exception {
        return mdUserStoreWriteDao.getUserStoreListByUserNoArr(userNoArr);
    }

    /**
     * 保存md_user_store对象
     * @param  mdUserStore
     * @return
     */
    public Integer saveMdUserStore(MdUserStore mdUserStore) throws Exception {
     	this.dbConstrains(mdUserStore);
     	return mdUserStoreWriteDao.insert(mdUserStore);
    }
     
    /**
     * 更新md_user_store对象
     * @param  mdUserStore
     * @return
     */
    public Integer updateMdUserStore(MdUserStore mdUserStore) throws Exception {
        this.dbConstrains(mdUserStore);
     	return mdUserStoreWriteDao.update(mdUserStore);
    }
     
    private void dbConstrains(MdUserStore mdUserStore) throws Exception {
		mdUserStore.setUserNo(StringUtil.dbSafeString(mdUserStore.getUserNo(), true, 40));
		mdUserStore.setStoreNo(StringUtil.dbSafeString(mdUserStore.getStoreNo(), true, 40));
    }
}