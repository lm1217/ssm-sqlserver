package com.iyeed.dao.db.read.xzn.system;

import com.iyeed.core.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemUserReadDao {

    SystemUser get(Integer id);

    Integer getCount(@Param("queryMap") Map<String, String> queryMap);

    List<SystemUser> page(@Param("queryMap") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);

    List<SystemUser> getByName(@Param("username") String username);

}
