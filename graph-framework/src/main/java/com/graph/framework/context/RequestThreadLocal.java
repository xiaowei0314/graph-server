package com.graph.framework.context;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class RequestThreadLocal {

    public static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<HttpServletRequest>();

    /**
     * 删除请求
     */
    public static void removeRequest() {
        threadLocal.remove();
    }

    /**
     * 获取请求
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return threadLocal.get();
    }

    /**
     * 设置请求
     * @param req 请求
     */
    public static void setRequest(HttpServletRequest req) {
        threadLocal.set(req);
    }

}
