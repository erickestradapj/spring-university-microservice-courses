package com.dev.microservices.app.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.dev.microservices.commons.exams.models.entity",
        "com.dev.microservices.commons.students.models.entity",
        "com.dev.microservices.app.courses.models.entity"})
public class MicroserviceCoursesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCoursesApplication.class, args);
    }
}
