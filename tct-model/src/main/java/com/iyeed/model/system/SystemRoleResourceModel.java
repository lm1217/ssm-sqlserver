package com.iyeed.model.system;

import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemRoleResource;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.dao.db.write.xzn.system.SystemRoleResourceWriteDao;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemRoleResourceModel {

    @Resource
    private SystemRoleResourceWriteDao systemRoleResourceWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
    * 根据id取得角色资源对应表
    * @param  systemRoleResourceId
    * @return
    */
    public SystemRoleResource getSystemRoleResourceById(Integer systemRoleResourceId) {
        return systemRoleResourceWriteDao.get(systemRoleResourceId);
    }

    /**
     * 保存角色资源对应表
     * @param  systemRoleResource
     * @return
     */
    public Integer saveSystemRoleResource(SystemRoleResource systemRoleResource) {
        return systemRoleResourceWriteDao.save(systemRoleResource);
    }

    /**
    * 更新角色资源对应表
    * @param  systemRoleResource
    * @return
    */
    public Integer updateSystemRoleResource(SystemRoleResource systemRoleResource) {
        return systemRoleResourceWriteDao.update(systemRoleResource);
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return systemRoleResourceWriteDao.getCount(queryMap);
    }

    public List<SystemRoleResource> page(Map<String, String> queryMap) {
        return systemRoleResourceWriteDao.page(queryMap);
    }

    public boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除角色资源对应表[" + id + "]时出错");
        return systemRoleResourceWriteDao.del(id) > 0;
    }

    public boolean save(String roleId, String[] resArr) throws Exception {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //删除此角色之前的资源关联
            systemRoleResourceWriteDao.delByRole(roleId);

            //保存选中的资源
            for (String resId : resArr) {
                SystemRoleResource srr = new SystemRoleResource();
                srr.setResourceId(Integer.valueOf(resId));
                srr.setRoleId(Integer.valueOf(roleId));
                systemRoleResourceWriteDao.save(srr);
            }

            transactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            throw e;
        }
        return true;
    }

    public List<SystemResource> getResourceByRoleId(Integer roleId) {
        if (roleId == null)
            throw new BusinessException("未指定角色");
        return systemRoleResourceWriteDao.getResourceByRoleId(roleId);
    }

    /**
     * <b>公用方法</b><br>
     * 此资源下所有有权限的子资源
     * @param pid
     * @param roleId
     * @return
     * @throws BusinessException
     */
    public List<SystemResource> getResourceByPid(Integer pid, Integer roleId) throws BusinessException {
        if (pid == null)
            throw new BusinessException("未指定父资源");
        if (roleId == null)
            throw new BusinessException("未指定角色");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        map.put("roleId", roleId);
        return systemRoleResourceWriteDao.getResourceByPid(map);
    }
}
