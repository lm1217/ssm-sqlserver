package com.iyeed.service.system.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemLog;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.system.SystemLogModel;
import com.iyeed.service.system.ISystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "systemLogService")
public class SystemLogServiceImpl implements ISystemLogService {
	private static final Logger log = LoggerFactory.getLogger(SystemLogServiceImpl.class);

	@Resource
    private SystemLogModel systemLogModel;

    /**
     * 根据id取得system_log对象
     * @param  systemLogId
     * @return
     */
    @Override
    public ServiceResult<SystemLog> getSystemLogById(Integer systemLogId) {
        ServiceResult<SystemLog> result = new ServiceResult<SystemLog>();
        try {
            result.setResult(systemLogModel.getSystemLogById(systemLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + systemLogId + "]取得system_log对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + systemLogId + "]取得system_log对象时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存system_log对象
     * @param  systemLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSystemLog(SystemLog systemLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemLogModel.saveSystemLog(systemLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存system_log对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存system_log对象时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新system_log对象
     * @param  systemLog
     * @return
     */
    @Override
    public ServiceResult<Integer> updateSystemLog(SystemLog systemLog) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemLogModel.updateSystemLog(systemLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新system_log对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新system_log对象时出现未知异常：", e);
        }
        return result;
    }
}