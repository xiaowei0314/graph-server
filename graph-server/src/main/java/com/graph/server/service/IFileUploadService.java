package com.graph.server.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @ClassName 文件处理接口
 * @Description 用户处理文件上传的业务逻辑
 * @Author xiaow
 * @Date 2018/11/12 16:36
 * @Version 1.0.0
 */
public interface IFileUploadService {

    /**
     * 文件存储
     * @param file
     * @return
     */
    boolean fileSave(MultipartFile file);

    /**
     * 分析文件内容，转换List集合
     * @param file
     * @return
     */
    List<Object> fileAnalyzeToList(File file);

}
