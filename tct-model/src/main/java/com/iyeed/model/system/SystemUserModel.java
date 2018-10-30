package com.iyeed.model.system;


import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

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
    public Integer saveSystemUser(SystemUser systemUser) throws Exception {
        return systemUserWriteDao.save(systemUser);
    }

    /**
    * 更新系统管理员表
    * @param  systemAdmin
    * @return
    */

    public Integer updateSystemUser(SystemUser systemAdmin) throws Exception {
        return systemUserWriteDao.update(systemAdmin);
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

}
