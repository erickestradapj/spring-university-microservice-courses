package com.dev.microservices.app.courses.models.entity;

import com.dev.microservices.commons.exams.models.entity.Exam;
import com.dev.microservices.commons.students.models.entity.Student;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Exam> exams;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Course() {
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    public void removeExam(Exam exam) {
        this.exams.remove(exam);
    }
}
