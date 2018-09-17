package com.iyeed.api.shiro;

import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.api.util.WebUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceCheckFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) {
        Subject subject = getSubject(request, response);
        String url = getPathWithinApplication(request);
        if (url != null && url.endsWith("/")) {
            // 截去url最后一个/
            url = url.substring(0, url.length() - 1);
        }
        return subject.isAuthenticated() && subject.isPermitted(url);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");

        Subject subject = getSubject(request, response);

        if (!subject.isAuthenticated()) {
            // 如果没有登录，去登录页
            AjaxResponse ajaxResponse = AjaxResponse.failure(RespCode.NOT_LOGIN);
            WebUtil.responseJson(httpServletResponse, ajaxResponse);
        } else {
            // 如果已登录，提示没有权限
            AjaxResponse ajaxResponse = AjaxResponse.failure(RespCode.NOT_AUTH);
            WebUtil.responseJson(httpServletResponse, ajaxResponse);
        }
        return false;
    }

}
