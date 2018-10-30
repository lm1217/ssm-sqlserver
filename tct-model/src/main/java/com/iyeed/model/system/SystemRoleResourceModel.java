package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemRoleResourceModel extends BaseModel {

    /**
    * 根据id取得角色资源对应表
    * @param  systemRoleResourceId
    * @return
    */
    public SystemRoleResource getSystemRoleResourceById(Integer systemRoleResourceId) throws Exception {
        return systemRoleResourceWriteDao.get(systemRoleResourceId);
    }

    /**
     * 保存角色资源对应表
     * @param  systemRoleResource
     * @return
     */
    public Integer saveSystemRoleResource(SystemRoleResource systemRoleResource) throws Exception {
        return systemRoleResourceWriteDao.save(systemRoleResource);
    }

    /**
    * 更新角色资源对应表
    * @param  systemRoleResource
    * @return
    */
    public Integer updateSystemRoleResource(SystemRoleResource systemRoleResource) throws Exception {
        return systemRoleResourceWriteDao.update(systemRoleResource);
    }

    public Integer pageCount(Map<String, String> queryMap) throws Exception {
        return systemRoleResourceWriteDao.getCount(queryMap);
    }

    public List<SystemRoleResource> page(Map<String, String> queryMap) throws Exception {
        return systemRoleResourceWriteDao.page(queryMap);
    }

    public boolean del(Integer id) throws Exception {
        return systemRoleResourceWriteDao.del(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(String roleId, String[] resArr) throws Exception {
        //删除此角色之前的资源关联
        systemRoleResourceWriteDao.delByRole(roleId);

        //保存选中的资源
        for (String resId : resArr) {
            SystemRoleResource srr = new SystemRoleResource();
            srr.setResourceId(Integer.valueOf(resId));
            srr.setRoleId(Integer.valueOf(roleId));
            systemRoleResourceWriteDao.save(srr);
        }
        return true;
    }

    public List<SystemResource> getResourceByRoleId(Integer roleId) throws Exception {
        return systemRoleResourceWriteDao.getResourceByRoleId(roleId);
    }

    /**
     * <b>公用方法</b><br>
     * 此资源下所有有权限的子资源
     * @param pid
     * @param roleId
     * @return
     * @throws BusinessException
     */
    public List<SystemResource> getResourceByPid(Integer pid, Integer roleId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        map.put("roleId", roleId);
        return systemRoleResourceWriteDao.getResourceByPid(map);
    }
}
