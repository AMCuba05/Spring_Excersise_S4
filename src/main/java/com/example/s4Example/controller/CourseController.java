//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.s4Example.controller;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.model.Student;
import com.example.s4Example.service.impl.CourseServiceImpl;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1"})
public class CourseController {
    @Autowired
    private CourseServiceImpl courseServiceImpl;

    public CourseController() {
    }

    @GetMapping({"/courses"})
    public List<Course> getAllCourses() {
        return this.courseServiceImpl.getAllCourses();
    }

    @GetMapping({"/courses/{code}"})
    public ResponseEntity<Course> getCourseByCode(@PathVariable("code") Long code) throws ResourceNotFoundException {
        Course course = this.courseServiceImpl.getCourseByCode(code);
        return ResponseEntity.ok().body(course);
    }

    @PostMapping({"/courses"})
    public Course createCourse(@Valid @RequestBody Course course) {
        return this.courseServiceImpl.createCourse(course);
    }

    @PutMapping({"/courses/{code}"})
    public ResponseEntity<Course> editCourse(@PathVariable("code") Long code, @Valid @RequestBody Course courseDetails) throws ResourceNotFoundException {
        Course course = this.courseServiceImpl.editCourse(code, courseDetails);
        return ResponseEntity.ok().body(course);
    }

    @PutMapping({"/courses/addStudent/{code}"})
    public ResponseEntity<Course> addStudent(@PathVariable("code") Long code, @Valid @RequestBody Student student) throws ResourceNotFoundException {
        Course course = this.courseServiceImpl.addStudent(student, code);
        return ResponseEntity.ok().body(course);
    }

    @DeleteMapping({"/courses/{code}"})
    public Map<String, Boolean> deleteCourse(@PathVariable("code") Long code) throws ResourceNotFoundException {
        return this.courseServiceImpl.deleteCourse(code);
    }
}
