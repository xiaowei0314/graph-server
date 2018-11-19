package com.graph.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class BaseBean {
    /**
     *  重写equals()方法
     * @param obj 对象
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     *  重写hashCode()方法
     * @return int
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * 可以详细显示对象的各个属性值
     * @return String
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
