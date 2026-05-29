package com.aurora;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whb
 */
@SpringBootApplication
@EnableFileStorage
@MapperScan("com.aurora.mapper")
public class AuroraServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuroraServerApplication.class, args);
    }
} 