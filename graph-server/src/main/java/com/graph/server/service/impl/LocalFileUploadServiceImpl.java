package com.graph.server.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @ClassName 本地存储上传文件业务实现类
 * @Description TODO
 * @Author xiaow
 * @Date 2018/11/12 17:02
 * @Version 1.0.0
 */
@Service("localFileUploadServiceImpl")
public class LocalFileUploadServiceImpl extends AbstractFileUploadServiceImpl{

    @Value("${graph.fileupload.path}")
    private String filePath;

    @Override
    public boolean fileSave(MultipartFile file){
        // TODO 文件保存本地逻辑
        return false;
    }

    public String getFilePath() {
        return filePath;
    }
}
