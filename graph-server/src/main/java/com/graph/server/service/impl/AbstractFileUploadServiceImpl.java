package com.graph.server.service.impl;

import com.graph.server.service.IFileUploadService;

import java.io.File;
import java.util.List;

/**
 * @ClassName 文件上传业务处理抽象类
 * @Description TODO
 * @Author xiaow
 * @Date 2018/11/12 16:58
 * @Version 1.0.0
 */
public abstract class AbstractFileUploadServiceImpl implements IFileUploadService {

    @Override
    public List<Object> fileAnalyzeToList(File file){
        // TODO 读取文件内容逻辑
        return null;
    }
}
