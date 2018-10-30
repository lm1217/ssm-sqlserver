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
import com.iyeed.core.entity.user.MdUser;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 功能描述:用户登录controller
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
@Controller
@RequestMapping(value = "api/system")
public class SystemUserLoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemUserLoginController.class);

    @SystemControllerLog(module = "系统-用户管理", businessDesc = "系统用户登录")
    @RequestMapping(value = "login.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse login(@RequestParam String username, @RequestParam String password) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, Md5.getMd5String(password).toCharArray());

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
            String[] storeNoArr = null;
            String[] userNoArr = null;
            if (systemUser.getUserType() == 1) {
                storeNo = systemUser.getUserNo();
            } else if (systemUser.getUserType() == 2) {
                ServiceResult<List<MdUser>> userListResult = mdUserService.getUserListByUserNo(systemUser.getUserNo());
                if (!userListResult.getSuccess()) {
                    return AjaxResponse.failure(RespCode.FAILED, userListResult.getMessage());
                }
                List<MdUser> userList = userListResult.getResult();
                userNoArr = new String[userList.size()];
                int i = 0;
                for (MdUser user : userList) {
                    userNoArr[i] = user.getUserNo();
                    i++;
                }
            }

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
