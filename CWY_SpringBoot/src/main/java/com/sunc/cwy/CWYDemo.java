package com.sunc.cwy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sunc
 */
@SpringBootApplication
@MapperScan("com.sunc.cwy.mapper")
public class CWYDemo {

    public static void main(String[] args) {
        SpringApplication.run(CWYDemo.class, args);
    }

}
