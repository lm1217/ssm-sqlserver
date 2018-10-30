package com.iyeed.api.quartz;

import com.iyeed.service.form.IBdFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FormJob {
    private static final Logger log = LoggerFactory.getLogger(FormJob.class);

    @Resource
    private IBdFormService bdFormService;

    /** 定时任务，每五分钟执行一次 */
    @Scheduled(cron = "0 */5 * * * ?")
    private void sendFormMail() {
//        bdFormService.sendFormMail();
    }

    /** 定时任务，每天凌晨3点执行一次 */
    @Scheduled(cron = "0 */5 * * * ?")
    private void changeAudit() {
//        bdFormService.changeAudit();
    }
}
