package com.iyeed.service.system;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemLog;

public interface ISystemLogService {

    /**
     * 根据id取得system_log对象
     * @param  systemLogId
     * @return
     */
    ServiceResult<SystemLog> getSystemLogById(Integer systemLogId);
    
    /**
     * 保存system_log对象
     * @param  systemLog
     * @return
     */
    ServiceResult<Integer> saveSystemLog(SystemLog systemLog);
     
    /**
     * 更新system_log对象
     * @param  systemLog
     * @return
     */
    ServiceResult<Integer> updateSystemLog(SystemLog systemLog);
}