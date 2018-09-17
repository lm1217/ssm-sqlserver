package com.iyeed.dao.db.write.xzn.sku;

import com.iyeed.core.entity.sku.MdSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MdSkuWriteDao {
 
	MdSku get(Integer id) throws Exception;

	List<MdSku> getSkuList(@Param("queryMap") Map<String, Object> queryMap) throws Exception;
	
	Integer insert(MdSku mdSku) throws Exception;
	
	Integer update(MdSku mdSku) throws Exception;
}