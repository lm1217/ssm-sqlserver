package com.iyeed.dao.db.write.xzn.user;

import com.iyeed.core.entity.user.MdUserStore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdUserStoreWriteDao {
 
	MdUserStore get(Integer id) throws Exception;

	List<MdUserStore> getUserStoreListByUserNo(String userNo) throws Exception;

	List<MdUserStore> getUserStoreListByUserNoArr(@Param("userNoArr") String[] userNoArr) throws Exception;
	
	Integer insert(MdUserStore mdUserStore) throws Exception;
	
	Integer update(MdUserStore mdUserStore) throws Exception;
}