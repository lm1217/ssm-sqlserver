package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdFormSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BdFormSkuWriteDao {
 
	BdFormSku get(Integer id) throws Exception;

	List<BdFormSku> getFormSkuList(String applyNo) throws Exception;

	List<BdFormSku> getFormSkuListForBack(@Param("applyNo") String applyNo,
										  @Param("storeNo") String storeNo) throws Exception;

	Integer insert(BdFormSku bdFormSku) throws Exception;

	Integer insertList(List<BdFormSku> list) throws Exception;
	
	Integer update(BdFormSku bdFormSku) throws Exception;

	Integer del(String applyNo) throws Exception;
}