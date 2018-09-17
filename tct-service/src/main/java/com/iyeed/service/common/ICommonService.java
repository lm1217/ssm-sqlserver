package com.iyeed.service.common;

import com.iyeed.core.ServiceResult;

/**
 * Created by DAVID on 2017/12/15.
 */
public interface ICommonService {
    /**
     * 给手机号发送验证码
     * @param  phone
     * @param  code
     * @return
     */
    ServiceResult<Boolean> sendSmsCode(String phone, String code);
}
