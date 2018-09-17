package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemUserWriteDao {

    SystemUser get(Integer id);

    Integer save(SystemUser systemUser);

    Integer update(SystemUser systemUser);

    Integer getCount(@Param("queryMap") Map<String, String> queryMap);

    List<SystemUser> page(@Param("queryMap") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);

    SystemUser getByNamePwd(Map<String, Object> queryMap);

    Integer del(Integer id);

}
