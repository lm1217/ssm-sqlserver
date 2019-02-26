package com.iyeed.dao.db.write.xzn.stock;

import com.iyeed.core.entity.stock.BdStockInvData;
import org.springframework.stereotype.Repository;

@Repository
public interface BdStockInvDataWriteDao {
 
	BdStockInvData get(Integer id) throws Exception;
	
	Integer insert(BdStockInvData bdStockInvData) throws Exception;
	
	Integer update(BdStockInvData bdStockInvData) throws Exception;

	Integer insertForBackup() throws Exception;
}