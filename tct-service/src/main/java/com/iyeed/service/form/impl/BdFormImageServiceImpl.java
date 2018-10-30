package com.iyeed.service.form.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.form.IBdFormImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "bdFormImageService")
public class BdFormImageServiceImpl extends BaseService implements IBdFormImageService {
	private static final Logger log = LoggerFactory.getLogger(BdFormImageServiceImpl.class);

    /**
     * 根据id取得表单-图片子表
     * @param  bdFormImageId
     * @return
     */
    @Override
    public ServiceResult<BdFormImage> getBdFormImageById(Integer bdFormImageId) {
        ServiceResult<BdFormImage> result = new ServiceResult<BdFormImage>();
        try {
            result.setResult(bdFormImageModel.getBdFormImageById(bdFormImageId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormImageService][getBdFormImageById]根据id["+bdFormImageId+"]取得表单-图片子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormImageService][getBdFormImageById]根据id["+bdFormImageId+"]取得表单-图片子表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<BdFormImage>> getBdFormImageListByApplyNo(String applyNo, Integer type) {
        ServiceResult<List<BdFormImage>> result = new ServiceResult<>();
        try {
            result.setResult(bdFormImageModel.getBdFormImageListByApplyNo(applyNo, type));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormImageService][getBdFormImageById]根据id["+applyNo+"]取得表单-图片子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormImageService][getBdFormImageById]根据id["+applyNo+"]取得表单-图片子表时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 保存表单-图片子表
     * @param  bdFormImage
     * @return
     */
    @Override
    public ServiceResult<Integer> saveBdFormImage(BdFormImage bdFormImage) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormImageModel.saveBdFormImage(bdFormImage));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormImageService][saveBdFormImage]保存表单-图片子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormImageService][saveBdFormImage]保存表单-图片子表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新表单-图片子表
     * @param  bdFormImage
     * @return
     */
    @Override
    public ServiceResult<Integer> updateBdFormImage(BdFormImage bdFormImage) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bdFormImageModel.updateBdFormImage(bdFormImage));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBdFormImageService][updateBdFormImage]更新表单-图片子表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBdFormImageService][updateBdFormImage]更新表单-图片子表时出现未知异常：",
                e);
        }
        return result;
    }
}