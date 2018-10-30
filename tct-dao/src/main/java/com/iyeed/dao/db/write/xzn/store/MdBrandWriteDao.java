package com.iyeed.dao.db.write.xzn.store;

import com.iyeed.core.entity.store.MdBrand;
import org.springframework.stereotype.Repository;

@Repository
public interface MdBrandWriteDao {
 
	MdBrand get(Integer id) throws Exception;

	MdBrand getBrandByBrandNo(String brandNo) throws Exception;
	
	Integer insert(MdBrand mdBrand) throws Exception;
	
	Integer update(MdBrand mdBrand) throws Exception;
}