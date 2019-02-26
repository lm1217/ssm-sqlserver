package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.StringUtil;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.system.vo.SystemBrandUserBean;
import com.iyeed.core.entity.system.vo.SystemUserPwdForm;
import com.iyeed.core.entity.system.vo.SystemUserStoreBean;
import com.iyeed.core.entity.system.vo.UserListForm;
import com.iyeed.core.utils.Md5;
import com.iyeed.dao.db.write.xzn.system.SystemUserStoreWriteDao;
import com.iyeed.service.system.ISystemRoleService;
import com.iyeed.service.system.ISystemUserService;
import com.iyeed.service.system.impl.SystemUserStoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/system/user")
@Scope("prototype")
public class SystemUserController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemUserController.class);

    /**
     * 获取系统用户列表
     * @param
     * @return AjaxResponse
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "按条件获取系统用户列表")
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

    /**
     * 获取系统用户列表(根据品牌获取列表)
     * @param
     * @return AjaxResponse
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "根据品牌条件获取系统用户列表")
    @RequestMapping(value = "getSystemBrandUserList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getSystemBrandUserList(@RequestBody UserListForm form) {
        SystemUser systemUser = this.getSystemUser();
        SystemUserBrand systemUserBrand = systemUserService.getSystemUserBrandByUsername(systemUser.getUsername()).getResult();
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        if (!isNull(systemUserBrand)) {
            queryMap.put("q_brandNo", systemUserBrand.getBrandNo());
        } else {
            return AjaxResponse.failure(RespCode.FAILED, "非法操作");
        }

        ServiceResult<List<SystemBrandUserBean>> serviceResult = systemUserService.getBrandUserList(queryMap, pagerInfo);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());
        dataMap.put("form", form);
        dataMap.put("systemBrandUserList",serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 保存(品牌管理新增门店子账号)
     * @param systemUser
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "品牌管理新增系统用户账号")
    @RequestMapping(value = "saveSystemBrandUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveSystemBrandUser(@RequestBody SystemUser systemUser) {
        ServiceResult<Integer> serviceResult;

        if (systemUser.getUserType() != 3 && systemUser.getUserType() != 4) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        systemUser.setRoleId(4);//默认门店帐号权限
        systemUser.setStatus(1);//默认可用状态
        String username = null;
        if (systemUser.getUserType() == 3) {
            username = systemUser.getStoreNo() + "MR";
        } else if (systemUser.getUserType() == 4) {
            username = systemUser.getStoreNo() + "AR";
        }
        systemUser.setUsername(username);
        systemUser.setUserNo(username);
        systemUser.setCreateTime(new Date());
        //初始密码
        systemUser.setPassword(Md5.getMd5String("123456"));
        serviceResult = systemUserService.saveSystemUser(systemUser);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

    @SystemControllerLog(module = "系统-用户管理", businessDesc = "编辑用户-根据系统用户ID获取用户信息")
    @RequestMapping(value = "editSystemUser.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse editSystemUser(@RequestParam Integer id) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("status", "1");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("roleList", systemRoleService.page(queryMap, null).getResult());
        SystemUser systemUser = systemUserService.getSystemUserById(id).getResult();
        if (systemUser.getUserType() > 2) {
            SystemUserStore systemUserStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
            systemUser.setStoreNo(systemUserStore.getStoreNo());
        }
        dataMap.put("systemUser", systemUser);

        return AjaxResponse.success(dataMap);
    }

    /**
     * 冻结管理员账号
     * @param id
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "根据系统用户ID冻结账号")
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

    /**
     * 删除账号
     * @param id
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "根据系统用户ID删除账号")
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
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "保存or更新系统用户账号")
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


    /**
     * 修改密码
     * @param pwdForm
     */
    @SystemControllerLog(module = "系统-用户管理", businessDesc = "修改用户密码")
    @RequestMapping(value = "editSystemUserPwd.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse editSystemUserPwd(@RequestBody SystemUserPwdForm pwdForm) {
        if (StringUtil.isEmpty(pwdForm.getOldPassword(), true) || StringUtil.isEmpty(pwdForm.getNewPassword(), true)) {
            return AjaxResponse.failure("密码不能为空，请重新输入！");
        }
        SystemUser systemUser = this.getSystemUser();

        if (!systemUser.getUsername().equals(pwdForm.getUsername())) {
            return AjaxResponse.failure("非法操作！");
        }

        String oldPasswordMd5 = Md5.getMd5String(pwdForm.getOldPassword());
        if (!oldPasswordMd5.equals(systemUser.getPassword())) {
            return AjaxResponse.failure("旧密码输入错误，请重新输入！");
        }

        SystemUser systemUserNew = new SystemUser();
        systemUserNew.setId(systemUser.getId());
        systemUserNew.setPassword(Md5.getMd5String(pwdForm.getNewPassword()));

        ServiceResult<Integer> serviceResult = systemUserService.updateSystemUser(systemUserNew);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure("密码修改失败，请稍后再试！");
        }
        return AjaxResponse.success("密码修改成功！");
    }

    @SystemControllerLog(module = "系统-用户管理", businessDesc = "获取门店帐号列表(用于门店帐号切换)")
    @RequestMapping(value = "getStoreSystemUserList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStoreSystemUserList() {
        SystemUser systemUser = this.getSystemUser();
        MdStore store = null;
        if (systemUser.getUserType() == 1) {
            store = mdStoreService.getMdStoreByStoreNo(systemUser.getUserNo()).getResult();
        } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
            store = mdStoreService.getMdStoreByStoreNo(userStore.getStoreNo()).getResult();
        } else {
            return AjaxResponse.failure("非门店帐号不允许此操作");
        }

        ServiceResult<List<SystemUserStoreBean>> serviceResult = systemUserStoreService.getSystemUserStoreListByStoreNo(store.getStoreNo());

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        List<SystemUserStoreBean> systemUserStoreBeanList = serviceResult.getResult();

        if (!isNull(systemUserStoreBeanList)) {
            for (SystemUserStoreBean sysUserStore : systemUserStoreBeanList) {
                sysUserStore.setStoreName(store.getStoreName());
            }
            SystemUserStoreBean systemUserStoreBean = new SystemUserStoreBean();
            systemUserStoreBean.setUserNo(store.getStoreNo());
            systemUserStoreBean.setStoreNo(store.getStoreNo());
            systemUserStoreBean.setStoreName(store.getStoreName());
            systemUserStoreBean.setUserType(1);
            systemUserStoreBeanList.add(systemUserStoreBean);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("systemUserStoreList", systemUserStoreBeanList);
        return AjaxResponse.success(dataMap);
    }
}
