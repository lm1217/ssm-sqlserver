package com.iyeed.api.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/10 15:04
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(ShiroSessionManager.class);
    private static final String AUTHORIZATION = "token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Iyeed_tct";


    public ShiroSessionManager(){
        super();
    }

    /**
     * 重写获取sessionId的方法调用当前Manager的获取方法
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
//        System.out.println("id：" + id);
        if(StringUtils.isEmpty(id)){
            //如果没有携带id参数则按照父类的方式在cookie进行获取
//            System.out.println("super：" + super.getSessionId(request, response));
            return super.getSessionId(request, response);
        }else{
            //如果请求头中有 token 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }

    @Override
    public void validateSessions() {
        super.validateSessions();
    }

    protected Session retrieveSession(SessionKey sessionKey) {
        try{
            return super.retrieveSession(sessionKey);
        }catch (UnknownSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public Date getStartTimestamp(SessionKey key) {
        try{
            return super.getStartTimestamp(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public Date getLastAccessTime(SessionKey key) {
        try{
            return super.getLastAccessTime(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public long getTimeout(SessionKey key){
        try{
            return super.getTimeout(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return 0;
        }
    }

    public void setTimeout(SessionKey key, long maxIdleTimeInMillis) {
        try{
            super.setTimeout(key, maxIdleTimeInMillis);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
        }
    }

    public void touch(SessionKey key) {
        try{
            super.touch(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
        }
    }

    public String getHost(SessionKey key) {
        try{
            return super.getHost(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public Collection<Object> getAttributeKeys(SessionKey key) {
        try{
            return super.getAttributeKeys(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
        try{
            return super.getAttribute(sessionKey, attributeKey);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) {
        try{
            super.setAttribute(sessionKey, attributeKey, value);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
        }
    }

    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) {
        try{
            return super.removeAttribute(sessionKey, attributeKey);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
            return null;
        }
    }

    public void stop(SessionKey key) {
        try{
            super.stop(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.info("获取不到SESSION不抛出异常");
        }
    }

    public void checkValid(SessionKey key) {
        try{
            super.checkValid(key);
        }catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            log.error("获取不到SESSION不抛出异常");
        }
    }

    @Override
    protected Session doCreateSession(SessionContext context) {
        try{
            return super.doCreateSession(context);
        }catch (IllegalStateException e) {
            return null;
        }
    }

    @Override
    protected Session newSessionInstance(SessionContext context) {
        Session session = super.newSessionInstance(context);
        session.setTimeout(getGlobalSessionTimeout());
        return session;
    }

    @Override
    public Session start(SessionContext context) {
        try{
            return super.start(context);
        }catch (NullPointerException e) {
            SimpleSession session = new SimpleSession();
            session.setId(0);
            return session;
        }
    }
}
