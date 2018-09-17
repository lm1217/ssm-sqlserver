package com.iyeed.dao.db.write.xzn.stock;

import com.iyeed.core.entity.stock.BdStockInvLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BdStockInvLogWriteDao {
 
	BdStockInvLog get(Integer id) throws Exception;

	Integer getBdStockInvLogListCount(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

	List<BdStockInvLog> getBdStockInvLogList(@Param("queryMap") Map<String, Object> queryMap,
											 @Param("start") Integer start,
											 @Param("size") Integer size) throws Exception;
	
	Integer insert(BdStockInvLog bdStockInvLog) throws Exception;

	Integer insertForStockInvDepotToCounter(@Param("id") Integer id,
									@Param("type") Integer type,
									@Param("moveNum") Integer moveNum) throws Exception;

	Integer insertForStockInvCounterToDepot(@Param("id") Integer id,
											@Param("type") Integer type,
											@Param("moveNum") Integer moveNum) throws Exception;
	
	Integer update(BdStockInvLog bdStockInvLog) throws Exception;
}