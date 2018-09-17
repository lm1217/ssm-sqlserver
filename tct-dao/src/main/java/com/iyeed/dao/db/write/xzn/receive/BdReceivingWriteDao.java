package com.iyeed.dao.db.write.xzn.receive;

import com.iyeed.core.entity.receive.BdReceiving;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BdReceivingWriteDao {
 
	BdReceiving get(Integer id) throws Exception;

	List<BdReceiving> getBdReceivingList(@Param("queryMap") Map<String, String> queryMap,
										 @Param("start") Integer start,
										 @Param("size") Integer size) throws Exception;
	/**
	 * 根据条件获取收货列表总数
	 * @param queryMap
	 * @return
	 */
	Integer getBdReceivingListCount(@Param("queryMap") Map<String, String> queryMap);
	
	Integer insert(BdReceiving bdReceiving) throws Exception;
	
	Integer update(BdReceiving bdReceiving) throws Exception;
}