package com.iyeed.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.system.ISystemRoleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "systemRoleResourceService")
public class SystemRoleResourceServiceImpl extends BaseService implements ISystemRoleResourceService {
    private static Logger log = LoggerFactory.getLogger(SystemRoleResourceServiceImpl.class);

    @Override
    public ServiceResult<SystemRoleResource> getSystemRoleResourceById(Integer systemRoleResourceId) {
        ServiceResult<SystemRoleResource> result = new ServiceResult<SystemRoleResource>();
        try {
            result.setResult(systemRoleResourceModel.getSystemRoleResourceById(systemRoleResourceId));
        } catch (Exception e) {
            log.error("根据id[" + systemRoleResourceId + "]取得角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemRoleResourceId + "]取得角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveSystemRoleResource(SystemRoleResource systemRoleResource) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                    systemRoleResourceModel.saveSystemRoleResource(systemRoleResource));
            result.setMessage("保存成功！");
        } catch (Exception e) {
            log.error("保存角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateSystemRoleResource(SystemRoleResource systemRoleResource) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                    systemRoleResourceModel.updateSystemRoleResource(systemRoleResource));
            result.setMessage("修改成功！");
        } catch (Exception e) {
            log.error("更新角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemRoleResource>> page(Map<String, String> queryMap, PagerInfo pager) {
        ServiceResult<List<SystemRoleResource>> serviceResult = new ServiceResult<List<SystemRoleResource>>();
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemRoleResourceModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            queryMap.put("start", start + "");
            queryMap.put("size", size + "");
            List<SystemRoleResource> list = systemRoleResourceModel.page(queryMap);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemRoleResourceServiceImpl][page] param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemRoleResourceServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(Integer id) {
        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(systemRoleResourceModel.del(id));
        } catch (Exception e) {
            log.error("[SystemRoleResourceServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

    @Override
    public ServiceResult<Boolean> save(String roleId, String[] resArr) {
        ServiceResult<Boolean> serRes = new ServiceResult<Boolean>();
        try {
            serRes.setResult(systemRoleResourceModel.save(roleId, resArr));
            serRes.setMessage("保存成功。");
        } catch (Exception e) {
            serRes.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, e.getMessage());
            e.printStackTrace();
        }
        return serRes;
    }

    @Override
    public ServiceResult<List<SystemResource>> getResourceByRoleId(Integer roleId) {
        ServiceResult<List<SystemResource>> serRes = new ServiceResult<List<SystemResource>>();
        try {
            serRes.setResult(systemRoleResourceModel.getResourceByRoleId(roleId));
        } catch (Exception e) {
            serRes.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, e.getMessage());
            e.printStackTrace();
        }
        return serRes;
    }

    @Override
    public ServiceResult<List<SystemResource>> getResourceByPid(Integer pid, Integer roleId) throws BusinessException {
        ServiceResult<List<SystemResource>> serRes = new ServiceResult<List<SystemResource>>();
        try {
            serRes.setResult(systemRoleResourceModel.getResourceByPid(pid, roleId));
        } catch (Exception e) {
            serRes.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, e.getMessage());
            e.printStackTrace();
        }
        return serRes;
    }
}