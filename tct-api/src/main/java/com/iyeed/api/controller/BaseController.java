package com.iyeed.api.controller;

import com.iyeed.core.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 控制层基类， 所有controller类都需要继承此类        
 */
public class BaseController {
    protected static Logger log = LoggerFactory.getLogger(BaseController.class);

    protected boolean isNull(Object... argumets) {
        for (Object obj : argumets) {
            if (obj == null || "".equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //date,datetime
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(null);
                    return;
                }
                try {
                    if (value.length() == 10) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    } else if (value.length() == 8) {
                        setValue(new SimpleDateFormat("HH:mm:ss").parse(value));
                    } else if (value.length() == 16) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(value));
                    } else {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                    }

                } catch (ParseException e) {
                    log.error("Can not convert '" + value + "' to java.util.Date", e);
                }
            }

            @Override
            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
            }

        });
        //int
        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Integer.parseInt(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });

        //long
        binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Long.parseLong(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });

        //double
        binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Double.parseDouble(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });
    }

    public ModelAndView redirectHandlerFor301(String sURL) {
        RedirectView rv = new RedirectView(sURL);
        rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        rv.setUrl(sURL);
        ModelAndView mv = new ModelAndView(rv);
        return mv;
    }

    /**
     * 调用此方法说明你的controller方法将要转到显示系统消息页面
     * @param stack
     * @param message
     * @param links
     */
    public void showMessage(Map<String, Object> stack, String message,
                            List<Map<String, String>> links) {
        if (links == null || links.size() == 0) {
            links = new ArrayList<Map<String, String>>();
            Map<String, String> link = new HashMap<String, String>();
            link.put("text", "返回上一页");
            link.put("url", "javascript:history.back()");
            links.add(link);
        }
        stack.put("messageInfo", message);
        stack.put("links", links);
        stack.put("msgType", "message");
    }
}
