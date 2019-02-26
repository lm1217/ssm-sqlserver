package com.iyeed.api.quartz;

import com.iyeed.service.file.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileJob {
    private static final Logger log = LoggerFactory.getLogger(FileJob.class);
    private static final String USER_MAPPING_DIR = "D:\\TCT\\HRDATA\\user_mapping.csv";
    private static final String DOOR_MAPPING_DIR = "D:\\TCT\\HRDATA\\door_mapping.csv";
    @Resource
    private IFileService fileService;
    /** 定时任务，每天凌晨12点30分执行一次 */
    @Scheduled(cron = "0 30 0 * * ?")
    private void executeUserMapping() {
        fileService.executeUserMapping(USER_MAPPING_DIR);
    }

    /** 定时任务，每天凌晨12点30分执行一次 */
    @Scheduled(cron = "0 30 0 * * ?")
    private void executeDoorMapping() {
        fileService.executeDoorMapping(DOOR_MAPPING_DIR);
    }

}
