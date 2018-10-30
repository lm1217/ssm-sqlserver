package com.iyeed.model.system;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.system.SystemLog;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

@Component
public class SystemLogModel extends BaseModel {
    
    /**
     * 根据id取得system_log对象
     * @param  systemLogId
     * @return
     */
    public SystemLog getSystemLogById(Integer systemLogId) throws Exception {
    	return systemLogWriteDao.get(systemLogId);
    }
    
    /**
     * 保存system_log对象
     * @param  systemLog
     * @return
     */
    public Integer saveSystemLog(SystemLog systemLog) throws Exception {
     	this.dbConstrains(systemLog);
     	return systemLogWriteDao.insert(systemLog);
    }
     
    /**
     * 更新system_log对象
     * @param  systemLog
     * @return
     */
    public Integer updateSystemLog(SystemLog systemLog) throws Exception {
        this.dbConstrains(systemLog);
     	return systemLogWriteDao.update(systemLog);
    }
     
    private void dbConstrains(SystemLog systemLog) throws Exception {
		systemLog.setModule(StringUtil.dbSafeString(systemLog.getModule(), true, 100));
		systemLog.setIp(StringUtil.dbSafeString(systemLog.getIp(), true, 20));
		systemLog.setCommit(StringUtil.dbSafeString(systemLog.getCommit(), true, 20));
		systemLog.setTargetName(StringUtil.dbSafeString(systemLog.getTargetName(), true, 100));
		systemLog.setMethodName(StringUtil.dbSafeString(systemLog.getMethodName(), true, 50));
		systemLog.setBusinessDesc(StringUtil.dbSafeString(systemLog.getBusinessDesc(), true, 100));
		systemLog.setRequestParams(StringUtil.dbSafeString(systemLog.getRequestParams(), true, 65535));
        systemLog.setResponseParams(StringUtil.dbSafeString(systemLog.getResponseParams(), true, 65535));
		systemLog.setOpUserNo(StringUtil.dbSafeString(systemLog.getOpUserNo(), true, 40));
    }
}