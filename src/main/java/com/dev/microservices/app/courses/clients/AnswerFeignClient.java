package com.dev.microservices.app.courses.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-answers")
public interface AnswerFeignClient {

    @GetMapping("/student/{studentId}/exams-answered")
    Iterable<Long> getExamsIdsWithAnswersStudent(@PathVariable Long studentId);
}
