package com.iyeed.dao.db.write.xzn.stock;

import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvSkuListBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface BdStockInvWriteDao {
 
	BdStockInv get(Integer id) throws Exception;

	List<GetStockInvSkuListBean> getStockInvSkuListByStoreNo(String storeNo) throws Exception;

	List<GetStockInvSkuListBean> getStockInvSkuList(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

    List<GetStockInvSkuListBean> getStockInvExceptionSkuList(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

	List<GetStockInvReportListBean> getStockInvReportList(@Param("queryMap") Map<String, Object> queryMap,
												  @Param("start") Integer start,
												  @Param("size") Integer size) throws Exception;

	Integer getStockInvReportListCount(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

	List<GetStockInvReportListBean> exportStockInvReportExcel(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

	Integer insert(BdStockInv bdStockInv) throws Exception;

	Integer insertOrUpdate(BdStockInv bdStockInv) throws Exception;
	
	Integer update(BdStockInv bdStockInv) throws Exception;

	Integer updateDepotToCounter(@Param("id") Integer id,
								 @Param("moveNum") Integer moveNum,
								 @Param("updateDate") Date updateDate) throws Exception;

	Integer updateCounterToDepot(@Param("id") Integer id,
								 @Param("moveNum") Integer moveNum,
								 @Param("updateDate") Date updateDate) throws Exception;
}