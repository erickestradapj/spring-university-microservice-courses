package com.dev.microservices.app.courses.services;

import com.dev.microservices.app.courses.models.entity.Course;
import com.dev.microservices.commons.services.CommonService;

public interface CourseService extends CommonService<Course> {

    Course findCourseByStudentId(Long id);

    Iterable<Long> getExamsIdsWithAnswersStudent(Long studentId);
}
