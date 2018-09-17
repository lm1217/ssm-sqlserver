package com.iyeed.service.form;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormImage;

import java.util.List;

public interface IBdFormImageService {

    /**
     * 根据id取得表单-图片子表
     * @param  bdFormImageId
     * @return
     */
    ServiceResult<BdFormImage> getBdFormImageById(Integer bdFormImageId);

    ServiceResult<List<BdFormImage>> getBdFormImageListByApplyNo(String applyNo);
    
    /**
     * 保存表单-图片子表
     * @param  bdFormImage
     * @return
     */
    ServiceResult<Integer> saveBdFormImage(BdFormImage bdFormImage);
     
    /**
     * 更新表单-图片子表
     * @param  bdFormImage
     * @return
     */
    ServiceResult<Integer> updateBdFormImage(BdFormImage bdFormImage);
}