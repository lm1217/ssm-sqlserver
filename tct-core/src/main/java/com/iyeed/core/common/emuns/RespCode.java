package com.iyeed.core.common.emuns;


import com.iyeed.core.common.BaseEnum;

/**
 * @author ZhangJunhua
 * @version 0.1 2017-03-16 17:57
 */
public enum RespCode implements BaseEnum<Integer> {
    SUCCESS(0, null), ERROR(-1, "系统错误"), FAILED(1, "失败"),

    /** 参数错误 */
    ILLEGAL_ARGUMENT(2, "参数错误"),

    /** 验证码错误 */
    CAPTCHA_ERROR(3, "验证码错误"),

    /** 验证码失效 */
    CAPTCHA_LAPSED(4, "验证码失效"),

    /** 验证码失效 */
    SMSVERRIFY_LAPSED(5, "短信验证码失效"),

    /** 验证码失效 */
    SMSVERRIFY_ERROR(6, "短信验证码错误"),

    /** 用户未登录 */
    NOT_LOGIN(11, "未登录"),

    /** 登录账号信息已过期 */
    TOKEN_EXPIRE(12, "登录已失效"),

    /** 该用户已被禁用！ */
    USER_DISABLED(13, "该用户已被禁用"),

    /** 手机号已经被注册过！ */
    PHONE_ISEXISTS(14, "手机号已经注册"),

    /** 今日获取验证码次数过多,请明日再试！ */
    GET_VERIFY_IS_TO_OFTEN(15,"今日获取验证码次数过多,请明日再试"),

    /** 今日获取实名认证次数过多,请明日再试！ */
    GET_SMRZ_IS_TO_OFTEN(16,"今日获取实名认证次数过多,请明日再试"),

    /** 未注册 */
    NOT_REGISTER(111, "未注册"),

    USER_NOT_EXIST(112, "用户不存在"),

    MOBILE_IS_NO_REGISTER(113, "手机号没有注册"),

    /** 重复点赞 */
    USER_LIKE_ALREADY(131, "今天已经赞TA了"),

    /** 不能给自己点赞 */
    USER_LIKE_MYSELF(132, "不要给自己点赞哟"),;

    private Integer code;
    private String desc;

    RespCode(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static RespCode getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        RespCode[] enumValues = values();
        for (RespCode enumValue : enumValues) {
            if (enumValue.getCode().equals(code)) {
                return enumValue;
            }
        }
        return null;
    }
}
