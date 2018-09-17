package com.iyeed.model.common;

import com.iyeed.core.third.sms.AliyunSMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by DAVID on 2017/12/15.
 */
@Component
public class CommonModel {
    private static Logger log = LoggerFactory.getLogger(CommonModel.class);

    /**
     * 给手机号发送验证码
     * @param  phone
     * @param  code
     * @return
     */
    public Boolean sendSmsCode(String phone, String code) throws Exception {
        return AliyunSMSUtil.sendSmsCode(phone, code);
    }
}
