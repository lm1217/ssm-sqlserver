package com.iyeed.api.controller.system;

import com.iyeed.api.controller.BaseController;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.utils.Md5;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.system.ISystemRoleResourceService;
import com.iyeed.service.system.ISystemRoleService;
import com.iyeed.service.system.ISystemUserService;
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
    @Resource
    private IMdStoreService mdStoreService;
    @Resource
    private IMdUserService mdUserService;
    @Resource
    private ISystemRoleResourceService systemRoleResourceService;

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
            ServiceResult<MdUser> userResult = mdUserService.getMdUserByUserNo(systemUser.getUserNo());
            if (!userResult.getSuccess()) {
                return AjaxResponse.failure(RespCode.FAILED, userResult.getMessage());
            }
            MdUser user = userResult.getResult();
            ServiceResult<MdStore> storeResult = mdStoreService.getMdStoreByStoreNo(user.getStoreNo());
            if (!storeResult.getSuccess()) {
                return AjaxResponse.failure(RespCode.FAILED, storeResult.getMessage());
            }
            MdStore store = storeResult.getResult();
            dataMap.put("id", systemUser.getId());
            dataMap.put("userNo", systemUser.getUserNo());
            dataMap.put("username", systemUser.getUsername());
            dataMap.put("roleId", systemUser.getRoleId());
            dataMap.put("storeNo", store.getStoreNo());
            dataMap.put("storeName", store.getStoreName());
            dataMap.put("brandNo", store.getBrandNo());
            dataMap.put("brandName", store.getBrandName());
            dataMap.put("brandLogo", store.getBrandLogo());
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
