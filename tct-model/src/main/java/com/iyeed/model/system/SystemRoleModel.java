package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemRole;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SystemRoleModel extends BaseModel {

    /**
    * 根据id取得角色表
    * @param  systemRoleId
    * @return
    */
    public SystemRole getSystemRoleById(Integer systemRoleId) throws Exception {
        return systemRoleWriteDao.get(systemRoleId);
    }

    /**
     * 保存角色表
     * @param  systemRole
     * @return
     */
    public Integer saveSystemRole(SystemRole systemRole) throws Exception {
        return systemRoleWriteDao.save(systemRole);
    }

    /**
    * 更新角色表
    * @param  systemRole
    * @return
    */
    public Integer updateSystemRole(SystemRole systemRole) throws Exception {
        return systemRoleWriteDao.update(systemRole);
    }

    public Integer pageCount(Map<String, String> queryMap) throws Exception {
        return systemRoleWriteDao.getCount(queryMap);
    }

    public List<SystemRole> page(Map<String, String> queryMap, Integer start, Integer size) throws Exception {
        return systemRoleWriteDao.page(queryMap, start, size);
    }

    public Boolean del(Integer id) throws Exception {
        return systemRoleWriteDao.del(id) > 0;
    }
}
