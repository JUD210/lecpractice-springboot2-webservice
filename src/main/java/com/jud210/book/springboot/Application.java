package com.jud210.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// @EnableJpaAuditing을 쓰고 HelloControllerTest를 실행하면 아래와 같은 오류가 뜬다.
// java.lang.IllegalStateException: Failed to load ApplicationContext
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
