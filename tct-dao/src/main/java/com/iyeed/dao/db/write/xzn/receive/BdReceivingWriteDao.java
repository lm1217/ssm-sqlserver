package com.iyeed.dao.db.write.xzn.receive;

import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.vo.ReceiveTesterBean;
import com.iyeed.core.entity.receive.vo.WaitReceiveTesterBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BdReceivingWriteDao {
 
	BdReceiving get(Integer id) throws Exception;

	List<BdReceiving> getBdReceivingList(@Param("queryMap") Map<String, Object> queryMap,
										 @Param("start") Integer start,
										 @Param("size") Integer size) throws Exception;
	/**
	 * 根据条件获取收货列表总数
	 * @param queryMap
	 * @return
	 */
	Integer getBdReceivingListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<ReceiveTesterBean> getReceiveTesterList(@Param("queryMap") Map<String, Object> queryMap,
												 @Param("start") Integer start,
												 @Param("size") Integer size) throws Exception;

	Integer getReceiveTesterListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<WaitReceiveTesterBean> getWaitReceiveTesterReport(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

	Integer insert(BdReceiving bdReceiving) throws Exception;
	
	Integer update(BdReceiving bdReceiving) throws Exception;

	//根据pkgNo设置收货单为已取消
	Integer updateByPkgNo(@Param("pkgNo") String pkgNo,
						  @Param("userNo") String userNo) throws Exception;

	//根据pkgNo设置收货单类型
	Integer updateTypeByPkgNo(@Param("pkgNo") String pkgNo,
							  @Param("receiveType") String receiveType) throws Exception;
}