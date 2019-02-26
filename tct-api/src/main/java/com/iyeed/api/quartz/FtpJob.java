package com.iyeed.api.quartz;

import com.iyeed.service.ftp.IFtpService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FtpJob {
    private static final String ESEHOME = "E:/ftp";
    @Resource
    private IFtpService ftpService;

    /** 定时任务，每五分钟执行一次 */
    @Scheduled(cron = "0 */5 * * * ?")
    private void executeMdUser() {
//        ftpService.executeMdUser(ESEHOME);
    }

}
