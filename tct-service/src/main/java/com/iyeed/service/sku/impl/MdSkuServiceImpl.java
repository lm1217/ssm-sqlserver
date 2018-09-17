package com.iyeed.service.sku.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.sku.MdSku;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.sku.MdSkuModel;
import com.iyeed.service.sku.IMdSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "mdSkuService")
public class MdSkuServiceImpl implements IMdSkuService {
	private static final Logger log = LoggerFactory.getLogger(MdSkuServiceImpl.class);

	@Resource
    private MdSkuModel mdSkuModel;

    /**
     * 根据id取得SKU表
     * @param  mdSkuId
     * @return
     */
    @Override
    public ServiceResult<MdSku> getMdSkuById(Integer mdSkuId) {
        ServiceResult<MdSku> result = new ServiceResult<MdSku>();
        try {
            result.setResult(mdSkuModel.getMdSkuById(mdSkuId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + mdSkuId + "]取得SKU表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + mdSkuId + "]取得SKU表时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MdSku>> getSkuList(Map<String, Object> queryMap) {
        ServiceResult<List<MdSku>> result = new ServiceResult<>();
        try {
            result.setResult(mdSkuModel.getSkuList(queryMap));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + queryMap + "]取得SKU表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + queryMap + "]取得SKU表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存SKU表
     * @param  mdSku
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdSku(MdSku mdSku) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdSkuModel.saveMdSku(mdSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存SKU表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存SKU表时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新SKU表
     * @param  mdSku
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdSku(MdSku mdSku) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdSkuModel.updateMdSku(mdSku));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新SKU表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新SKU表时出现未知异常：", e);
        }
        return result;
    }
}