package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdFormImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdFormImageWriteDao {
 
	BdFormImage get(Integer id) throws Exception;

	List<BdFormImage> getFormImageList(String applyNo) throws Exception;
	
	Integer insert(BdFormImage bdFormImage) throws Exception;
	
	Integer update(BdFormImage bdFormImage) throws Exception;
}