package com.iyeed.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统配置参数
 * Created by Junhua.Zhang on 2016/8/5.
 */
public class SystemConfig {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    private String sysEnv;//当前环境

    public static void main(String[] args) {

    }

    public void setSysEnv(String sysEnv) {
        this.sysEnv = sysEnv;
    }

    public String getSysEnv() {
        return sysEnv;
    }

    public boolean isProEnv() {
        return "pro".equalsIgnoreCase(sysEnv);
    }
}
