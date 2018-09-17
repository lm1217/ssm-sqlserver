package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.vo.GetDisposeListBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdFormDisposeWriteDao {
 
	BdFormDispose get(Integer id) throws Exception;

	BdFormDispose getDispose(@Param("applyNo") String applyNo,
							 @Param("userNo") String userNo,
							 @Param("storeNo") String storeNo) throws Exception;

	List<GetDisposeListBean> getDisposeListByApplyNo(String applyNo) throws Exception;
	
	Integer insert(BdFormDispose bdFormDispose) throws Exception;
	
	Integer update(BdFormDispose bdFormDispose) throws Exception;
}