package com.example.s4Example.service.impl;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.model.Student;
import com.example.s4Example.repository.CourseRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.s4Example.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseRepository courseRepository;

    public CourseServiceImpl() {
    }

    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course getCourseByCode(Long code) throws ResourceNotFoundException {
        Course course = (Course)this.courseRepository.findById(code).orElseThrow(() -> {
            return new ResourceNotFoundException("Class not found for code" + code);
        });
        return course;
    }

    @Override
    public Course createCourse(Course myClass) {
        return (Course)this.courseRepository.save(myClass);
    }

    @Override
    public Course editCourse(Long code, Course courseDetails) throws ResourceNotFoundException {
        Course course = (Course)this.courseRepository.findById(code).orElseThrow(() -> {
            return new ResourceNotFoundException("Class not found for code" + code);
        });
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setStudents(courseDetails.getStudents());
        Course editedCourse = (Course)this.courseRepository.save(course);
        return editedCourse;
    }

    @Override
    public Map<String, Boolean> deleteCourse(Long code) throws ResourceNotFoundException {
        Course course = (Course)this.courseRepository.findById(code).orElseThrow(() -> {
            return new ResourceNotFoundException("Class not found for code" + code);
        });
        this.courseRepository.delete(course);
        Map<String, Boolean> response = new HashMap();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public Course addStudent(Student student, Long courseCode) throws ResourceNotFoundException{
        Course course = (Course)this.courseRepository.findById(courseCode).orElseThrow(() -> {
            return new ResourceNotFoundException("Class not found for code" + courseCode);
        });
        course.addStudent(student);
        Course editedCourse = (Course)this.courseRepository.save(course);
        return editedCourse;
    }
}
