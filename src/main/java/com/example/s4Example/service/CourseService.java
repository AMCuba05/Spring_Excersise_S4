package com.example.s4Example.service;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    Course getCourseByCode(Long code) throws ResourceNotFoundException;

    Course createCourse(Course myClass);

    Course editCourse(Long code, Course courseDetails) throws ResourceNotFoundException;

    Boolean deleteCourse(Long code) throws ResourceNotFoundException;
}
