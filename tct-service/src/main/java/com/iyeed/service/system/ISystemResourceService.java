package com.iyeed.service.system;

import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemResource;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:系统资源管理
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
public interface ISystemResourceService {

    /**
     * 根据id取得资源表
     * @param  systemResourceId
     * @return
     */
    ServiceResult<SystemResource> getSystemResourceById(Integer systemResourceId);

    /**
     * 保存资源表
     * @param  systemResource
     * @return
     */
    ServiceResult<Integer> saveSystemResource(SystemResource systemResource);

    /**
    * 更新资源表
    * @param  systemResource
    * @return
    */
    ServiceResult<Integer> updateSystemResource(SystemResource systemResource);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    ServiceResult<List<SystemResource>> page(Map<String, String> queryMap, PagerInfo pager);

    /**
     * 删除
     * @param id
     * @return
     */
    ServiceResult<Boolean> del(Integer id);

    ServiceResult<List<SystemResource>> getByPid(Integer pid);
}