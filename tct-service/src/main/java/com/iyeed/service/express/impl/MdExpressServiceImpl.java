package com.iyeed.service.express.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.express.MdExpress;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.express.IMdExpressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "mdExpressService")
public class MdExpressServiceImpl extends BaseService implements IMdExpressService {
	private static final Logger log = LoggerFactory.getLogger(MdExpressServiceImpl.class);

    @Override
    public ServiceResult<List<MdExpress>> getExpressList(Map<String, Object> qureyMap) {
        ServiceResult<List<MdExpress>> result = new ServiceResult<>();
        try {
            result.setResult(mdExpressModel.getExpressList(qureyMap));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + qureyMap + "]取得快递公司信息表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + qureyMap + "]取得快递公司信息表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 根据id取得快递公司信息表
     * @param  mdExpressId
     * @return
     */
    @Override
    public ServiceResult<MdExpress> getMdExpressById(Integer mdExpressId) {
        ServiceResult<MdExpress> result = new ServiceResult<MdExpress>();
        try {
            result.setResult(mdExpressModel.getMdExpressById(mdExpressId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdExpressService][getMdExpressById]根据id["+mdExpressId+"]取得快递公司信息表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdExpressService][getMdExpressById]根据id["+mdExpressId+"]取得快递公司信息表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存快递公司信息表
     * @param  mdExpress
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdExpress(MdExpress mdExpress) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdExpressModel.saveMdExpress(mdExpress));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdExpressService][saveMdExpress]保存快递公司信息表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdExpressService][saveMdExpress]保存快递公司信息表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新快递公司信息表
     * @param  mdExpress
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdExpress(MdExpress mdExpress) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdExpressModel.updateMdExpress(mdExpress));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdExpressService][updateMdExpress]更新快递公司信息表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdExpressService][updateMdExpress]更新快递公司信息表时出现未知异常：",
                e);
        }
        return result;
    }
}