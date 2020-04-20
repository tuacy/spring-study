package com.tuacy.collect.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/4/20 21:46.
 */
@SpringBootApplication
@EnableRetry
public class RetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetryApplication.class, args);
    }

}
