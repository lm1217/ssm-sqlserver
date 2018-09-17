package com.iyeed.dao.db.write.xzn.receive;

import com.iyeed.core.entity.receive.BdReceivingSku;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdReceivingSkuWriteDao {
 
	BdReceivingSku get(Integer id) throws Exception;

	List<BdReceivingSku> getBdReceivingSkuListByErpNo(String erpNo) throws Exception;
	
	Integer insert(BdReceivingSku bdReceivingSku) throws Exception;
	
	Integer update(BdReceivingSku bdReceivingSku) throws Exception;
}