package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.ExceptionReportBean;
import com.iyeed.core.entity.form.vo.FormJobBean;
import com.iyeed.core.entity.form.vo.FormJobForm;
import com.iyeed.core.entity.form.vo.GetDisposeFormListBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BdFormWriteDao {
 
	BdForm get(Integer id) throws Exception;

	BdForm getByApplyNo(String applyNo) throws Exception;

	List<FormJobBean> getBdFormListForSendFormMail() throws Exception;

	List<FormJobBean> getBdFormListForChangeAudit() throws Exception;

	List<GetDisposeFormListBean> getBdFormList(@Param("queryMap") Map<String, Object> queryMap,
											   @Param("start") Integer start,
											   @Param("size") Integer size) throws Exception;

	Integer getBdFormListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<ExceptionReportBean> getExceptionReportList(@Param("queryMap") Map<String, Object> queryMap,
													 @Param("start") Integer start,
													 @Param("size") Integer size) throws Exception;

	Integer getExceptionReportListCount(@Param("queryMap") Map<String, Object> queryMap);

	Integer insert(BdForm bdForm) throws Exception;
	
	Integer update(BdForm bdForm) throws Exception;

	Integer updateForSendFormMail(@Param("idArr") Integer[] idArr) throws Exception;

	Integer updateForChangeAudit(FormJobForm formJobForm) throws Exception;

	Integer delBdForm(Integer id) throws Exception;

	Integer delBdFormByApplyNo(String applyNo) throws Exception;
}