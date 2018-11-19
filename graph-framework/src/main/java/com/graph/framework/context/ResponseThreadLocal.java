package com.graph.framework.context;

import javax.servlet.http.HttpServletResponse;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class ResponseThreadLocal {

    public static ThreadLocal<HttpServletResponse> threadLocal = new ThreadLocal<HttpServletResponse>();

    /**
     * 删除响应
     */
    public static void removeResponse() {
        threadLocal.remove();
    }

    /**
     * 获取响应
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return threadLocal.get();
    }

    /**
     * 设置响应
     * @param res 请求
     */
    public static void setResponse(HttpServletResponse res) {
        threadLocal.set(res);
    }

}
