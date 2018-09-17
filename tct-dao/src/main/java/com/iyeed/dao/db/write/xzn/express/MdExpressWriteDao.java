package com.iyeed.dao.db.write.xzn.express;

import com.iyeed.core.entity.express.MdExpress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MdExpressWriteDao {
 
	MdExpress get(Integer id) throws Exception;

	List<MdExpress> getExpressList(@Param("queryMap") Map<String, Object> qureyMap) throws Exception;
	
	Integer insert(MdExpress mdExpress) throws Exception;
	
	Integer update(MdExpress mdExpress) throws Exception;
}