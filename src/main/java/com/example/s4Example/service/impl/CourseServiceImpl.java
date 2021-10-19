package com.example.s4Example.service.impl;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.repository.CourseRepository;
import java.util.List;

import com.example.s4Example.service.CourseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course getCourseByCode(Long code) throws ResourceNotFoundException {
        Course course = (Course)this.courseRepository.findById(code).orElseThrow(() -> {
            return new ResourceNotFoundException(String.format("Class not found for code %s", code));
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
            return new ResourceNotFoundException(String.format("Class not found for code %s", code));
        });
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setStudents(courseDetails.getStudents());
        Course editedCourse = (Course)this.courseRepository.save(course);
        return editedCourse;
    }

    @Override
    public Boolean deleteCourse(Long code) throws ResourceNotFoundException {
        Course course = (Course)this.courseRepository.findById(code).orElseThrow(() -> {
            return new ResourceNotFoundException(String.format("Class not found for code %s", code));
        });
        this.courseRepository.delete(course);
        return Boolean.TRUE;
    }

}
