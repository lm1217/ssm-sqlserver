package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BdFormImageModel extends BaseModel {
    
    /**
     * 根据id取得表单-图片子表
     * @param  bdFormImageId
     * @return
     */
    public BdFormImage getBdFormImageById(Integer bdFormImageId) throws Exception {
    	return bdFormImageWriteDao.get(bdFormImageId);
    }

    public List<BdFormImage> getBdFormImageListByApplyNo(String applyNo, Integer type) throws Exception {
        return bdFormImageWriteDao.getFormImageList(applyNo, type);
    }
    
    /**
     * 保存表单-图片子表
     * @param  bdFormImage
     * @return
     */
    public Integer saveBdFormImage(BdFormImage bdFormImage) throws Exception {
     	this.dbConstrains(bdFormImage);
     	return bdFormImageWriteDao.insert(bdFormImage);
    }

    public Integer saveBdFormImageList(List<BdFormImage> list) throws Exception {
        return bdFormImageWriteDao.insertList(list);
    }
     
    /**
     * 更新表单-图片子表
     * @param  bdFormImage
     * @return
     */
    public Integer updateBdFormImage(BdFormImage bdFormImage) throws Exception {
        this.dbConstrains(bdFormImage);
     	return bdFormImageWriteDao.update(bdFormImage);
    }
     
    private void dbConstrains(BdFormImage bdFormImage) throws Exception {
		bdFormImage.setImageUrl(StringUtil.dbSafeString(bdFormImage.getImageUrl(), true, 200));
    }
}