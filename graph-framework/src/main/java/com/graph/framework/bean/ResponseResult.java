package com.graph.framework.bean;


import com.graph.framework.constant.CoreConstants;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class ResponseResult<T> extends BaseBean {

    private Integer code;

    private String message;

    private T data;

    /**
     * 构造函数
     */
    public ResponseResult() {

    }

    /**
     * 构造函数
     * @param data
     */
    public ResponseResult(T data) {
        this(CoreConstants.REQUEST_SUCCESS_CODE, "成功", data);
    }

    /**
     *构造函数
     * @param code
     * @param message
     */
    public ResponseResult(Integer code, String message) {
        this(code, message, null);
    }

    /**
     * 构造函数
     * @param code
     * @param message
     * @param data
     */
    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(data);
    }

    /**
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(Integer code, String message, T data) {
        return new ResponseResult<T>(code, message, data);
    }

    /**
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        return new ResponseResult<T>(code, message);
    }

    /**
     * getter
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     * setter
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getter
     * @return
     */
    public Integer getCode() {
        return code;
    }

    /**
     * setter
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * getter
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public boolean success() {
        return this.code == CoreConstants.REQUEST_SUCCESS_CODE;
    }
}