package com.iyeed.service.store.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.store.MdBrand;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.store.IMdBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "mdBrandService")
public class MdBrandServiceImpl extends BaseService implements IMdBrandService {
	private static final Logger log = LoggerFactory.getLogger(MdBrandServiceImpl.class);

    /**
     * 根据id取得md_brand对象
     * @param  mdBrandId
     * @return
     */
    @Override
    public ServiceResult<MdBrand> getMdBrandById(Integer mdBrandId) {
        ServiceResult<MdBrand> result = new ServiceResult<MdBrand>();
        try {
            result.setResult(mdBrandModel.getMdBrandById(mdBrandId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + mdBrandId + "]取得md_brand对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + mdBrandId + "]取得md_brand对象时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<MdBrand> getBrandByBrandNo(String brandNo) {
        ServiceResult<MdBrand> result = new ServiceResult<MdBrand>();
        try {
            result.setResult(mdBrandModel.getBrandByBrandNo(brandNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + brandNo + "]取得md_brand对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + brandNo + "]取得md_brand对象时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存md_brand对象
     * @param  mdBrand
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdBrand(MdBrand mdBrand) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdBrandModel.saveMdBrand(mdBrand));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存md_brand对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存md_brand对象时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新md_brand对象
     * @param  mdBrand
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdBrand(MdBrand mdBrand) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdBrandModel.updateMdBrand(mdBrand));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新md_brand对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新md_brand对象时出现未知异常：", e);
        }
        return result;
    }
}