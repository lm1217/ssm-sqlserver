package com.iyeed.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huang on 2017/11/29.
 */
public class CacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        chain.doFilter(request, response);
        HttpServletResponse res=(HttpServletResponse) response;
        res.setHeader("expries", "-1");
        res.setHeader("pragma", "no-cache");
        res.setHeader("cache-control", "no-cache");
    }

    @Override
    public void destroy() {

    }

}
