package com.cc.ata;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.cc.ata.common.config.MyExceptionResolver;
import com.cc.ata.common.utils.CountDownUtils;
import com.cc.ata.websocket.WebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.Scanner;

@EnableTransactionManagement//开启事物支持，启用注解事物管理
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableCaching
public class QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickApplication.class, args);
        while (true) {
            System.out.println("操作| 0：关闭 1：什么都不做");
            Scanner scanner = new Scanner(System.in);
            String a = scanner.nextLine();
            if (Objects.equals(a, "0")) {
                WebSocket webSocket = new WebSocket();
                CountDownUtils.daoJiShi(32, webSocket);
            }
        }
    }

    @Bean
    public TaskRunner taskRunner() {
        return new TaskRunner();
    }

    // 注册统一异常处理bean
    @Bean
    public MyExceptionResolver myExceptionResolver() {
        return new MyExceptionResolver();
    }
}
