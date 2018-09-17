package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.vo.GetDisposeListBean;
import com.iyeed.dao.db.write.xzn.form.BdFormDisposeWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BdFormDisposeModel {
    
    @Resource
    private BdFormDisposeWriteDao bdFormDisposeWriteDao;
    
    /**
     * 根据id取得bd_form_dispose对象
     * @param  bdFormDisposeId
     * @return
     */
    public BdFormDispose getBdFormDisposeById(Integer bdFormDisposeId) throws Exception {
    	return bdFormDisposeWriteDao.get(bdFormDisposeId);
    }

    public List<GetDisposeListBean> getDisposeListByApplyNo(String applyNo) throws Exception {
        return bdFormDisposeWriteDao.getDisposeListByApplyNo(applyNo);
    }
    
    /**
     * 保存bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    public Integer saveBdFormDispose(BdFormDispose bdFormDispose) throws Exception {
     	this.dbConstrains(bdFormDispose);
     	return bdFormDisposeWriteDao.insert(bdFormDispose);
    }
     
    /**
     * 更新bd_form_dispose对象
     * @param  bdFormDispose
     * @return
     */
    public Integer updateBdFormDispose(BdFormDispose bdFormDispose) throws Exception {
        this.dbConstrains(bdFormDispose);
     	return bdFormDisposeWriteDao.update(bdFormDispose);
    }
     
    private void dbConstrains(BdFormDispose bdFormDispose) throws Exception {
		bdFormDispose.setApplyNo(StringUtil.dbSafeString(bdFormDispose.getApplyNo(), true, 40));
		bdFormDispose.setStoreNo(StringUtil.dbSafeString(bdFormDispose.getStoreNo(), true, 40));
		bdFormDispose.setStoreName(StringUtil.dbSafeString(bdFormDispose.getStoreName(), true, 40));
		bdFormDispose.setDisposeUserNo(StringUtil.dbSafeString(bdFormDispose.getDisposeUserNo(), true, 40));
		bdFormDispose.setDisposeUserName(StringUtil.dbSafeString(bdFormDispose.getDisposeUserName(), true, 40));
		bdFormDispose.setDisposeStatus(StringUtil.dbSafeString(bdFormDispose.getDisposeStatus(), true, 40));
		bdFormDispose.setRemark(StringUtil.dbSafeString(bdFormDispose.getRemark(), true, 400));
    }
}