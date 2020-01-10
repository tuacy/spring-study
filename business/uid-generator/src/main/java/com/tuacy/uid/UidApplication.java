package com.tuacy.uid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @name: UidApplication
 * @author: tuacy.
 * @date: 2020/1/10.
 * @version: 1.0
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.tuacy.uid.mapper")
public class UidApplication {

    public static void main(String[] args) {
        SpringApplication.run(UidApplication.class, args);
    }

}
