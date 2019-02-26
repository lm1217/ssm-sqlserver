package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.vo.SystemBrandUserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemUserWriteDao {

    SystemUser get(Integer id) throws Exception;

    Integer save(SystemUser systemUser) throws Exception;

    Integer insertUserBrand(SystemUserBrand systemUserBrand) throws Exception;

    Integer update(SystemUser systemUser) throws Exception;

    Integer getCount(@Param("queryMap") Map<String, String> queryMap) throws Exception;

    List<SystemUser> page(@Param("queryMap") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size) throws Exception;

    SystemUser getByNamePwd(Map<String, Object> queryMap) throws Exception;

    SystemUser getByUserNo(String userNo) throws Exception;

    SystemUserBrand getByUsername(String username) throws Exception;

    Integer del(Integer id) throws Exception;

    Integer updateLogins(Integer id) throws Exception;

    List<SystemBrandUserBean> getBrandUserList(@Param("queryMap") Map<String, Object> queryMap,
                                               @Param("start") Integer start,
                                               @Param("size") Integer size) throws Exception;

    Integer getBrandUserListCount(@Param("queryMap") Map<String, Object> queryMap) throws Exception;

}
