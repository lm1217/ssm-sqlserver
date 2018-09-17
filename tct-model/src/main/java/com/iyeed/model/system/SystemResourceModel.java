package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.dao.db.write.xzn.system.SystemResourceWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemResourceModel {

    @Resource
    private SystemResourceWriteDao systemResourceWriteDao;

    /**
    * 根据id取得资源表
    * @param  systemResourceId
    * @return
    */
    public SystemResource getSystemResourceById(Integer systemResourceId) {
        return systemResourceWriteDao.get(systemResourceId);
    }

    /**
     * 保存资源表
     * @param  systemResource
     * @return
     */
    public Integer saveSystemResource(SystemResource systemResource) {
        return systemResourceWriteDao.save(systemResource);
    }

    /**
    * 更新资源表
    * @param  systemResource
    * @return
    */
    public Integer updateSystemResource(SystemResource systemResource) {
        return systemResourceWriteDao.update(systemResource);
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return systemResourceWriteDao.getCount(queryMap);
    }

    public List<SystemResource> page(Map<String, String> queryMap) {
        return systemResourceWriteDao.page(queryMap);
    }

    public Boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除资源表[" + id + "]时出错");
        return systemResourceWriteDao.del(id) > 0;
    }

    public List<SystemResource> getByPid(Integer pid) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pid", pid);
        return systemResourceWriteDao.list(param);
    }
}
