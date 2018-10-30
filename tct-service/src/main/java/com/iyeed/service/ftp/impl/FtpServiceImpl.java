package com.iyeed.service.ftp.impl;

import com.iyeed.core.ftp.FTPDeal;
import com.iyeed.model.ftp.FtpModel;
import com.iyeed.service.ftp.IFtpService;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

@Service(value = "ftpService")
public class FtpServiceImpl implements IFtpService {
    private static final Logger log = LoggerFactory.getLogger(FtpServiceImpl.class);

    @Resource
    private FTPDeal ftpDeal;
    @Resource
    private FtpModel ftpModel;
    private String orginRemoteDirectory;

    @Override
    public void executeMdUser(String aseHome) {
        try {
            ftpDeal.connect();
            orginRemoteDirectory = ftpDeal.cleanDirectory(ftpDeal.cd(null), orginRemoteDirectory);

            String remoteDataDir = ftpDeal.mkdir(orginRemoteDirectory, new String[] {"DATA"});

            for (String filename : ftpDeal.ls(orginRemoteDirectory)) {
                ftpDeal.rename(orginRemoteDirectory + "/" + filename, remoteDataDir + "/" + filename);
            }
            File dataFile = null;
            String localFilename = null;
            String remoteBackupDir = ftpDeal.mkdir(remoteDataDir, new String[] {"BACKUP", DateUtils.formatDate(new Date(), "yyyyMMdd")});
            String toDealDir = ftpDeal.mkdir(remoteDataDir, new String[] {"TODEAL", DateUtils.formatDate(new Date(), "yyyyMMdd")});

            File targetDateFile = new File(aseHome  +  toDealDir);

            for (String filename : ftpDeal.ls(remoteDataDir)) {
                if (!"BACKUP".equals(filename) && !"TODEAL".equals(filename)) {
                    try {
                        localFilename = filename;
                        if (new File(toDealDir + File.separator + localFilename).exists()) {
                            localFilename = localFilename.substring(0, localFilename.lastIndexOf(".")) + "-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS") + localFilename.substring(localFilename.lastIndexOf("."));
                        }
                        dataFile = ftpDeal.get(remoteDataDir + "/" + filename, targetDateFile.getAbsolutePath() + File.separator + localFilename);

                        if (dataFile != null) {
                            ftpDeal.rename(remoteDataDir + "/" + filename, remoteBackupDir + "/" + filename);
                        }
                        ftpModel.saveCsvMdUser(targetDateFile.getAbsolutePath() + File.separator + localFilename, localFilename);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            if (ftpDeal != null) {
                ftpDeal.disconnect();
            }
        }

    }

}
