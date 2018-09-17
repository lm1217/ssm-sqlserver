package com.iyeed.api.controller.common.emuns;


import com.iyeed.api.controller.common.BaseEnum;

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

    /** 用户没有权限 */
    NOT_AUTH(10, "没有此权限"),
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
    USER_LIKE_MYSELF(132, "不要给自己点赞哟"),

    /** 设备不在线 */
    DEVICE_OFFLINE(200, "设备不在线"),
    /** 请不要频繁控制设备 */
    DEVICE_CTRL_OFTEN(220, "请不要频繁控制设备"),
    /** 未绑定该设备 */
    DEVICE_CTRL_NOT_BIND(221, "未绑定该设备"),
    /** 您不是主管理员 */
    DEVICE_IS_NOT_ADMIN(222, "您不是主管理员"),
    /** 设备名重复 */
    DEVICE_ALREADY_NAME(223, "设备名重复"),
    /** 设备未响应 */
    DEVICE_CTRL_TIMEOUT(201, "设备未响应"),
    /** 开盖保护 */
    DEVICE_CTRL_PROTECT(202, "设备保护盖未关闭"),
    /** 控制失败 */
    DEVICE_CTRL_FAILURE(203, "控制失败"),
    /** 设备未开启 */
    DEVICE_NOT_RUNNING(205, "设备未开启"),
    /** 设备不存在 */
    DEVICE_NOT_EXIST(207, "设备不存在"),
    /**
     * 当前设备尚有其他用户绑定，请先转让'主管理员'权限后方可直接删除设备
     */
    STATUS_209(209, "当前设备尚有其他用户绑定，请先转让'主管理员'权限后方可直接删除设备"),

    /** 该设备已绑定 */
    DEVICE_ALREADY_BIND(210, "该设备已绑定"),

    /** 未获取到设备位置 */
    DEVICE_POSITION_NOT_EXIST(231, "未获取到设备位置"),

    /** 已经是最新版本 */
    VERSION_IS_NEW(300, "已经是最新版本"),

    /** 版本号不存在 */
    VERSION_NOT_EXIST(301, "版本号不存在"),

    /** 当前没有一个版本 */
    VERSION_NOT_EXIST_NOW(302, "当前没有一个版本"),

    /** 敏感词 */
    KEYWORD_IS_FILTER(400, "敏感词"),

    /** 您无权访问他人信息*/
    HAVE_NO_AUTHORITY(450, "您无权访问他人信息"),

    /** 订单不存在 您无权访问他人信息*/
    ORDER_ISNOT_EXIST(500, "订单不存在"),

    /** 网单不存在 您无权访问他人信息*/
    ORDERPRODUCT_ISNOT_EXIST(501, "网单不存在"),

    /** 获得商品信息失败！！*/
    GET_PRODUCT_FAIL(520, "获取商品信息失败"),

    /** 获得商品信息为空！！*/
    GET_PRODUCT_IS_NULL(521, "获取商品信息为空"),

    /** 货品信息为空！！*/
    GET_PRODUCTGOODS_IS_NULL(522, "货品信息为空"),

    /** 支付失败，请不要重复提交，谢谢！！*/
    ORDER_SESSION_ISDIFF(550, "支付失败，请不要重复提交，谢谢！"),

    /** 已经申请过提现了！！*/
    AGENT_APPLYING(570, "已经申请过提现了"),

    /** 您有超时费用未结算，请支付相关费用后再申请退还押金！ */
    HAVE_OVER_FEE_ON_PAY(580, "您有超时费用未结算，请支付相关费用后再申请退还押金"),

    /** 审核不通过！！*/
    AGENT_IS_FAILED(600, "审核不通过"),

    /** 审核失败！！*/
    AGENT_IS_LOOKING(601, "审核中"),

    /** 审核通过！！*/
    AGENT_IS_PASS(602, "审核通过"),

    /** 你不是合伙人！！*/
    AGENT_NOT_EXIST(620, "你不是合伙人"),;

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
