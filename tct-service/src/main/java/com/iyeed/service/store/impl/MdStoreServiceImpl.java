package com.iyeed.service.store.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.store.IMdStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "mdStoreService")
public class MdStoreServiceImpl extends BaseService implements IMdStoreService {
	private static final Logger log = LoggerFactory.getLogger(MdStoreServiceImpl.class);

    @Override
    public ServiceResult<MdStore> getMdStoreByStoreNo(String storeNo) {
        ServiceResult<MdStore> result = new ServiceResult<MdStore>();
        try {
            result.setResult(mdStoreModel.getMdStoreByStoreNo(storeNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][getMdStoreById]根据id["+storeNo+"]取得门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][getMdStoreById]根据id["+storeNo+"]取得门店表时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 根据id取得门店表
     * @param  mdStoreId
     * @return
     */
    @Override
    public ServiceResult<MdStore> getMdStoreById(Integer mdStoreId) {
        ServiceResult<MdStore> result = new ServiceResult<MdStore>();
        try {
            result.setResult(mdStoreModel.getMdStoreById(mdStoreId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][getMdStoreById]根据id["+mdStoreId+"]取得门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][getMdStoreById]根据id["+mdStoreId+"]取得门店表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetStoreListBean>> getStoreListByBrandNo(String brandNo) {
        ServiceResult<List<GetStoreListBean>> result = new ServiceResult<>();
        try {
            result.setResult(mdStoreModel.getStoreListByBrandNo(brandNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][getMdStoreById]根据id[]取得门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][getMdStoreById]根据id[]取得门店表时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<GetStoreListBean>> getStoreList() {
        ServiceResult<List<GetStoreListBean>> result = new ServiceResult<>();
        try {
            result.setResult(mdStoreModel.getStoreList());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][getMdStoreById]根据id[]取得门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][getMdStoreById]根据id[]取得门店表时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 保存门店表
     * @param  mdStore
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdStore(MdStore mdStore) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdStoreModel.saveMdStore(mdStore));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][saveMdStore]保存门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][saveMdStore]保存门店表时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新门店表
     * @param  mdStore
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdStore(MdStore mdStore) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdStoreModel.updateMdStore(mdStore));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdStoreService][updateMdStore]更新门店表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdStoreService][updateMdStore]更新门店表时出现未知异常：",
                e);
        }
        return result;
    }
}