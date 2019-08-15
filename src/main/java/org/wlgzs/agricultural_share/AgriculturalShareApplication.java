package org.wlgzs.agricultural_share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//注释部分打war包时需要
//@ServletComponentScan
//public class AgriculturalShareApplication extends SpringBootServletInitializer {
public class AgriculturalShareApplication {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AgriculturalShareApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(AgriculturalShareApplication.class, args);
    }

}
