package com.iyeed.service.user.impl;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.service.BaseService;
import com.iyeed.service.user.IMdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "mdUserService")
public class MdUserServiceImpl extends BaseService implements IMdUserService {
	private static final Logger log = LoggerFactory.getLogger(MdUserServiceImpl.class);

    /**
     * 根据id取得md_user对象
     * @param  mdUserId
     * @return
     */
    @Override
    public ServiceResult<MdUser> getMdUserById(Integer mdUserId) {
        ServiceResult<MdUser> result = new ServiceResult<MdUser>();
        try {
            result.setResult(mdUserModel.getMdUserById(mdUserId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdUserService][getMdUserById]根据id["+mdUserId+"]取得md_user对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdUserService][getMdUserById]根据id["+mdUserId+"]取得md_user对象时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<MdUser> getMdUserByUserNo(String userNo) {
        ServiceResult<MdUser> result = new ServiceResult<MdUser>();
        try {
            result.setResult(mdUserModel.getMdUserByUserNo(userNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdUserService][getMdUserById]根据id["+userNo+"]取得md_user对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdUserService][getMdUserById]根据id["+userNo+"]取得md_user对象时出现未知异常：",
                    e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MdUser>> getUserListByUserNo(String userNo) {
        ServiceResult<List<MdUser>> result = new ServiceResult<>();
        try {
            result.setResult(mdUserModel.getUserListByUserNo(userNo));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdUserService][getMdUserById]根据id["+userNo+"]取得md_user对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdUserService][getMdUserById]根据id["+userNo+"]取得md_user对象时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 保存md_user对象
     * @param  mdUser
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMdUser(MdUser mdUser) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdUserModel.saveMdUser(mdUser));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdUserService][saveMdUser]保存md_user对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdUserService][saveMdUser]保存md_user对象时出现未知异常：",
                e);
        }
        return result;
    }
    /**
     * 更新md_user对象
     * @param  mdUser
     * @return
     */
    @Override
    public ServiceResult<Integer> updateMdUser(MdUser mdUser) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mdUserModel.updateMdUser(mdUser));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMdUserService][updateMdUser]更新md_user对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMdUserService][updateMdUser]更新md_user对象时出现未知异常：",
                e);
        }
        return result;
    }
}