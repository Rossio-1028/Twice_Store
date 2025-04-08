package com.shanzhu.st.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件 业务服务层接口
 *
 *
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @param fileName      文件名
     * @return 结果
     * @throws IOException io异常
     */
    boolean uploadFile(MultipartFile multipartFile, String fileName) throws IOException;
}
