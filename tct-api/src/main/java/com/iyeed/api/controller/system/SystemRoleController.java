package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.system.SystemRole;
import com.iyeed.core.entity.system.vo.RoleListForm;
import com.iyeed.service.system.ISystemRoleResourceService;
import com.iyeed.service.system.ISystemRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:角色管理controller
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/system/role")
public class SystemRoleController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemRoleController.class);

    /**
     * 验证角色编码不重复
     * @param roleCode
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "验证角色编码不重复")
    @RequestMapping(value = "validateRole.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse validateRole(@RequestParam String roleCode) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("roleCode", roleCode);
        ServiceResult<List<SystemRole>> serviceResult = systemRoleService.page(queryMap, null);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        boolean isValid  = serviceResult.getResult().size() == 0;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("isValid", isValid);
        return AjaxResponse.success(dataMap);
    }



    /**
     * 获取角色列表
     * @return
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "按条件获取角色列表")
    @RequestMapping(value = "roleList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse roleList(@RequestBody RoleListForm form) {
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q_roleName", form.getRoleName());
        queryMap.put("roleCode", form.getRoleCode());
        if (!isNull(form.getStatus())) {
            queryMap.put("status", String.valueOf(form.getStatus()));
        }

        ServiceResult<List<SystemRole>> serviceResult = systemRoleService.page(queryMap, pagerInfo);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());
        dataMap.put("form", form);
        dataMap.put("roleList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 编辑角色(根据角色ID获取角色信息)
     * @param roleId
     * @return
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "编辑角色-根据角色ID获取角色信息")
    @RequestMapping(value = "editRole.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse editRole(@RequestParam(value = "id") Integer roleId) {
        ServiceResult<SystemRole> serviceResult = systemRoleService.getSystemRoleById(roleId);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("role", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 保存角色信息(新增 or 更新)
     * @param role
     * @return
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "保存or更新角色信息")
    @RequestMapping(value = "saveRole.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveRole(@RequestBody SystemRole role) {
        role.setStatus(1);//初始状态 默认为正常
        ServiceResult<Integer> serviceResult = null;
        if (role.getId() != null && role.getId() != 0) {
            //编辑
            serviceResult = systemRoleService.updateSystemRole(role);
        } else {
            //新增
            serviceResult = systemRoleService.saveSystemRole(role);
        }
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

    /**
     * 保存角色资源
     * @param roleId
     * @param resourceIds
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "保存角色资源信息")
    @RequestMapping(value = "saveRoleResource.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveRoleResource(@RequestParam String roleId, @RequestParam String resourceIds) {

        String[] resourceArr = resourceIds.split(",");
        ServiceResult<Boolean> serviceResult = systemRoleResourceService.save(roleId, resourceArr);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

    /**
     * 删除角色信息
     * @param id
     */
    @SystemControllerLog(module = "系统-角色管理", businessDesc = "根据角色ID删除角色")
    @RequestMapping(value = "delRole.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse delRole(@RequestParam Integer id) {
        ServiceResult<Boolean> serviceResult = systemRoleService.del(id);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }
}
