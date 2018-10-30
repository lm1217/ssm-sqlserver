package com.iyeed.service.user.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.user.IMdUserStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "mdUserStoreService")
public class MdUserStoreServiceImpl extends BaseService implements IMdUserStoreService {
	private static final Logger log = LoggerFactory.getLogger(MdUserStoreServiceImpl.class);

    /**
     * 根据id取得md_user_store对象
     * @param  mdUserStoreId
     * @return
     */
    @Override
    public ServiceResult<MdUserStore> getMdUserStoreById(Integer mdUserStoreId) {
        ServiceResult<MdUserStore> result = new ServiceResult<MdUserStore>();
        try {
            result.setResult(mdUserStoreModel.getMdUserStoreById(mdUserStoreId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + mdUserStoreId + "]取得md_user_store对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + mdUserStoreId + "]取得md_user_store对象时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MdUserStore>> getMdUserStoreListByUserNo(String userNo) {
        ServiceResult<List<MdUserStore>> result = new ServiceResult<>();
        try {
            result.setResult(mdUserStoreModel.getMdUserStoreListByUserNo(userNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + userNo + "]取得md_user_store对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + userNo + "]取得md_user_store对象时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MdUserStore>> getMdUserStoreListByUserNoArr(String[] userNoArr) {
        ServiceResult<List<MdUserStore>> result = new ServiceResult<>();
        try {
            result.setResult(mdUserStoreModel.getMdUserStoreListByUserNoArr(userNoArr));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("根据id[" + userNoArr.toString() + "]取得md_user_store对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("根据id[" + userNoArr.toString() + "]取得md_user_store对象时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存md_user_store对象
     * @param  mdUserStore
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdUserStore(MdUserStore mdUserStore) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdUserStoreModel.saveMdUserStore(mdUserStore));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("保存md_user_store对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("保存md_user_store对象时出现未知异常：", e);
        }
        return result;
    }
    /**
     * 更新md_user_store对象
     * @param  mdUserStore
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdUserStore(MdUserStore mdUserStore) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdUserStoreModel.updateMdUserStore(mdUserStore));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("更新md_user_store对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("更新md_user_store对象时出现未知异常：", e);
        }
        return result;
    }
}