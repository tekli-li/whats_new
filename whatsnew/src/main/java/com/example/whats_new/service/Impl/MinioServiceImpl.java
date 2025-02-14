package com.example.whats_new.service.Impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.whats_new.service.MinioService;
import com.example.whats_new.utils.MinioUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioUtils minioUtils;

    @Override
    public Boolean bucketExists(String bucketName) {
        return minioUtils.bucketExists(bucketName);
    }

    @Override
    public void makeBucket(String bucketName) {
        minioUtils.makeBucket(bucketName);
    }

    @SneakyThrows
    @Override
    public List<Object> listObjects(String bucketName) {
        return minioUtils.listObjects(bucketName);
    }

    @Override
    public Boolean upload(MultipartFile multipartFile) {
        return minioUtils.putObject(multipartFile);
    }

    @Override
    public void upload(MultipartFile[] multipartFile) {
        minioUtils.putObject(multipartFile);
    }

    @Override
    public Boolean delFile(String bucketName,String fileName) {
        return minioUtils.removeObject(bucketName, fileName);
    }

    @Override
    public String getFileUrl(String fileName) {
        return minioUtils.getObjectUrl(fileName);
    }
}