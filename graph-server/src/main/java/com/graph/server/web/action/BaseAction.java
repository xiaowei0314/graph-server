package com.graph.server.web.action;

import com.graph.server.bean.AuthContext;
import com.graph.server.context.AuthContextThreadLocal;
import com.graph.framework.constant.CoreConstants;
import com.graph.framework.context.RequestThreadLocal;
import com.graph.framework.context.ResponseThreadLocal;
import com.graph.framework.exception.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * action基类
 *
 */
public abstract class BaseAction {
	
    /**
     * 获取request
     * @return
     */
	protected HttpServletRequest getRequest() throws BusinessException {
		
		HttpServletRequest req = RequestThreadLocal.getRequest();
		if(null == req){
			throw new BusinessException(CoreConstants.REQUEST_PROGRAM_ERROR_CODE, "获取request失败！", false);
		}
		return req;
	}
	
    /**
     * 获取response
     * @return
     */
	protected HttpServletResponse getResponse() throws BusinessException {
		
		HttpServletResponse res = ResponseThreadLocal.getResponse();
		if(null == res){
			throw new BusinessException(CoreConstants.REQUEST_PROGRAM_ERROR_CODE, "获取response失败！", false);
		}
		return res;
	}

	/**
	 * 获取auth context
	 * @return
	 */
	protected AuthContext getAuthContext() throws BusinessException {

		AuthContext context = AuthContextThreadLocal.getAuthContext();
		if(null == context){
			throw new BusinessException(CoreConstants.REQUEST_PROGRAM_ERROR_CODE, "获取authContext失败！", false);
		}
		return context;
	}

}
