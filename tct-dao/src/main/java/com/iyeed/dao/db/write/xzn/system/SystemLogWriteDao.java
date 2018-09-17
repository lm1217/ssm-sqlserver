package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogWriteDao {
 
	SystemLog get(Integer id) throws Exception;
	
	Integer insert(SystemLog systemLog) throws Exception;
	
	Integer update(SystemLog systemLog) throws Exception;
}