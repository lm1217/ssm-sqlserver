package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemRoleResourceWriteDao {

    SystemRoleResource get(Integer id);

    Integer save(SystemRoleResource systemRolesResource);

    Integer update(SystemRoleResource systemRolesResource);

    Integer getCount(Map<String, String> queryMap);

    List<SystemRoleResource> page(Map<String, String> queryMap);

    Integer del(Integer id);

    /**
     * 此角色下的资源
     * @param roleId
     * @return
     */
    List<SystemResource> getResourceByRoleId(@Param("roleId") Integer roleId);

    /**
     * 此资源下的有权限的子资源
     * @param queryMap
     * @return
     */
    List<SystemResource> getResourceByPid(Map<String, Object> queryMap);

    /**
     * 删除该角色下的资源关联
     * @param roleId
     * @return
     */
    Integer delByRole(String roleId);

}
