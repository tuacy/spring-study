package com.tuacy.jta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @name: SecurityApplication
 * @author: tuacy.
 * @date: 2019/11/14.
 * @version: 1.0
 * @Description:
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)  // 启注解事务管理，等同于xml配置方式的
@MapperScan("com.tuacy.jta.mapper")
public class JtaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtaServerApplication.class, args);
    }

}
