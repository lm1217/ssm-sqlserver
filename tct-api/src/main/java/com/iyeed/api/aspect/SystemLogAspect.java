package com.iyeed.api.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.entity.system.SystemLog;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.utils.CommonUtil;
import com.iyeed.service.system.ISystemLogService;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class SystemLogAspect {
    @Resource
    private ISystemLogService systemLogService;

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    /**
     * 设置controller切入点
     */
    @Pointcut("@annotation(com.iyeed.core.annotation.SystemControllerLog)")
    public void controllerAspect() { }

    /**
     * 设置service切入点
     */
    @Pointcut("@annotation(com.iyeed.core.annotation.SystemServiceLog)")
    public void serviceAspect() { }

    /**
     * 前置通知 用于拦截Controller层操作-目标方法执行前执行
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
//        System.out.println("==========前置通知开始==========");
    }

    /**
     * 环绕通知 在执行上面其他操作的同时也执行这个方法
     * @param pjp
     */
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("==========环绕通知开始==========");
        Gson gson = new GsonBuilder().serializeNulls().create();
        Object object = null;
        // 系统日志实体对象
        SystemLog log = new SystemLog();
        // 获取登录用户账户
        Subject subject = SecurityUtils.getSubject();
        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        if (systemUser != null) {
            log.setOpUserNo(systemUser.getUserNo());
        }
        // 获取客户端ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = CommonUtil.getIpAddr(request);
        log.setIp(ip);
        // 获取用户请求方法的参数并序列化为JSON格式字符串
        String requestParams = "";
        // 获取响应结果参数并序列化为JSON格式字符串
        String responseParams = "";
        // 拦截的目标对象
        Object target = pjp.getTarget();
        // 拦截目标对象名称
        String targetName = target.getClass().getName();
        log.setTargetName(targetName);
        // 拦截的方法名称
        String methodName = pjp.getSignature().getName();
        log.setMethodName(methodName);

        Class<?> clazz = Class.forName(targetName);
        String clazzName = clazz.getName();

        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 获取被切参数名称及参数值
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);

        if (nameAndArgs != null && nameAndArgs.size() > 0) {
            requestParams = gson.toJson(nameAndArgs);
        }
        log.setRequestParams(requestParams);
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error("请求响应拦截异常{}", e.getMessage());
        } catch (SecurityException e) {
            e.printStackTrace();
            logger.error("请求响应拦截异常{}", e.getMessage());
        }
        if (null != method) {
            // 判断是否包含自定义的注解，说明一下这里的SystemControllerLog就是我自己自定义的注解
            if (method.isAnnotationPresent(SystemControllerLog.class)) {
                SystemControllerLog systemControllerLog = method.getAnnotation(SystemControllerLog.class);
                log.setModule(systemControllerLog.module());
                log.setBusinessDesc(systemControllerLog.businessDesc());
                // 方法执行前获取时间,用来计算模块执行时间
                long start = System.currentTimeMillis();
                object = pjp.proceed();
                long end = System.currentTimeMillis();
                // 将计算好的时间保存在实体中
                log.setExeTime(end - start);
                responseParams = gson.toJson(object);
                log.setResponseParams(responseParams);
                AjaxResponse ajaxResponse = (AjaxResponse) object;
                if (ajaxResponse.getCode() == 0) {
                    log.setCommit("执行成功！");
                } else {
                    log.setCommit("执行失败");
                }
                systemLogService.saveSystemLog(log);
            } else { // 没有包含注解
                object = pjp.proceed();
            }
        } else { // 不需要拦截直接执行
            object = pjp.proceed();
        }
        return object;
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut="serviceAspect()", throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        System.out.println("==========异常通知开始==========");
    }

    /**
     * 最终通知
     */
    @After("controllerAspect()")
    public void doAfter() {
//        System.out.println("==========最终通知开始==========");
    }

    /**
     * 通过反射机制 获取被切参数名以及参数值
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     * @throws NotFoundException
     */
    private Map getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map map = new HashMap();
        ClassPool pool = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
//            exception
        }
//        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }
}
