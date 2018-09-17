package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.api.util.WebUtil;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.vo.UserListForm;
import com.iyeed.core.exception.BusinessException;
import com.iyeed.core.utils.Md5;
import com.iyeed.service.system.ISystemRoleService;
import com.iyeed.service.system.ISystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:用户管理Controller
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
@Controller
@RequestMapping(value = "api/system/user")
@Scope("prototype")
public class SystemUserController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private ISystemRoleService systemRoleService;

    /**
     * 获取系统用户列表
     * @param
     * @return AjaxResponse
     */
    @RequestMapping(value = "getSystemUserList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getSystemUserList(@RequestBody UserListForm form) {
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q_username", form.getUsername());
        queryMap.put("q_userNo", form.getUserNo());
        if (!isNull(form.getStatus())) {
            queryMap.put("status", String.valueOf(form.getStatus()));
        }
        ServiceResult<List<SystemUser>> serviceResult = systemUserService.page(queryMap, pagerInfo);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());
        dataMap.put("form", form);
        dataMap.put("systemUserList",serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }


    @RequestMapping(value = "editSystemUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse editSystemUser(@RequestParam Integer id) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("status", "1");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("roleList", systemRoleService.page(queryMap, null).getResult());
        dataMap.put("systemUser", systemUserService.getSystemUserById(id).getResult());

        return AjaxResponse.success(dataMap);
    }

    /**
     * 冻结管理员账号
     * @param id
     */
    @RequestMapping(value = "freezeSystemUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse freezeSystemUser(@RequestParam Integer id) {
        ServiceResult<SystemUser> serviceResult = systemUserService.getSystemUserById(id);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        SystemUser systemUser = serviceResult.getResult();
        systemUser.setStatus(2);

        systemUserService.updateSystemUser(systemUser);
        return AjaxResponse.success("账号[" + systemUser.getUsername() + "]已被冻结");
    }

    @RequestMapping(value = "delSystemUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse delSystemUser(@RequestParam Integer id) {
        ServiceResult<SystemUser> serviceResult = systemUserService.getSystemUserById(id);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        SystemUser systemUser = serviceResult.getResult();
        systemUser.setStatus(3);

        systemUserService.updateSystemUser(systemUser);
        return AjaxResponse.success("账号[" + systemUser.getUsername() + "]已删除");
    }

    /**
     * 保存
     * @param systemUser
     */
    @RequestMapping(value = "saveSystemUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveSystemUser(@RequestBody SystemUser systemUser) {
        ServiceResult<Integer> serviceResult;
        if (systemUser.getRoleId() == 0 || systemUser.getStatus() == 0) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (systemUser.getId() == null || systemUser.getId().intValue() == 0) {
            systemUser.setCreateTime(new Date());
            //初始密码
            systemUser.setPassword(Md5.getMd5String("123456"));
            serviceResult = systemUserService.saveSystemUser(systemUser);
        } else {
            if (null != systemUser.getPassword() && !"".equals(systemUser.getPassword()))
                systemUser.setPassword(Md5.getMd5String(systemUser.getPassword()));
            serviceResult = systemUserService.updateSystemUser(systemUser);
        }
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }


//    /**
//     * 修改密码
//     * @param config
//     * @param request
//     * @param dataMap
//     * @return
//     */
//    @RequestMapping(value = "editpwd/update", method = { RequestMethod.POST })
//    @ResponseBody
//    public AjaxResponse configUpdate(HttpServletRequest request,
//                                         Map<String, Object> dataMap) {
//        String oldPassword = request.getParameter("oldPassword");
//        String newPassword = request.getParameter("newPassword");
//        String newPasswordCfm = request.getParameter("newPasswordCfm");
//        if (StringUtil.isEmpty(oldPassword, true) || StringUtil.isEmpty(newPassword, true)
//            || StringUtil.isEmpty(newPasswordCfm, true)) {
//            return new HttpJsonResult<Boolean>("密码不能为空，请重新输入！");
//        }
//        if (!newPassword.equals(newPasswordCfm)) {
//            return new HttpJsonResult<Boolean>("新密码与确认密码不一致，请重新输入！");
//        }
//        if (oldPassword.equals(newPassword)) {
//            return new HttpJsonResult<Boolean>("新密码与旧密码不能相同，请重新输入！");
//        }
//
//        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
//
//        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
//
//        ServiceResult<SystemAdmin> systemAdminRlt = systemAdminService
//            .getSystemAdminById(adminUser.getId());
//        if (!systemAdminRlt.getSuccess()) {
//            return new HttpJsonResult<Boolean>(systemAdminRlt.getMessage());
//        }
//        SystemAdmin adminDb = systemAdminRlt.getResult();
//
//        String oldPasswordMd5 = Md5.getMd5String(oldPassword);
//        if (!oldPasswordMd5.equals(adminDb.getPassword())) {
//            return new HttpJsonResult<Boolean>("旧密码输入错误，请重新输入！");
//        }
//
//        SystemAdmin adminNew = new SystemAdmin();
//        adminNew.setId(adminUser.getId());
//        adminNew.setPassword(Md5.getMd5String(newPassword));
//
//        ServiceResult<Integer> serviceResult = systemAdminService.updateSystemAdmin(adminNew);
//        if (!serviceResult.getSuccess()) {
//            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
//                throw new RuntimeException(serviceResult.getMessage());
//            } else {
//                jsonResult.setMessage(serviceResult.getMessage());
//                return jsonResult;
//            }
//        }
//        jsonResult.setData(true);
//        return jsonResult;
//    }
}
