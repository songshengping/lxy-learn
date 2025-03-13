package com.lxy.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.lxy.learn"})
public class LxyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxyLearnApplication.class, args);
    }

}
