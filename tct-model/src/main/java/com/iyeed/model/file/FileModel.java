package com.iyeed.model.file;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.hrmd.DoorMapping;
import com.iyeed.core.entity.hrmd.UserMapping;
import com.iyeed.core.exception.ArgumentException;
import com.iyeed.core.utils.CsvFileUtil;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

@Component
public class FileModel extends BaseModel {
    @Transactional(rollbackFor = Exception.class)
    public void saveCsvUserMapping(File file) throws Exception {
        List<List<String>> csvList = null;

        CsvFileUtil csv = new CsvFileUtil(file.getAbsolutePath());
        //按照逗号","分割截取
        csvList = csv.readCSVFile();

        if (null == csvList || csvList.size() == 0){
            throw new ArgumentException("上传的文件格式不正确");
        }

        hrUserMappingWriteDao.del();

        List<String> cellValue = null;
        UserMapping userMapping = null;
        for (int i = 1; i < csvList.size(); i++) {
            cellValue = csvList.get(i);
            if (cellValue != null && cellValue.size() >= 6) {
                //过滤程丽这条数据，HR那边导出时无法过滤
                if (cellValue.get(3).equals("1071199") && cellValue.get(4).equals("1074789"))
                    continue;
                userMapping = new UserMapping();
                userMapping.setUserName(cellValue.get(0));
                userMapping.setUserNameEn(cellValue.get(1));
                userMapping.setUserNtaccount(cellValue.get(2));
                userMapping.setUserId(cellValue.get(3));
                userMapping.setUserPid(cellValue.get(4));
                userMapping.setUserEmail(cellValue.get(5));
                hrUserMappingWriteDao.insert(userMapping);
            }
        }
        backupCsvFile(file);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCsvDoorMapping(File file) throws Exception {
        List<List<String>> csvList = null;

        CsvFileUtil csv = new CsvFileUtil(file.getAbsolutePath());
        //按照逗号","分割截取
        csvList = csv.readCSVFile();

        if (null == csvList || csvList.size() == 0){
            throw new ArgumentException("上传的文件格式不正确");
        }

        hrDoorMappingWriteDao.del();

        List<String> cellValue = null;
        DoorMapping doorMapping = null;
        for (int i = 1; i < csvList.size(); i++) {
            cellValue = csvList.get(i);
            if (cellValue != null && cellValue.size() >= 7) {
                doorMapping = new DoorMapping();
                doorMapping.setBrandCode(cellValue.get(0));
                doorMapping.setStoreNo(cellValue.get(1));
                doorMapping.setStoreName(cellValue.get(2));
                doorMapping.setUserName(cellValue.get(3));
                doorMapping.setUserNameEn(cellValue.get(4));
                doorMapping.setUserNtaccount(cellValue.get(5));
                doorMapping.setUserId(cellValue.get(6));
                hrDoorMappingWriteDao.insert(doorMapping);
            }
        }
        backupCsvFile(file);
    }
}
