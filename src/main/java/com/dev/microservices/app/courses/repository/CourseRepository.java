package com.dev.microservices.app.courses.repository;

import com.dev.microservices.app.courses.models.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN FETCH c.students s WHERE s.id=?1")
    Course findCourseByStudentId(Long id);
}
