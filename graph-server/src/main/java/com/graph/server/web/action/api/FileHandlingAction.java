package com.graph.server.web.action.api;

import com.graph.framework.bean.ResponseResult;
import com.graph.server.constant.FileHandleConstants;
import com.graph.server.service.IFileUploadService;
import com.graph.server.web.action.BaseAction;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 接受并执行文件处理相关的请求
 */

@Api(tags = "文件处理操作",description = "<p style='font-weight:normal;font-size:15px'>" +
        "</br>返回码: " +
        "</br>code：0，message ：操作成功；代表请求成功，如果有返回数据则在data中 " +
        "</br>code：ARC100001，message ：（错误信息）；代表鉴权失败！" +
        "</br>code：-1，message ：（错误信息）；代表请求参数不合法" +
        "</br>code：-100，message ：（错误信息）；代表内部系统错误</p>")
@RestController
@RequestMapping("/fileHandling")
public class FileHandlingAction extends BaseAction {

    @Autowired
    IFileUploadService localFileUploadServiceImpl;

    @PostMapping("/singleFileUpload")
    public ResponseResult uploadHandle(@RequestParam("file") MultipartFile file){
        if(localFileUploadServiceImpl.fileSave(file)){
            return new ResponseResult(0, FileHandleConstants.FILE_UPLOAD_SUCCESS);
        } else {
            return new ResponseResult(-1, FileHandleConstants.FILE_UPLOAD_FAIL);
        }

    }

}
