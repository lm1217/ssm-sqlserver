package com.iyeed.core;

import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConstantsEJS {

    /**
     * 默认显示页数20
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final String SERVICE_RESULT_CODE_SYSERROR = "syserror";
    public static final String SERVICE_RESULT_EXCEPTION_SYSERROR = "服务异常，请联系系统管理员。";

    /**
     *
     */
    public static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
    /**
     * token过期时间，默认1个月
     */
    public static final int TOKEN_EXPIRY_TIME = 1;

    /** 系统编码 */
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    //**********************  AliyunOss 配置 ******************************//
    /**
     * endpoint
     */
    public static final String ALIYUN_OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";

    /**
     * key
     */
    public static final String ALIYUN_OSS_ACCESS_KEY_ID = "LTAI4ufyCeVsMWCM";

    /**
     * secret
     */
    public static final String ALIYUN_OSS_ACCESS_KEY_SECRET = "1r4PFOhASTXmncrghtkmqAxG0Fa9et";

    /**
     * bucket
     */
    public static final String ALIYUN_OSS_APK_BUCKET = "dev-love-lm-dev";

    /**
     * oss图片路径
     */
    public static final String ALIYUN_OSS_BUCKET_ENDPOINT = "https://dev-love-lm-dev.oss-cn-shenzhen.aliyuncs.com";

    //**********************  极光推送 配置 ******************************//

    /**
     * secret
     */
    public static final String JPUSH_MASTER_SECRET = "0034718806a61cf62d26faf4";

    /**
     * app_key
     */
    public static final String JPUSH_APP_KEY = "ff56236ef99da4d90e58753e";

    //**********************  阿里大于短信(合并到阿里云了) 配置 ******************************//
    public static final String ALIDAYU_APPKEY = "LTAIuopEm6C3Bl0Z";
    public static final String ALIDAYU_SECRET = "zWa4lCsfYdD39fS5eoCcNMsTkBkxvj";
    //短信验证码模版（用户用户注册）
    public static final String ALIDAYU_SMS_TEMPLATE1 = "SMS_74550009";
    //短信通知模版（用于密码找回）
    public static final String ALIDAYU_SMS_TEMPLATE2 = "SMS_74630002";
    //短信通知模版（用于租赁到期提醒）
    public static final String ALIDAYU_SMS_TEMPLATE3 = "SMS_75850126";
    //短信通知模版（用于注册通知）
    public static final String ALIDAYU_SMS_TEMPLATE4 = "SMS_79510009";
    //签名（必须跟申请的签名一致）
    public static final String ALIDAYU_SMS_SIGNNAME = "易时租";
    public static final String ALIDAYU_SMS_EXPIRE_MINUTE = "5";

    //  *********************** netty socket ************************** //
    /**
     * key:用户id  value:ChannelHandlerContext  用于聊天
     */
    public static final Map<Long, ChannelHandlerContext> CTXMAP = new ConcurrentHashMap<>(256);

    public static final int WEBSOCKET_PORT = 8091;

    /**
     * 用于咨询列表  未读数
     */
    public static final Map<Long, ChannelHandlerContext> CTXMAP_CONSULT = new ConcurrentHashMap<>(256);

    /**
     * 图片验证码 session key
     */
    public static final String VERIFY_NUMBER_NAME = "verify_number_name";

    /**
     * 用户账号状态(1-正常 2-冻结 3-删除)
     */
    public static final int SYSTEM_USER_STATUS_NORM = 1;

    public static final int SYSTEM_RESOURCE_ROOT = 0;

}
