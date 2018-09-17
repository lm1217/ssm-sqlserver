package com.iyeed.service.common.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.common.CommonModel;
import com.iyeed.service.common.ICommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by DAVID on 2017/12/15.
 */
@Service(value = "commonServiceImpl")
public class CommonServiceImpl implements ICommonService {
    private static Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    private CommonModel commonModel;

    /**
     * 给手机号发送验证码
     * @param  phone
     * @param  code
     * @return
     */
    @Override
    public ServiceResult<Boolean> sendSmsCode(String phone, String code) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(commonModel.sendSmsCode(phone, code));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ICommonService][sendSmsCode]保存会员表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ICommonService][sendSmsCode]保存会员表时出现未知异常：", e);
        }
        return result;
    }
}
