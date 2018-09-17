package com.iyeed.api.util;

import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.StringUtil;
import com.iyeed.core.utils.PaginationUtil;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 2017/11/30.
 */
public class WebRequestUtil {
    /**
     * 查询条件以  q_ 开头的参数取到
     * @param request
     * @return
     */
    public static Map<String, String> handlerQueryMap(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (params == null)
            return null;
        Map<String, String> queryMap = new HashMap<String, String>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().length <= 0)
                continue;
            //不考虑复选框多值情况，通常查询条件使用复选框为单值情况。
            if (entry.getKey().startsWith("q_") && entry.getValue().length == 1) {
                queryMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return queryMap;
    }

    /**
     * 查询条件以  q_ 开头的参数
     * @param request
     * @return
     */
    public static Map<String, Object> handlerQueryMapReturnObject(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (params == null)
            return null;
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().length <= 0)
                continue;
            //不考虑复选框多值情况，通常查询条件使用复选框为单值情况。
            if (entry.getKey().startsWith("q_") && entry.getValue().length == 1) {
                queryMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return queryMap;
    }

    public static String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String url = "http://" + request.getServerName();
        if (null2Int(Integer.valueOf(request.getServerPort())) != 80) {
            url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
        } else {
            url = url + contextPath;
        }
        return url;
    }

    public static int null2Int(Object s) {
        int v = 0;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**
     * requeset所有参数
     * @param request
     * @return
     */
    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (params == null)
            return null;
        Map<String, String> queryMap = new HashMap<String, String>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().length <= 0)
                continue;
            queryMap.put(entry.getKey(), entry.getValue()[0]);
        }
        return queryMap;
    }

    /**
     * 查询条件拼接  不以q_开头的
     * @param request
     * @return
     */
    public static Map<String, Object> handlerQueryMapNoQ(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (params == null)
            return null;
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().length <= 0)
                continue;
            //不考虑复选框多值情况，通常查询条件使用复选框为单值情况。
            if (entry.getValue().length == 1) {
                queryMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return queryMap;
    }

    /**
     * 分页信息相关 TODO 需要分前台和后台的page_size
     * @param request
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static PagerInfo handlerPagerInfo(HttpServletRequest request, Object map) {
        try {
            int pageSize = "".equals(StringUtil.nullSafeString(request.getParameter("rows"))) ? ConstantsEJS.DEFAULT_PAGE_SIZE
                    : Integer.parseInt(request.getParameter("rows"));
            int pageIndex = "".equals(StringUtil.nullSafeString(request.getParameter("page"))) ? 1
                    : Integer.parseInt(request.getParameter("page"));

            if (map instanceof ModelAndView) {
                ((ModelAndView) map).addObject("pageSize", pageSize);
                ((ModelAndView) map).addObject("pageIndex", pageIndex);
            } else if (map instanceof Model) {
                ((Model) map).addAttribute("pageSize", pageSize);
                ((Model) map).addAttribute("pageIndex", pageIndex);
            } else if (map instanceof ModelMap) {
                ((ModelMap) map).addAttribute("pageSize", pageSize);
                ((ModelMap) map).addAttribute("pageIndex", pageIndex);
            } else {
                ((Map<String, String>) map).put("pageSize", pageSize + "");
                ((Map<String, String>) map).put("pageIndex", pageIndex + "");
            }
            return new PagerInfo(pageSize, pageIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页信息相关 TODO 需要分前台和后台的pageSize，假如需要额外制定分页，那传分页的条数
     * @param request
     * @param map
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public static PagerInfo handlerPagerInfo(HttpServletRequest request, Object map, int pageSize) {
        try {
            if (pageSize == 0) {
                pageSize = "".equals(StringUtil.nullSafeString(request.getParameter("rows"))) ? ConstantsEJS.DEFAULT_PAGE_SIZE
                        : Integer.parseInt(request.getParameter("rows"));
            }
            int pageIndex = "".equals(StringUtil.nullSafeString(request.getParameter("page"))) ? 1
                    : Integer.parseInt(request.getParameter("page"));

            if (map instanceof ModelAndView) {
                ((ModelAndView) map).addObject("pageSize", pageSize);
                ((ModelAndView) map).addObject("pageIndex", pageIndex);
            } else if (map instanceof Model) {
                ((Model) map).addAttribute("pageSize", pageSize);
                ((Model) map).addAttribute("pageIndex", pageIndex);
            } else if (map instanceof ModelMap) {
                ((ModelMap) map).addAttribute("pageSize", pageSize);
                ((ModelMap) map).addAttribute("pageIndex", pageIndex);
            } else {
                ((Map<String, String>) map).put("pageSize", pageSize + "");
                ((Map<String, String>) map).put("pageIndex", pageIndex + "");
            }
            return new PagerInfo(pageSize, pageIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        // 如果客户端经过多级反向代理，则X-Forwarded-For的值并不止一个，而是一串IP值，
        // 取X-Forwarded-For中第一个非unknown的有效IP字符串即为用户真实IP
        String ipResult = getIp(ip);
        if (ipResult != null) {
            return ipResult;
        }

        ip = request.getHeader("Proxy-Client-IP");
        ipResult = getIp(ip);
        if (ipResult != null) {
            return ipResult;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        ipResult = getIp(ip);
        if (ipResult != null) {
            return ipResult;
        }
        ip = request.getRemoteAddr();
        ipResult = getIp(ip);
        if (ipResult != null) {
            return ipResult;
        }
        return "";
    }

    private static String getIp(String ips) {
        if (StringUtil.isEmpty(ips, true)) {
            return null;
        }
        String[] tokens = ips.split(",");
        for (String s : tokens) {
            if (StringUtil.isIp(s.trim())) {
                return s.trim();
            }
        }
        return null;
    }

    /**
     * 获取前台分页的对象
     * @param request
     * @return
     */
    public static PaginationUtil handlerPaginationUtil(HttpServletRequest request) {
        String numStr = request.getParameter("num");
        if (numStr == null) {
            numStr = "1";
        }
        int num = "".equals(StringUtil.nullSafeString(numStr)) ? 1 : Integer.parseInt(numStr);
        int pageSize = ConstantsEJS.DEFAULT_PAGE_SIZE;
        if (request.getAttribute("pageSize") != null) {
            pageSize = "".equals(StringUtil.nullSafeString(request.getAttribute("pageSize")
                    .toString())) ? 1 : Integer.parseInt(request.getAttribute("pageSize").toString());
        }
        return new PaginationUtil(num, pageSize);
    }

}
