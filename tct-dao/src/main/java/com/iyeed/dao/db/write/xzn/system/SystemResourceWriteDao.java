package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemResourceWriteDao {

    SystemResource get(Integer id);

    Integer save(SystemResource systemResource);

    Integer update(SystemResource systemResource);

    Integer getCount(Map<String, String> queryMap);

    List<SystemResource> page(Map<String, String> queryMap);

    List<SystemResource> list(Map<String, Object> queryMap);

    Integer del(Integer id);

}
