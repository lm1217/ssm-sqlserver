package com.iyeed.service.user;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.user.MdUser;

import java.util.List;

public interface IMdUserService {

    /**
     * 根据id取得md_user对象
     * @param  mdUserId
     * @return
     */
    ServiceResult<MdUser> getMdUserById(Integer mdUserId);

    ServiceResult<MdUser> getMdUserByUserNo(String userNo);

    ServiceResult<List<MdUser>> getUserListByUserNo(String userNo);
    
    /**
     * 保存md_user对象
     * @param  mdUser
     * @return
     */
    ServiceResult<Integer> saveMdUser(MdUser mdUser);
     
    /**
     * 更新md_user对象
     * @param  mdUser
     * @return
     */
    ServiceResult<Integer> updateMdUser(MdUser mdUser);
}