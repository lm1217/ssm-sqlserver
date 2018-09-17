package com.iyeed.service.system;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemRole;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:系统角色管理
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
public interface ISystemRoleService {

    /**
     * 根据id取得角色表
     * @param  systemRoleId
     * @return
     */
    ServiceResult<SystemRole> getSystemRoleById(Integer systemRoleId);

    /**
     * 保存角色表
     * @param  systemRole
     * @return
     */
    ServiceResult<Integer> saveSystemRole(SystemRole systemRole);

    /**
    * 更新角色表
    * @param  systemRole
    * @return
    */
    ServiceResult<Integer> updateSystemRole(SystemRole systemRole);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    ServiceResult<List<SystemRole>> page(Map<String, String> queryMap, PagerInfo pager);

    /**
     * 删除
     * @param id
     * @return
     */
    ServiceResult<Boolean> del(Integer id);
}