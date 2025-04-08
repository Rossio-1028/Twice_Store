package com.shanzhu.st.service.impl;

import com.shanzhu.st.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    //使用@Value注解从配置文件中读取userFilePath属性的值，并将其注入到userFilePath变量中
    @Value("${userFilePath}")
    private String userFilePath;

    /*
     *发布闲置商品中上传文件的方法
     * multipartFile 要上传的文件，类型为MultipartFile，这是Spring框架用于处理文件上传的类
     * fileName 上传后的文件名
     * @return 如果文件上传成功返回true，失败返回false
     * throw IOException 当文件操作出现异常时抛出该异常
     */
    public boolean uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        //创建一个File对象，表示文件存储的目录
        File fileDir = new File(userFilePath);
        //检查文件存储目录是否存在
        if (!fileDir.exists()) {
            //如果目录不存在，则尝试创建该目录及其所有父目录
            if (!fileDir.mkdirs()) {
                //如果创建目录失败，返回false表示上传失败
                return false;
            }
        }
        //创建一个File对象，表示要上传的文件
        File file = new File(fileDir.getAbsolutePath() + "/" + fileName);
        //检查该文件是否已经存在
        if (file.exists()) {
            //如果文件已经存在，则尝试删除该文件
            if (!file.delete()) {
                //如果删除文件失败，返回false表示上传失败
                return false;
            }
        }
        //尝试创建新文件
        if (file.createNewFile()) {
            //如果文件创建成功，则将上传的文件内容传输到新创建的文件中
            multipartFile.transferTo(file);
            //文件上传成功，返回true
            return true;
        }
        //文件创建失败，返回false
        return false;
    }
}
