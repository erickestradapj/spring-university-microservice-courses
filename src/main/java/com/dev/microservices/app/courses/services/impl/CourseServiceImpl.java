package com.dev.microservices.app.courses.services.impl;

import com.dev.microservices.app.courses.clients.AnswerFeignClient;
import com.dev.microservices.app.courses.models.entity.Course;
import com.dev.microservices.app.courses.repository.CourseRepository;
import com.dev.microservices.app.courses.services.CourseService;
import com.dev.microservices.commons.services.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl extends CommonServiceImpl<Course, CourseRepository> implements CourseService {

    @Autowired
    private AnswerFeignClient client;

    @Override
    @Transactional(readOnly = true)
    public Course findCourseByStudentId(Long id) {
        return repository.findCourseByStudentId(id);
    }

    @Override
    public Iterable<Long> getExamsIdsWithAnswersStudent(Long studentId) {
        return client.getExamsIdsWithAnswersStudent(studentId);
    }
}
