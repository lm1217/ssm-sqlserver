package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemRole;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.dao.db.write.xzn.system.SystemRoleWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SystemRoleModel {

    @Resource
    private SystemRoleWriteDao systemRoleWriteDao;

    /**
    * 根据id取得角色表
    * @param  systemRoleId
    * @return
    */
    public SystemRole getSystemRoleById(Integer systemRoleId) {
        return systemRoleWriteDao.get(systemRoleId);
    }

    /**
     * 保存角色表
     * @param  systemRole
     * @return
     */
    public Integer saveSystemRole(SystemRole systemRole) {
        return systemRoleWriteDao.save(systemRole);
    }

    /**
    * 更新角色表
    * @param  systemRole
    * @return
    */
    public Integer updateSystemRole(SystemRole systemRole) {
        return systemRoleWriteDao.update(systemRole);
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return systemRoleWriteDao.getCount(queryMap);
    }

    public List<SystemRole> page(Map<String, String> queryMap, Integer start, Integer size) {
        return systemRoleWriteDao.page(queryMap, start, size);
    }

    public Boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除角色表[" + id + "]时出错");
        return systemRoleWriteDao.del(id) > 0;
    }
}
