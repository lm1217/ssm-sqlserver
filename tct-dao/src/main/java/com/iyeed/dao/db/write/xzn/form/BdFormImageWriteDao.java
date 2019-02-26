package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdFormImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdFormImageWriteDao {
 
	BdFormImage get(Integer id) throws Exception;

	List<BdFormImage> getFormImageList(@Param("applyNo") String applyNo,
									   @Param("type") Integer type) throws Exception;
	
	Integer insert(BdFormImage bdFormImage) throws Exception;

	Integer insertList(List<BdFormImage> list) throws Exception;
	
	Integer update(BdFormImage bdFormImage) throws Exception;

	Integer del(String applyNo) throws Exception;
}