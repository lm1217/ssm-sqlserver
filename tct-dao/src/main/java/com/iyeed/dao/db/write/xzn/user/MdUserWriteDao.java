package com.iyeed.dao.db.write.xzn.user;

import com.iyeed.core.entity.user.MdUser;
import org.springframework.stereotype.Repository;

@Repository
public interface MdUserWriteDao {
 
	MdUser get(Integer id) throws Exception;

	MdUser getUserByUserId(String userId) throws Exception;

	MdUser getUserByUserNo(String userNo) throws Exception;
	
	Integer insert(MdUser mdUser) throws Exception;
	
	Integer update(MdUser mdUser) throws Exception;
}