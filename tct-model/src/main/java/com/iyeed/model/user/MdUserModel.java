package com.iyeed.model.user;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.dao.db.write.xzn.user.MdUserWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MdUserModel {
    
    @Resource
    private MdUserWriteDao mdUserWriteDao;
    
    /**
     * 根据id取得md_user对象
     * @param  mdUserId
     * @return
     */
    public MdUser getMdUserById(Integer mdUserId) throws Exception {
    	return mdUserWriteDao.get(mdUserId);
    }

    public MdUser getMdUserByUserNo(String userNo) throws Exception {
        return mdUserWriteDao.getUserByUserNo(userNo);
    }
    
    /**
     * 保存md_user对象
     * @param  mdUser
     * @return
     */
    public Integer saveMdUser(MdUser mdUser) throws Exception {
     	this.dbConstrains(mdUser);
     	return mdUserWriteDao.insert(mdUser);
    }
     
    /**
     * 更新md_user对象
     * @param  mdUser
     * @return
     */
    public Integer updateMdUser(MdUser mdUser) throws Exception {
        this.dbConstrains(mdUser);
     	return mdUserWriteDao.update(mdUser);
    }
     
    private void dbConstrains(MdUser mdUser) throws Exception {
		mdUser.setUserId(StringUtil.dbSafeString(mdUser.getUserId(), true, 40));
		mdUser.setUserNo(StringUtil.dbSafeString(mdUser.getUserNo(), true, 40));
		mdUser.setUserName(StringUtil.dbSafeString(mdUser.getUserName(), true, 40));
		mdUser.setUserPid(StringUtil.dbSafeString(mdUser.getUserPid(), true, 40));
		mdUser.setPost(StringUtil.dbSafeString(mdUser.getPost(), true, 40));
		mdUser.setEmail(StringUtil.dbSafeString(mdUser.getEmail(), true, 100));
    }
}