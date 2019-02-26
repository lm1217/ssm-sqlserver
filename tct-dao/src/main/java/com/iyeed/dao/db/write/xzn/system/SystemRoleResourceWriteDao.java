package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemRoleResourceWriteDao {

    SystemRoleResource get(Integer id) throws Exception;

    Integer save(SystemRoleResource systemRolesResource) throws Exception;

    Integer update(SystemRoleResource systemRolesResource) throws Exception;

    Integer getCount(Map<String, String> queryMap) throws Exception;

    List<SystemRoleResource> page(Map<String, String> queryMap) throws Exception;

    Integer del(Integer id) throws Exception;

    /**
     * 此角色下的资源
     * @param roleId
     * @return
     */
    List<SystemResource> getResourceByRoleId(@Param("roleId") Integer roleId) throws Exception;

    /**
     * 此资源下的有权限的子资源
     * @param queryMap
     * @return
     */
    List<SystemResource> getResourceByPid(Map<String, Object> queryMap) throws Exception;

    /**
     * 删除该角色下的资源关联
     * @param roleId
     * @return
     */
    Integer delByRole(String roleId) throws Exception;

}
