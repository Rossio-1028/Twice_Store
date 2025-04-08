package com.shanzhu.st;

import com.shanzhu.st.utils.OrderTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 启动类
 *
 *
 */
@Slf4j
@SpringBootApplication()
public class BackendApplication {

    public static void main(String[] args) {
        //SpringBoot 执行启动
        SpringApplication.run(BackendApplication.class, args);

        OrderTaskHandler.run();

        log.info("=====================项目后端启动成功============================");

    }

}
