package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.GetDisposeFormListBean;
import com.iyeed.core.entity.receive.BdReceiving;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BdFormWriteDao {
 
	BdForm get(Integer id) throws Exception;

	List<GetDisposeFormListBean> getBdFormList(@Param("queryMap") Map<String, String> queryMap,
											   @Param("start") Integer start,
											   @Param("size") Integer size) throws Exception;

	Integer getBdFormListCount(@Param("queryMap") Map<String, String> queryMap);

	Integer insert(BdForm bdForm) throws Exception;
	
	Integer update(BdForm bdForm) throws Exception;
}