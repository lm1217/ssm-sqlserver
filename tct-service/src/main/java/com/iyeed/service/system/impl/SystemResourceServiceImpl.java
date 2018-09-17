package com.iyeed.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.system.SystemResourceModel;
import com.iyeed.service.system.ISystemResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "systemResourceService")
public class SystemResourceServiceImpl implements ISystemResourceService {
    private static Logger log = LoggerFactory.getLogger(SystemResourceServiceImpl.class);

    @Resource
    private SystemResourceModel systemResourceModel;

    /**
    * 根据id取得资源表
    * @param  systemResourceId
    * @return
    */
    @Override
    public ServiceResult<SystemResource> getSystemResourceById(Integer systemResourceId) {
        ServiceResult<SystemResource> result = new ServiceResult<SystemResource>();
        try {
            result.setResult(systemResourceModel.getSystemResourceById(systemResourceId));
        } catch (Exception e) {
            log.error("根据id[" + systemResourceId + "]取得资源表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemResourceId + "]取得资源表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存资源表
     * @param  systemResource
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSystemResource(SystemResource systemResource) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemResourceModel.saveSystemResource(systemResource));
            result.setMessage("保存成功");
        } catch (Exception e) {
            log.error("保存资源表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存资源表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新资源表
    * @param  systemResource
    * @return
    */
    @Override
    public ServiceResult<Integer> updateSystemResource(SystemResource systemResource) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemResourceModel.updateSystemResource(systemResource));
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新资源表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新资源表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemResource>> page(Map<String, String> queryMap,
                                                     PagerInfo pager) {
        ServiceResult<List<SystemResource>> serviceResult = new ServiceResult<List<SystemResource>>();
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemResourceModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            queryMap.put("start", start + "");
            queryMap.put("size", size + "");
            List<SystemResource> list = systemResourceModel.page(queryMap);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemResourceServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemResourceServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(Integer id) {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(systemResourceModel.del(id));
            result.setMessage("删除成功");
        } catch (Exception e) {
            log.error("[SystemResourceServiceImpl][del] exception:" + e.getMessage());
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemResource>> getByPid(Integer pid) {
        ServiceResult<List<SystemResource>> serviceResult = new ServiceResult<List<SystemResource>>();
        try {
            serviceResult.setResult(systemResourceModel.getByPid(pid));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemResourceServiceImpl][getByPid] pid:" + pid);
            log.error("[SystemResourceServiceImpl][getByPid] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}