package com.iyeed.core.utils;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DAVID on 2017/12/6.
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
    private static final Pattern PATTERN_PHONE = Pattern.compile("^1[34578]\\d{9}$");

    /**
     * 检查手机号是否正确
     * @param phone
     * @return
     */
    public static boolean validPhone(String phone) {
        if (phone == null) {
            return false;
        }
        return PATTERN_PHONE.matcher(phone).matches();
    }

    /**
     * Html转Text纯文本
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            log.error("Html2Text 出错了，错误内容:{}", e);
        }
        return textStr;// 返回文本字符串
    }

    public static String startTime(String startTime) {

        if (!StringUtil.isNullOrEmpty(startTime) && startTime.length() <= 10) {
            startTime += " 00:00:00";
        }
        return startTime;
    }

    public static String endTime(String endTime) {
        if (!StringUtil.isNullOrEmpty(endTime) && endTime.length() <= 10) {
            endTime += " 23:59:59";
        }
        return endTime;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static void main(String[] args) {
        String url = Html2Text("<a Style='color:red'>测试<adsfg>内容</a><img src='asdfadg.jpg' />");
        System.out.println(url);
    }
}
