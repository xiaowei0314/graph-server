package com.graph.framework.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class CodeMessage {

    private String code;

    private String message;

    /**
     * 构造函数
     */
    public CodeMessage() {
    }

    /**
     * 构造函数
     * @param code
     * @param message
     */
    public CodeMessage(String code, String message) {
        setCode(code);
        setMessage(message);
    }

    /**
     * getter
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     * setter
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getter
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * setter
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * getter
     * @return
     */
    public String getMessage(String paramValue) {
        return StringUtils.replacePattern(this.message, "\\$\\{.*\\}",
                paramValue);
    }

    /**
     *  getter
     * @param paramValues
     * @return
     */
    public String getMessage(Map<String, String> paramValues) {
        String msg = this.message;
        for (Map.Entry entry : paramValues.entrySet()) {
            msg = StringUtils.replacePattern(msg,
                    "\\$\\{" + (String) entry.getKey() + "\\}",
                    (String) entry.getValue());
        }
        return msg;
    }

    /**
     * 重写toString()方法
     * @return
     */
    @Override
    public String toString() {
        return "code = " + this.code + ",message = " + this.message;
    }

}
