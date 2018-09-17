package com.iyeed.core.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.iyeed.core.ConstantsEJS;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 极光推送工具类
 * 
 * @author zhb
 * @version v 0.1 2017-03-25 11:23
 */
public class JpushUtil {

    private static final Logger logger        = LoggerFactory.getLogger(JpushUtil.class);

    private static final String MASTER_SECRET = ConstantsEJS.JPUSH_MASTER_SECRET;
    private static final String APP_KEY       = ConstantsEJS.JPUSH_APP_KEY;

    private static JPushClient  jPushClient;

    static {
        jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
    }

    /**
     * 推送通知给所有用户
     */
    public static void pushToAll(String notification) {
        System.out.println(MASTER_SECRET+"----");
        System.out.println(APP_KEY+"----");
        if(StringUtils.isBlank(notification)){
            return;
        }

        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
                .setNotification(Notification.alert(notification))
                .setAudience(Audience.all()).build();
        push(payload);
    }

    /**
     * 推送通知给指定用户
     */
//    public static void pushToUser(Long userId, String notification) {
//        if(userId == null || userId == 0 || StringUtils.isBlank(notification)){
//            return;
//        }
//        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
//                .setNotification(Notification.alert(notification))
//                .setAudience(Audience.alias(String.valueOf(userId)))
//                .build();
//
//        push(payload);
//    }

    /**
     * 推送通知和消息给指定用户，带message的消息会在APP内弹出窗口，title为弹窗标题
     */
    public static void pushToUser(Long userId, String notification, String message) {
        if(userId == null || userId == 0 || StringUtils.isBlank(notification) || StringUtils.isBlank(message)){
            return;
        }

        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
                .setNotification(Notification.alert(notification))
                .setMessage(Message.newBuilder().setMsgContent(message).addExtra("title", notification).build())
                .setAudience(Audience.alias(String.valueOf(userId)))
                .build();

        push(payload);
    }

    private static void push(PushPayload payload) {
        try {
            jPushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.warn("send push error! {}", e.getLocalizedMessage());
        } catch (APIRequestException e) {
            // 忽略1011=没有满足条件的推送目标
            if (e.getErrorCode() != 1011) {
                logger.warn("send push error! {}", e.getLocalizedMessage());
            }
        }
    }

    public static void main(String[] args) {
        pushToUser(666888L, "通知", "内容");
    }
}
