package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdFormSku;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdFormSkuWriteDao {
 
	BdFormSku get(Integer id) throws Exception;

	List<BdFormSku> getFormSkuList(String applyNo) throws Exception;
	
	Integer insert(BdFormSku bdFormSku) throws Exception;
	
	Integer update(BdFormSku bdFormSku) throws Exception;
}