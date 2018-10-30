package com.iyeed.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemRole;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.system.ISystemRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "systemRoleService")
public class SystemRoleServiceImpl extends BaseService implements ISystemRoleService {
    private static Logger log = LoggerFactory.getLogger(SystemRoleServiceImpl.class);

    /**
    * 根据id取得角色表
    * @param  systemRoleId
    * @return
    */
    @Override
    public ServiceResult<SystemRole> getSystemRoleById(Integer systemRoleId) {
        ServiceResult<SystemRole> result = new ServiceResult<SystemRole>();
        try {
            result.setResult(systemRoleModel.getSystemRoleById(systemRoleId));
        } catch (Exception e) {
            log.error("根据id[" + systemRoleId + "]取得角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemRoleId + "]取得角色表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存角色表
     * @param  systemRole
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSystemRole(SystemRole systemRole) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemRoleModel.saveSystemRole(systemRole));
            result.setMessage("保存成功");
        } catch (Exception e) {
            log.error("保存角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存角色表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新角色表
    * @param  systemRole
    * @return
    */
    @Override
    public ServiceResult<Integer> updateSystemRole(SystemRole systemRole) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemRoleModel.updateSystemRole(systemRole));
            result.setMessage("修改成功");
        } catch (Exception e) {
            log.error("更新角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新角色表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemRole>> page(Map<String, String> queryMap, PagerInfo pager) {
        ServiceResult<List<SystemRole>> serviceResult = new ServiceResult<List<SystemRole>>();
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemRoleModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<SystemRole> list = systemRoleModel.page(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemRolesServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemRolesServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(systemRoleModel.del(id));
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemRoleServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return serviceResult;
    }
}