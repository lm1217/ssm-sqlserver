package com.iyeed.model.excel;

import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class ExcelModel extends BaseModel {
    @Transactional(rollbackFor = Exception.class)
    public Integer updateReceiving(List<Map<String, Object>> objList, String userNo) throws Exception {
        for (Map<String, Object> map : objList) {
            bdReceivingWriteDao.updateByPkgNo(map.get("pkgNo").toString(), userNo);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateTypeReceiving(List<Map<String, Object>> objList) throws Exception {
        for (Map<String, Object> map : objList) {
            String str = null;
            if (map.get("receiveType") != null) {
                str = map.get("receiveType").toString();
                if (str.length() - 5 > 0) {
                    str = str.substring(str.length() - 5).toUpperCase();
                    if (str.contains("VM")) {
                        str = "VM";
                    } else if (str.contains("NEW")) {
                        str = "NEW";
                    } else if (str.contains("RNWS")) {
                        str = "RNWS";
                    }
                }
            }
            bdReceivingWriteDao.updateTypeByPkgNo(map.get("pkgNo").toString(), str);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateStore(List<Map<String, Object>> objList) throws Exception {
        for (Map<String, Object> map : objList) {
            mdStoreWriteDao.updateStore(map.get("brand").toString(),
                                        map.get("door").toString(),
                                        map.get("region").toString(),
                                        map.get("city").toString(),
                                        map.get("channel").toString(),
                                        map.get("doorType").toString(),
                                        map.get("asm").toString(),
                                        map.get("ac").toString());
        }
        return null;
    }
}
