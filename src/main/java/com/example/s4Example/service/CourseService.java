package com.example.s4Example.service;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import java.util.List;
import java.util.Map;

import com.example.s4Example.model.Student;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    List<Course> getAllCourses();

    Course getCourseByCode(Long code) throws ResourceNotFoundException;

    Course createCourse(Course myClass);

    Course editCourse(Long code, Course courseDetails) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCourse(Long code) throws ResourceNotFoundException;

    Course addStudent(Student student, Long courseCode) throws ResourceNotFoundException;
}
