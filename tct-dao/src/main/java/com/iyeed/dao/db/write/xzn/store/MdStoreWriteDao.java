package com.iyeed.dao.db.write.xzn.store;

import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdStoreWriteDao {
 
	MdStore get(Integer id) throws Exception;

	MdStore getStoreByStoreNo(String storeNo) throws Exception;

	List<GetStoreListBean> getStoreList() throws Exception;

	List<GetStoreListBean> getStoreListByBrandNo(@Param("brandNo") String brandNo) throws Exception;

	List<GetStoreListBean> getBrandStoreListByBrandNo(@Param("brandNo") String brandNo) throws Exception;

	Integer insert(MdStore mdStore) throws Exception;
	
	Integer update(MdStore mdStore) throws Exception;

	Integer updateStore(@Param("brandCode") String brandCode,
						@Param("storeId") String storeId,
						@Param("region") String region,
						@Param("city") String city,
						@Param("channel") String channel,
						@Param("doorType") String doorType,
						@Param("asm") String asm,
						@Param("ac") String ac) throws Exception;
}