package com.example.s4Example.controller;

import com.example.s4Example.dto.CourseDTO;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.service.impl.CourseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.example.s4Example.utils.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    public CourseController() {
    }

    @GetMapping({"/courses"})
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = this.courseServiceImpl.getAllCourses();
        ObjectMapperUtils mapper = new ObjectMapperUtils();
        List<CourseDTO> response = mapper.mapAll(courses, CourseDTO.class );
        return response;
    }

    @GetMapping({"/courses/{code}"})
    public ResponseEntity<CourseDTO> getCourseByCode(@PathVariable("code") Long code) throws ResourceNotFoundException {
        Course course = this.courseServiceImpl.getCourseByCode(code);
        return ResponseEntity.ok().body(mapper.map(course, CourseDTO.class));
    }

    @PostMapping({"/courses"})
    public CourseDTO createCourse(@Valid @RequestBody CourseDTO course) {
        Course source = mapper.map(course, Course.class);
        Course response = this.courseServiceImpl.createCourse(source);
        return mapper.map(response, CourseDTO.class);
    }

    @PutMapping({"/courses/{code}"})
    public ResponseEntity<CourseDTO> editCourse(@PathVariable("code") Long code, @Valid @RequestBody CourseDTO courseDetails) throws ResourceNotFoundException {
        Course destination = mapper.map(courseDetails, Course.class);
        CourseDTO course = mapper.map(this.courseServiceImpl.editCourse(code, destination), CourseDTO.class);
        return ResponseEntity.ok().body(course);
    }

    @DeleteMapping({"/courses/{code}"})
    public Map<String, Boolean> deleteCourse(@PathVariable("code") Long code) throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap();
        response.put("deleted" , this.courseServiceImpl.deleteCourse(code) );
        return response;
    }
}
