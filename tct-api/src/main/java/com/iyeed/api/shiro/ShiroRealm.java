package com.iyeed.api.shiro;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.system.SystemResource;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.service.system.ISystemRoleResourceService;
import com.iyeed.service.system.ISystemUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 功能描述:自定义Realm(shrio权限)
 *
 * @Auther guanghua.deng
 * @Date 2018/8/3 17:39
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private ISystemRoleResourceService systemRoleResourceService;

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  ---> shiro 封装令牌 ----> realm 通过用户名将密码查询返回
     * ----> shiro 自动去比较查询出密码和用户输入密码是否一致 ----> 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        log.info("==================>>Shiro登录认证开始<<==================");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        ServiceResult<List<SystemUser>> userResult = systemUserService.getSystemUserByName(token.getUsername());
        if (!userResult.getSuccess()) {
            log.error(userResult.getMessage());
            return null;
        }
        List<SystemUser> systemUserList = userResult.getResult();
        // 账号不存在
        if (systemUserList == null || systemUserList.size() == 0) {
            log.error("用户不存在，用户名：" + token.getUsername());
            throw new UnknownAccountException();
        }
        // 账号名重复
        if (systemUserList.size() > 1) {
            log.error("用户重复，用户名：" + token.getUsername());
            return null;
        }

        SystemUser systemUser = systemUserList.get(0);

        // 账号未启用
        if (systemUser.getStatus() != 1) {
            log.error("用户账号已停用，用户名：" + token.getUsername());
            throw new DisabledAccountException();
        }
        log.info("==================>>Shiro登录认证结束<<==================");
        // 认证缓存信息
        return new SimpleAuthenticationInfo(systemUser, systemUser.getPassword().toCharArray(), getName());
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("==================>>Shiro权限认证开始<<==================");
        SystemUser systemUser = (SystemUser) principals.getPrimaryPrincipal();

        ServiceResult<List<SystemResource>> resourceResult = systemRoleResourceService.getResourceByRoleId(systemUser.getRoleId());

        if (!resourceResult.getSuccess()) {
            log.error(resourceResult.getMessage());
        }

        List<SystemResource> resourceList = resourceResult.getResult();
        if (resourceList == null || resourceList.size() == 0) {
            log.error("该用户所属角色没有任何权限");
        }

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

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        log.info("==================>>Shiro权限认证结束<<==================");

        return info;
    }
}
