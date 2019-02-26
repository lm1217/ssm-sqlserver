package com.iyeed.service.excel;

import com.iyeed.core.ServiceResult;

import java.util.List;
import java.util.Map;

public interface IImportExcelService {
    ServiceResult<Integer> updateReceiving(List<Map<String, Object>> objList, String userNo);

    ServiceResult<Integer> updateTypeReceiving(List<Map<String, Object>> objList);

    ServiceResult<Integer> updateStore(List<Map<String, Object>> objList);
}
