package com.graph.server.service.impl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Create by xiaow on 2018/11/13
 *
 * @Version 1.0.0
 */
@Service("remoteFileUploadServiceImpl")
public class RemoteFileUploadServiceImpl extends AbstractFileUploadServiceImpl {

    @Override
    public boolean fileSave(MultipartFile file){
       // TODO 文件保存云端逻辑
        return false;
    }
}
