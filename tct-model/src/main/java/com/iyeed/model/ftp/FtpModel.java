package com.iyeed.model.ftp;

import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.exception.ArgumentException;
import com.iyeed.core.utils.CsvFileUtil;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Component
public class FtpModel extends BaseModel {

    @Transactional(rollbackFor = Exception.class)
    public void saveCsvMdUser(String url, String fileName) throws Exception {
        File file = new File(url);
        List<List<String>> csvList = null;

        CsvFileUtil csv = new CsvFileUtil(file.getAbsolutePath());
        //按照逗号","分割截取
        csvList = csv.readCSVFile();

        if (null == csvList || csvList.size() == 0){
            throw new ArgumentException("上传的文件格式不正确");
        }

        List<String> cellValue = null;
        MdUser user = null;

        for (int i = 1; i < csvList.size(); i++) {
            cellValue = csvList.get(i);
            if (cellValue != null && cellValue.size() >= 4) {
                user = new MdUser();
                user.setUserId(cellValue.get(0));
                user.setUserNo(cellValue.get(1));
                user.setUserName(cellValue.get(2));
                user.setUserPid(cellValue.get(3));
                user.setUserEmail(cellValue.get(5));
                mdUserWriteDao.insert(user);
            }
        }
    }

}
