package com.graph.framework.log;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by rocky on 2018/5/23.
 */
@Aspect
@Order(10)
@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(com.graph.framework.log.WebLogAspect.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    private WebLogBean logBean = null;

    @Pointcut("@annotation(com.graph.framework.log.WebLog)")
    public void start() {
    }

    @Before("start()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        logBean = new WebLogBean();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String classMethod = String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logBean.setClassMethod(classMethod);
        logBean.setHttpMethod(request.getMethod());
        logBean.setRequestUri(request.getRequestURL().toString());
        //http header信息
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> header = Maps.newConcurrentMap();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            header.put(name, value);
        }
        logBean.setHttpHeader(JSONObject.toJSONString(header));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile) {
                args[i] = o.toString();
            }
        }
        String requestParam = JSONObject.toJSONString(args);
        logBean.setRequestParams(requestParam.length() > 2000 ? JSONObject.toJSONString("请求参数数据超过2000个字符不予显示") : requestParam);

        //自定义名称
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        WebLog webLog = method.getAnnotation(WebLog.class);
        logBean.setTitle(webLog.value());
    }

    @Around("start()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Exception e) {
//            e.printStackTrace();
            logBean.setException(e.getMessage());
            if (logger.isErrorEnabled()) {
                logger.error(logBean.toString());
            }
            throw e;
        }
    }

    @AfterReturning(returning = "ret", pointcut = "start()")
    public void doAfterReturning(Object ret) {
        String retString = JSONObject.toJSONString(ret);
        logBean.setResponse(retString.length() > 2000 ? JSONObject.toJSONString("返回数据超过2000个字符不予显示") : retString);
        logBean.setUseTime(System.currentTimeMillis() - startTime.get());
        if (logger.isInfoEnabled()) {
            logger.info(logBean.toString());
        }
        startTime.remove();
    }

}
