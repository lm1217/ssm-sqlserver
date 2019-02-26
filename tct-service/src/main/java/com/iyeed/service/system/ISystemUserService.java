package com.iyeed.service.system;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.vo.SystemBrandUserBean;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:系统用户管理
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
public interface ISystemUserService {

    /**
     * 根据id取得系统管理员表
     * @param  systemUserId
     * @return
     */
    ServiceResult<SystemUser> getSystemUserById(Integer systemUserId);

    /**
     * 保存系统管理员表
     * @param  systemUser
     * @return
     */
    ServiceResult<Integer> saveSystemUser(SystemUser systemUser);

    /**
    * 更新系统管理员表
    * @param  systemUser
    * @return
    */
    ServiceResult<Integer> updateSystemUser(SystemUser systemUser);

    ServiceResult<Integer> updateLogins(Integer id);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    ServiceResult<List<SystemUser>> page(Map<String, String> queryMap, PagerInfo pager);

    /**
     * 删除
     * @param id
     * @return
     */
    ServiceResult<Boolean> del(Integer id);

    ServiceResult<SystemUser> getSystemUserByNamePwd(String name, String password);

    /**
     * 根据用户名取用户
     * @param name
     * @return
     */
    ServiceResult<List<SystemUser>> getSystemUserByName(String name);

    ServiceResult<SystemUserBrand> getSystemUserBrandByUsername(String username);

    ServiceResult<Integer> saveSystemUserBrand(SystemUserBrand systemUserBrand);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    ServiceResult<List<SystemBrandUserBean>> getBrandUserList(Map<String, Object> queryMap, PagerInfo pager);
}