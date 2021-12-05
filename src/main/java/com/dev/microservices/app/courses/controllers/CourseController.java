package com.dev.microservices.app.courses.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.dev.microservices.commons.exams.models.entity.Exam;
import com.dev.microservices.commons.students.models.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.microservices.app.courses.models.entity.Course;
import com.dev.microservices.app.courses.services.CourseService;
import com.dev.microservices.commons.controllers.CommonController;

@RestController
public class CourseController extends CommonController<Course, CourseService> {

    @Value("${config.balancer.test}")
    private String balancerTest;

    @GetMapping("/balancer-test")
    public ResponseEntity<?> balancerTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("balancer", balancerTest);
        response.put("courses", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validate(result);
        }

        Optional<Course> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course courseDB = o.get();
        courseDB.setName(course.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDB));
    }

    @PutMapping("/{id}/assign-students")
    public ResponseEntity<?> assignStudents(@RequestBody List<Student> students, @PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course courseDB = o.get();

        students.forEach(courseDB::addStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDB));
    }

    @PutMapping("/{id}/delete-student")
    public ResponseEntity<?> deleteStudent(@RequestBody Student student, @PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course courseDB = o.get();

        courseDB.removeStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDB));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> searchByStudentId(@PathVariable Long id) {
        Course course = service.findCourseByStudentId(id);

        if (course != null) {

            List<Long> examsId = (List<Long>) service.getExamsIdsWithAnswersStudent(id);

            List<Exam> exams = course.getExams().stream().map(exam -> {
                if (examsId.contains(exam.getId())) {
                    exam.setAnswered(true);
                }
                return exam;
            }).collect(Collectors.toList());

            course.setExams(exams);
        }

        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}/assign-exams")
    public ResponseEntity<?> assignExams(@RequestBody List<Exam> exams, @PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course courseDB = o.get();

        exams.forEach(courseDB::addExam);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDB));
    }

    @PutMapping("/{id}/delete-exam")
    public ResponseEntity<?> deleteExam(@RequestBody Exam exam, @PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course courseDB = o.get();

        courseDB.removeExam(exam);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDB));
    }
}
