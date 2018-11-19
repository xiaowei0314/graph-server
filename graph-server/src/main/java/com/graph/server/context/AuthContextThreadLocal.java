package com.graph.server.context;

import com.graph.server.bean.AuthContext;

public class AuthContextThreadLocal {

	public static ThreadLocal<AuthContext> threadLocal = new ThreadLocal<AuthContext>();

	/**
	 * 设置authContext
	 * @param context 上下文
	 */
	public static void setAuthContext(AuthContext context){
		threadLocal.set(context);
	}

	/**
	 * 删除authContext
	 */
	public static void removeAuthContext(){
		threadLocal.remove();
	}

	/**
	 * 获取authContext
	 */
	public static AuthContext getAuthContext(){
		return threadLocal.get();
	}
	
}
