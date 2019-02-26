package com.iyeed.model.system;


import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemBrandUserBean;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemUserModel extends BaseModel {

    /**
    * 根据id取得系统管理员表
    * @param  systemUserId
    * @return
    */
    public SystemUser getSystemUserById(Integer systemUserId) throws Exception {
        return systemUserWriteDao.get(systemUserId);
    }

    /**
     * 保存系统管理员表
     * @param  systemUser
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer saveSystemUser(SystemUser systemUser) throws Exception {
        if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            systemUserStoreWriteDao.del(systemUser.getUserNo());
            SystemUserStore systemUserStore = new SystemUserStore();
            systemUserStore.setUserNo(systemUser.getUserNo());
            systemUserStore.setStoreNo(systemUser.getStoreNo());
            systemUserStoreWriteDao.save(systemUserStore);
        }
        return systemUserWriteDao.save(systemUser);
    }

    /**
    * 更新系统管理员表
    * @param  systemUser
    * @return
    */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSystemUser(SystemUser systemUser) throws Exception {
        if (systemUser.getUserType() != null && systemUser.getUserType() > 2 && systemUser.getUserNo() != null) {
            systemUserStoreWriteDao.del(systemUser.getUserNo());
            SystemUserStore systemUserStore = new SystemUserStore();
            systemUserStore.setUserNo(systemUser.getUserNo());
            systemUserStore.setStoreNo(systemUser.getStoreNo());
            systemUserStoreWriteDao.save(systemUserStore);
        }
        return systemUserWriteDao.update(systemUser);
    }

    public Integer updateLogins(Integer id) throws Exception {
        return systemUserWriteDao.updateLogins(id);
    }

    public Integer pageCount(Map<String, String> queryMap) throws Exception {
        return systemUserWriteDao.getCount(queryMap);
    }

    public List<SystemUser> page(Map<String, String> queryMap, Integer start, Integer size) throws Exception {
        return systemUserWriteDao.page(queryMap, start, size);
    }

    public Boolean del(Integer id) throws Exception {
        return this.systemUserWriteDao.del(id) > 0;
    }

    public SystemUser getSystemUserByNamePwd(String name, String password) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", password);
        return systemUserWriteDao.getByNamePwd(map);
    }

    /**
     * 根据用户名取用户
     * @param username
     * @return
     */
    public List<SystemUser> getSystemUserByName(String username) throws Exception {
        return systemUserReadDao.getByName(username);
    }

    public SystemUserBrand getSystemUserBrandByUsername(String username) throws Exception {
        return systemUserWriteDao.getByUsername(username);
    }

    public Integer saveSystemUserBrand(SystemUserBrand systemUserBrand) throws Exception {
        return systemUserWriteDao.insertUserBrand(systemUserBrand);
    }

    public Integer getBrandUserListCount(Map<String, Object> queryMap) throws Exception {
        return systemUserWriteDao.getBrandUserListCount(queryMap);
    }

    public List<SystemBrandUserBean> getBrandUserList(Map<String, Object> queryMap, Integer start, Integer size) throws Exception {
        return systemUserWriteDao.getBrandUserList(queryMap, start, size);
    }

}
