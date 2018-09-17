package com.iyeed.dao.db.read.xzn.system;

import com.iyeed.core.entity.system.SystemResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemResourceReadDao {

    SystemResource get(Integer id);

    Integer getCount(Map<String, String> queryMap);

    List<SystemResource> page(Map<String, String> queryMap);

    List<SystemResource> list(Map<String, Object> queryMap);

    List<SystemResource> getByPId(Integer pid);

}
