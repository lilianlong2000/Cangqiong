package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    AliOssUtil ossUtil;
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result<String> upload(MultipartFile file) {
        try {
        //获取文件上传的全称
        String filename = file.getOriginalFilename();
        //获取文件格式
        String substring = filename.substring(filename.lastIndexOf("."));
        //uuid获取唯一上传名字
        String objectName = UUID.randomUUID().toString() + substring;
            //上传成功返回上传地址
            String url = ossUtil.upload(file.getBytes(), objectName);
            return Result.success(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
