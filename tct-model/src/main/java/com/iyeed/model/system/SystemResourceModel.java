package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemResourceModel extends BaseModel {

    /**
    * 根据id取得资源表
    * @param  systemResourceId
    * @return
    */
    public SystemResource getSystemResourceById(Integer systemResourceId) throws Exception {
        return systemResourceWriteDao.get(systemResourceId);
    }

    /**
     * 保存资源表
     * @param  systemResource
     * @return
     */
    public Integer saveSystemResource(SystemResource systemResource) throws Exception {
        return systemResourceWriteDao.save(systemResource);
    }

    /**
    * 更新资源表
    * @param  systemResource
    * @return
    */
    public Integer updateSystemResource(SystemResource systemResource) throws Exception {
        return systemResourceWriteDao.update(systemResource);
    }

    public Integer pageCount(Map<String, String> queryMap) throws Exception {
        return systemResourceWriteDao.getCount(queryMap);
    }

    public List<SystemResource> page(Map<String, String> queryMap) throws Exception {
        return systemResourceWriteDao.page(queryMap);
    }

    public Boolean del(Integer id) throws Exception {
        return systemResourceWriteDao.del(id) > 0;
    }

    public List<SystemResource> getByPid(Integer pid) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pid", pid);
        return systemResourceWriteDao.list(param);
    }
}
