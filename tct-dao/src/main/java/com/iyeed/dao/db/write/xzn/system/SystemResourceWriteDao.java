package com.iyeed.dao.db.write.xzn.system;

import com.iyeed.core.entity.system.SystemResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemResourceWriteDao {

    SystemResource get(Integer id) throws Exception;

    Integer save(SystemResource systemResource) throws Exception;

    Integer update(SystemResource systemResource) throws Exception;

    Integer getCount(Map<String, String> queryMap) throws Exception;

    List<SystemResource> page(Map<String, String> queryMap) throws Exception;

    List<SystemResource> list(Map<String, Object> queryMap) throws Exception;

    Integer del(Integer id) throws Exception;

}
