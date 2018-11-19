package com.graph.server.bean;

import com.graph.framework.bean.BaseBean;
import lombok.Data;

@Data
public class AuthContext extends BaseBean {

    private Long userId;
    private String tokenKey;
}
