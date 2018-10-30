package com.iyeed.service.system;


import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:系统角色-资源管理
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
public interface ISystemRoleResourceService {

    /**
     * 根据id取得角色资源对应表
     * @param  systemRoleResourceId
     * @return
     */
    ServiceResult<SystemRoleResource> getSystemRoleResourceById(Integer systemRoleResourceId);

    /**
     * 保存角色资源对应表
     * @param  systemRoleResource
     * @return
     */
    ServiceResult<Integer> saveSystemRoleResource(SystemRoleResource systemRoleResource);

    /**
    * 更新角色资源对应表
    * @param  systemRoleResource
    * @return
    */
    ServiceResult<Integer> updateSystemRoleResource(SystemRoleResource systemRoleResource);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    ServiceResult<List<SystemRoleResource>> page(Map<String, String> queryMap, PagerInfo pager);

    /**
     * 删除
     * @param id
     * @return
     */
    ServiceResult<Boolean> del(Integer id);

    /**
     * 保存角色资源关系
     * @param roleId
     * @param resArr
     * @return
     */
    ServiceResult<Boolean> save(String roleId, String[] resArr);

    /**
     * 获取该角色下的所有资源列表
     * @param roleId
     * @return
     */
    ServiceResult<List<SystemResource>> getResourceByRoleId(Integer roleId);

    /**
     * 根据父资源ID、角色ID、使用范围获取资源
     * @param pid
     * @param roleId
     * @return
     */
    ServiceResult<List<SystemResource>> getResourceByPid(Integer pid, Integer roleId);
}