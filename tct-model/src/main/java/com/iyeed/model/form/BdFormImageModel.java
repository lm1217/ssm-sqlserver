package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.dao.db.write.xzn.form.BdFormImageWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BdFormImageModel {
    
    @Resource
    private BdFormImageWriteDao bdFormImageWriteDao;
    
    /**
     * 根据id取得表单-图片子表
     * @param  bdFormImageId
     * @return
     */
    public BdFormImage getBdFormImageById(Integer bdFormImageId) throws Exception {
    	return bdFormImageWriteDao.get(bdFormImageId);
    }

    public List<BdFormImage> getBdFormImageListByApplyNo(String applyNo) throws Exception {
        return bdFormImageWriteDao.getFormImageList(applyNo);
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