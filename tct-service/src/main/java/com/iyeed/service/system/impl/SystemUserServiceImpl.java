package com.iyeed.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.system.ISystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "systemUserService")
public class SystemUserServiceImpl extends BaseService implements ISystemUserService {
    private static Logger log = LoggerFactory.getLogger(SystemUserServiceImpl.class);

    @Override
    public ServiceResult<SystemUser> getSystemUserById(Integer systemUserId) {
        ServiceResult<SystemUser> result = new ServiceResult<>();
        try {
            result.setResult(systemUserModel.getSystemUserById(systemUserId));
        } catch (Exception e) {
            log.error("根据id[" + systemUserId + "]取得系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemUserId + "]取得系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveSystemUser(SystemUser systemUser) {
        ServiceResult<Integer> result = new ServiceResult<>();
        try {
            result.setResult(systemUserModel.saveSystemUser(systemUser));
            result.setMessage("保存成功");
        } catch (Exception e) {
            log.error("保存系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateSystemUser(SystemUser systemUser) {
        ServiceResult<Integer> result = new ServiceResult<>();
        try {
            result.setResult(systemUserModel.updateSystemUser(systemUser));
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemUser>> page(Map<String, String> queryMap, PagerInfo pager) {
        ServiceResult<List<SystemUser>> serviceResult = new ServiceResult<>();
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemUserModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<SystemUser> list = systemUserModel.page(queryMap, start, size);

            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemUserServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemUserServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(Integer id) {
        ServiceResult<Boolean> sr = new ServiceResult<>();
        try {
            sr.setResult(systemUserModel.del(id));
        } catch (Exception e) {
            log.error("[SystemUserServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

    @Override
    public ServiceResult<SystemUser> getSystemUserByNamePwd(String name, String password) {
        ServiceResult<SystemUser> result = new ServiceResult<>();
        try {
            result.setResult(systemUserModel.getSystemUserByNamePwd(name, password));
        } catch (Exception e) {
            log.error("[SystemUserServiceImpl][getSystemUserByNamePwd] exception:", e);
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemUser>> getSystemUserByName(String username) {
        ServiceResult<List<SystemUser>> result = new ServiceResult<>();
        try {
            result.setResult(systemUserModel.getSystemUserByName(username));
        } catch (Exception e) {
            log.error("[SystemUserServiceImpl][getSystemUserByName] exception:", e);
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}