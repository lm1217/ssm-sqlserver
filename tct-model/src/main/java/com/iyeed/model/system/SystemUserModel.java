package com.iyeed.model.system;


import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.dao.db.read.xzn.system.SystemUserReadDao;
import com.iyeed.dao.db.write.xzn.system.SystemRoleWriteDao;
import com.iyeed.dao.db.write.xzn.system.SystemUserWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemUserModel {

    @Resource
    private SystemUserReadDao systemUserReadDao;
    @Resource
    private SystemUserWriteDao systemUserWriteDao;
    @Resource
    private SystemRoleWriteDao systemRoleWriteDao;

    /**
    * 根据id取得系统管理员表
    * @param  systemUserId
    * @return
    */
    public SystemUser getSystemUserById(Integer systemUserId) {
        return systemUserWriteDao.get(systemUserId);
    }

    /**
     * 保存系统管理员表
     * @param  systemUser
     * @return
     */
    public Integer saveSystemUser(SystemUser systemUser) {
        return systemUserWriteDao.save(systemUser);
    }

    /**
    * 更新系统管理员表
    * @param  systemAdmin
    * @return
    */

    public Integer updateSystemUser(SystemUser systemAdmin) {
        return systemUserWriteDao.update(systemAdmin);
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return systemUserWriteDao.getCount(queryMap);
    }

    public List<SystemUser> page(Map<String, String> queryMap, Integer start, Integer size) {
        List<SystemUser> list = systemUserWriteDao.page(queryMap, start, size);

        return list;
    }

    public Boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除系统管理员表[" + id + "]时出错");
        return this.systemUserWriteDao.del(id) > 0;
    }

    public SystemUser getSystemUserByNamePwd(String name, String password) {
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
    public List<SystemUser> getSystemUserByName(String username) {
        return systemUserReadDao.getByName(username);
    }

}
