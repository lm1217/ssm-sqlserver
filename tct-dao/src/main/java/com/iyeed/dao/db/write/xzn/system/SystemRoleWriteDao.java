package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemRoleWriteDao {

    SystemRole get(Integer id);

    Integer save(SystemRole systemRole);

    Integer update(SystemRole systemRole);

    Integer getCount(@Param("queryMap") Map<String, String> queryMap);

    List<SystemRole> page(@Param("queryMap") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);

    Integer del(Integer id);

}
