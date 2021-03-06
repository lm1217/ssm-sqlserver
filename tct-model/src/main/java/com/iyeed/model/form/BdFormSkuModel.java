package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BdFormSkuModel extends BaseModel {
    
    /**
     * 根据id取得表单-SKU子表 
     * @param  bdFormSkuId
     * @return
     */
    public BdFormSku getBdFormSkuById(Integer bdFormSkuId) throws Exception {
    	return bdFormSkuWriteDao.get(bdFormSkuId);
    }

    public List<BdFormSku> getBdFormSkuListByApplyNo(String applyNo) throws Exception {
        return bdFormSkuWriteDao.getFormSkuList(applyNo);
    }

    public List<BdFormSku> getBdFormSkuListForBack(String applyNo, String storeNo) throws Exception {
        return bdFormSkuWriteDao.getFormSkuListForBack(applyNo, storeNo);
    }
    
    /**
     * 保存表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    public Integer saveBdFormSku(BdFormSku bdFormSku) throws Exception {
     	this.dbConstrains(bdFormSku);
     	return bdFormSkuWriteDao.insert(bdFormSku);
    }

    public Integer saveBdFormSkuList(List<BdFormSku> list) throws Exception {
        return bdFormSkuWriteDao.insertList(list);
    }
     
    /**
     * 更新表单-SKU子表 
     * @param  bdFormSku
     * @return
     */
    public Integer updateBdFormSku(BdFormSku bdFormSku) throws Exception {
        this.dbConstrains(bdFormSku);
     	return bdFormSkuWriteDao.update(bdFormSku);
    }
     
    private void dbConstrains(BdFormSku bdFormSku) throws Exception {
		bdFormSku.setApplyNo(StringUtil.dbSafeString(bdFormSku.getApplyNo(), true, 40));
		bdFormSku.setSkuCode(StringUtil.dbSafeString(bdFormSku.getSkuCode(), true, 40));
		bdFormSku.setSkuName(StringUtil.dbSafeString(bdFormSku.getSkuName(), true, 40));
    }
}