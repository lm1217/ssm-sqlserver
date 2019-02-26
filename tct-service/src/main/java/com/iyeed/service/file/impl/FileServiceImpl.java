package com.iyeed.service.file.impl;

import com.iyeed.service.BaseService;
import com.iyeed.service.file.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service(value = "fileService")
public class FileServiceImpl extends BaseService implements IFileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public void executeUserMapping(String fileDir) {
        try {
            File file = new File(fileDir);
            // 检测文件是否存在
            if (!file.exists() || !file.isFile()) {
                log.info("文件：" + fileDir + "不存在,待更新！");
            } else {
                fileModel.saveCsvUserMapping(file);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void executeDoorMapping(String fileDir) {
        try {
            File file = new File(fileDir);
            // 检测文件是否存在
            if (!file.exists() || !file.isFile()) {
                log.info("文件：" + fileDir + "不存在,待更新！");
            } else {
                fileModel.saveCsvDoorMapping(file);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
