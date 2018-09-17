package com.iyeed.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.StringUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangJunhua
 * @version 0.1 2017-03-16 17:42
 */
public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 返回输出json
     */
    public static void responseJson(HttpServletResponse response, AjaxResponse ajaxResponse) {
        String jsonString = JSON.toJSONString(ajaxResponse, SerializerFeature.WriteDateUseDateFormat);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            IOUtils.write(jsonString, outputStream, ConstantsEJS.UTF_8);
        } catch (IOException e) {
            logger.debug("write jsonString failure ", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 生成参数绑定错误的返回码
     */
    public static AjaxResponse bindingErrorResponse(BindingResult... results) {
        return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT, ValidateUtil.getErrorMsg(results));
    }

    /**
     * 先从 x-forwarded-for 中获取，获取不到再从 X-Real-IP 中获取, x-forwarded-for 按","分隔后取第一个ip地址，
     * 比如 11.11.11.11,22.22.22.22 ;返回11.11.11.11
     */
    public static String getIp(HttpServletRequest req) {
        String xff = req.getHeader("x-forwarded-for");
        if (xff == null || xff.isEmpty()) {
            xff = req.getHeader("X-Real-IP");
        }
        if (xff == null || xff.isEmpty()) {
            xff = req.getRemoteAddr();
        }
        if (xff != null && xff.contains(",")) {
            xff = xff.split(",")[0].trim();
            if ((xff == null || xff.isEmpty()) && xff.split(",").length > 1) {
                xff = xff.split(",")[1].trim();
            }
        }
        return xff;
    }

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
}
