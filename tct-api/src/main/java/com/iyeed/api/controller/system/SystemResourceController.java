package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemResourceTree;
import com.iyeed.core.entity.system.SystemRole;
import com.iyeed.core.entity.system.SystemRoleResource;
import com.iyeed.service.system.ISystemResourceService;
import com.iyeed.service.system.ISystemRoleResourceService;
import com.iyeed.service.system.ISystemRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:资源管理controller
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/system/resource")
@Scope("prototype")
public class SystemResourceController extends BaseController {
    private List<Integer> roleResourceIds = new ArrayList<Integer>();
    private List<SystemResource> resourceAll;
    private byte[] lock = new byte[0];

    private static final Logger log = LoggerFactory.getLogger(SystemResourceController.class);

    /**
     * 编辑资源(根据资源ID获取资源信息)
     * @param resourceId
     * @return
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "编辑资源-根据资源ID获取资源信息")
    @RequestMapping(value = "editResource.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse editResource(@RequestParam(value = "id") Integer resourceId) {
        ServiceResult<SystemResource> serviceResult = systemResourceService.getSystemResourceById(resourceId);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("resource", serviceResult.getResult());

        List<SystemResourceTree> tree = new ArrayList<>();
        ServiceResult<List<SystemResource>> serviceResultList = systemResourceService.page(new HashMap<>(), null);
        if (!serviceResultList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        synchronized (lock) {
            this.resourceAll = serviceResultList.getResult();
            generateTree(tree, getByPid(0));
        }
        dataMap.put("resourceTree", tree);
        return AjaxResponse.success(dataMap);
    }

    /**
     * 根据资源ID获取资源子列表
     * @param pid
     * @return
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "根据资源ID获取资源子列表")
    @RequestMapping(value = "resourceList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse resourceList(@RequestParam(value = "id") Integer pid) {
        ServiceResult<List<SystemResource>> serviceResult = systemResourceService.getByPid(pid);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("resourceList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:获取整个资源树
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/23 14:11
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "获取整个资源树")
    @RequestMapping(value = "resourceTree.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse resourceTree() {
        List<SystemResourceTree> tree = new ArrayList<>();
        ServiceResult<List<SystemResource>> serviceResult = systemResourceService.page(new HashMap<>(), null);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        synchronized (lock) {
            this.resourceAll = serviceResult.getResult();
            generateTree(tree, getByPid(0));
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("resourceTree", tree);
        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:根据角色id获取角色资源树(用于分配权限时候的展示)
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/23 14:09
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "根据角色id获取角色资源树")
    @RequestMapping(value = "roleResourceTree.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse roleResourceTree(@RequestParam String roleId) {
        List<SystemResourceTree> tree = new ArrayList<>();
        ServiceResult<List<SystemResource>> serviceResult = systemResourceService.getByPid(ConstantsEJS.SYSTEM_RESOURCE_ROOT);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("roleId", roleId);

        SystemRole role = systemRoleService.getSystemRoleById(Integer.valueOf(roleId)).getResult();
        List<SystemRoleResource> srrlist = systemRoleResourceService.page(queryMap, null).getResult();
        for (SystemRoleResource sr : srrlist) {
            this.roleResourceIds.add(sr.getResourceId());
        }
        synchronized (lock) {
            this.resourceAll = systemResourceService.page(new HashMap<>(), null).getResult();
            generateTree(tree, serviceResult.getResult());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("role", role);
        dataMap.put("roleResourceTree", tree);
        return AjaxResponse.success(dataMap);
    }

    /**
     * 递归生成树
     * @param systemResourceTreeList
     * @param systemResourceList
     * @return
     */
    private List<SystemResourceTree> generateTree(List<SystemResourceTree> systemResourceTreeList,
                                            List<SystemResource> systemResourceList) {
        for (SystemResource systemResource : systemResourceList) {
            SystemResourceTree systemResourceTree = new SystemResourceTree();
            systemResourceTree.setId(systemResource.getId());
            systemResourceTree.setText(systemResource.getContent());
            if (this.roleResourceIds != null && this.roleResourceIds.contains(systemResource.getId()))
                systemResourceTree.setChecked(true);
            systemResourceTree.setChildren(generateTree(new ArrayList<>(), getByPid(systemResource.getId())));
            systemResourceTree.setState(systemResourceTree.getChildren().size() > 0 ? "closed" : "open");
            systemResourceTreeList.add(systemResourceTree);
        }
        return systemResourceTreeList;
    }

    private List<SystemResource> getByPid(Integer pid) {
        if (this.resourceAll == null || this.resourceAll.size() < 1)
            return null;
        List<SystemResource> reslist = new ArrayList<>();
        for (SystemResource res : this.resourceAll) {
            if (res.getPid() == pid.intValue())
                reslist.add(res);
        }
        return reslist;
    }

    /**
     * 方法描述:保存资源信息（新增加 or 更新）
     *
     * @param resource
     * @Author guanghua.deng
     * @Date 2018/8/23 14:06
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "保存or更新资源信息")
    @RequestMapping(value = "saveResource.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveResource(@RequestBody SystemResource resource) {
        ServiceResult<Integer> serviceResult = null;
        if (resource != null && resource.getId() != 0) {
            //编辑
            serviceResult = systemResourceService.updateSystemResource(resource);
        } else {
            //新增
            resource.setStatus(1);
            serviceResult = systemResourceService.saveSystemResource(resource);
        }
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

    /**
     * 方法描述:根据资源ID删除对应资源
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/23 14:12
     */
    @SystemControllerLog(module = "系统-资源管理", businessDesc = "根据资源ID删除对应资源")
    @RequestMapping(value = "delResource.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse delResource(@RequestParam Integer id) {
        if (id == null) {
            return AjaxResponse.failure("请选择要删除的资源");
        }
        ServiceResult<Boolean> serviceResult = systemResourceService.del(id);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

}
