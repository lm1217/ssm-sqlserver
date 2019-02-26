package com.iyeed.dao.db.write.xzn.form;

import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.vo.*;
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

	List<FormJobBean> getBdFormListForDestroySendFormMail() throws Exception;

	List<FormJobBean> getBdFormListForDestroyChangeAudit() throws Exception;

	List<GetDisposeFormListBean> getBdFormList(@Param("queryMap") Map<String, Object> queryMap,
											   @Param("start") Integer start,
											   @Param("size") Integer size) throws Exception;

	Integer getBdFormListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<ExceptionReportBean> getExceptionReportList(@Param("queryMap") Map<String, Object> queryMap,
													 @Param("start") Integer start,
													 @Param("size") Integer size) throws Exception;

	Integer getExceptionReportListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<GetDestroyFormListBean> getDestroyFormList(@Param("queryMap") Map<String, Object> queryMap,
													@Param("start") Integer start,
													@Param("size") Integer size) throws Exception;

	Integer getDestroyFormListCount(@Param("queryMap") Map<String, Object> queryMap);

    List<GetDestroyTheftFormListBean> getDestroyTheftFormList(@Param("queryMap") Map<String, Object> queryMap,
                                                    @Param("start") Integer start,
                                                    @Param("size") Integer size) throws Exception;

    Integer getDestroyTheftFormListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<GetDSIFormListBean> getDSIFormList(@Param("queryMap") Map<String, Object> queryMap,
													@Param("start") Integer start,
													@Param("size") Integer size) throws Exception;

	Integer getDSIFormListCount(@Param("queryMap") Map<String, Object> queryMap);

	List<StockReportBean> getStockReportList(@Param("queryMap") Map<String, Object> queryMap,
													 @Param("start") Integer start,
													 @Param("size") Integer size) throws Exception;

	Integer getStockReportListCount(@Param("queryMap") Map<String, Object> queryMap);

	Integer insert(BdForm bdForm) throws Exception;
	
	Integer update(BdForm bdForm) throws Exception;

	Integer updateForSendFormMail(@Param("idArr") Integer[] idArr) throws Exception;

	Integer updateForChangeAudit(FormJobForm formJobForm) throws Exception;

	Integer delBdForm(Integer id) throws Exception;

	Integer delBdFormByApplyNo(String applyNo) throws Exception;
}