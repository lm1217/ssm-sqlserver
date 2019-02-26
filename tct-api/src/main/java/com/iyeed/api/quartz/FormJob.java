package com.iyeed.api.quartz;

import com.iyeed.service.form.IBdFormService;
import com.iyeed.service.stock.IBdStockInvDataService;
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
    @Resource
    private IBdStockInvDataService bdStockInvDataService;

    /** 定时任务，每小时执行一次 (审批邮件提醒)*/
    @Scheduled(cron = "0 * */1 * * ?")
    private void sendFormMail() {
        bdFormService.sendFormMail();
    }

    /** 定时任务，每天凌晨3点执行一次 (审批检查，超时跳级)*/
    @Scheduled(cron = "0 0 3 * * ?")
    private void changeAudit() {
        bdFormService.changeAudit();
    }

    /** 定时任务，每天凌晨1点执行一次 (备份库存表)*/
    @Scheduled(cron = "0 0 1 * * ?")
    private void insertForBackup() {
        //TODO 此任务交给sql job 来完成
//        bdStockInvDataService.insertForBackup();
    }
}
