package com.aurora.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 删除文件
     * @param url
     * @return
     */
    Boolean delete(String url);
}
