package com.iyeed.service.excel.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.excel.IImportExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "importExcelService")
public class ImportExcelServiceImpl extends BaseService implements IImportExcelService {
    private static final Logger log = LoggerFactory.getLogger(ImportExcelServiceImpl.class);

    @Override
    public ServiceResult<Integer> updateReceiving(List<Map<String, Object>> objList, String userNo) {
        ServiceResult<Integer> result = new ServiceResult<>();
        try {
            result.setResult(excelModel.updateReceiving(objList, userNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据objList[" + objList + "]出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据objList[" + objList + "]出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateTypeReceiving(List<Map<String, Object>> objList) {
        ServiceResult<Integer> result = new ServiceResult<>();
        try {
            result.setResult(excelModel.updateTypeReceiving(objList));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据objList[" + objList + "]出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据objList[" + objList + "]出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateStore(List<Map<String, Object>> objList) {
        ServiceResult<Integer> result = new ServiceResult<>();
        try {
            result.setResult(excelModel.updateStore(objList));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据objList[" + objList + "]出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据objList[" + objList + "]出现未知异常：", e);
        }
        return result;
    }
}
