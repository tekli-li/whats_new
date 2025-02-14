package com.example.whats_new.controller;

import com.example.whats_new.pojo.Result;
import com.example.whats_new.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/pics")
@CrossOrigin
@Slf4j
public class PicsController {
    @Autowired
    private MinioService minioService;
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            // 根据业务设计，设置存储路径：按天创建目录
            String objectName = new SimpleDateFormat("yyyy-MM-dd/").format(new Date())
                    + UUID.randomUUID().toString()
                    + fileName.substring(fileName.lastIndexOf("."));

            minioService.upload(file);
            log.info("文件格式为:{}", file.getContentType());
            log.info("文件原名称为:{}", fileName);
            log.info("文件对象路径为:{}", minioService.getFileUrl(fileName));
            return Result.success(minioService.getFileUrl(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }
}
