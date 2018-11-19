package com.graph.framework.exception;

import com.graph.framework.bean.ResponseResult;
import com.graph.framework.constant.CoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseResult handleBusinessException(BusinessException e) {
        if (e.isLogged()) {
            logger.error("业务异常:{}", e.getErrorDetailMsg());
        }
        return new ResponseResult(e.getErrorCode(), e.getMessage(), "");
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        logger.error("程序异常:", e);
        return new ResponseResult(CoreConstants.REQUEST_PROGRAM_ERROR_CODE, "系统异常", "");
    }
}