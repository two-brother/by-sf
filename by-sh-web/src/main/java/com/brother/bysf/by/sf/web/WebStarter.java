package com.brother.bysf.by.sf.web;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sk-shifanwen
 * @date 2017/12/14
 */
@SpringBootApplication
public class WebStarter {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(WebStarter.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
