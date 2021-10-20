package com.example.s4Example.controller;

import com.example.s4Example.dto.CourseDTO;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.service.CourseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@NoArgsConstructor
@RequestMapping({"/api/v1"})
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping({"/courses"})
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = this.courseService.getAllCourses();
        List<CourseDTO> coursesDto = new ArrayList<>();
        for (Course entity : courses) {
            CourseDTO map = mapper.map(entity, CourseDTO.class);
            coursesDto.add(map);
        }
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }

    @GetMapping({"/courses/{code}"})
    public ResponseEntity<CourseDTO> getCourseByCode(@PathVariable("code") Long code) throws ResourceNotFoundException {
        Course course = this.courseService.getCourseByCode(code);
        CourseDTO body = mapper.map(course, CourseDTO.class);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping({"/courses"})
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO course) {
        Course source = mapper.map(course, Course.class);
        Course result = this.courseService.createCourse(source);
        CourseDTO body = mapper.map(result, CourseDTO.class);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping({"/courses/{code}"})
    public ResponseEntity<CourseDTO> editCourse(@PathVariable("code") Long code, @Valid @RequestBody CourseDTO courseDetails) throws ResourceNotFoundException {
        Course destination = mapper.map(courseDetails, Course.class);
        CourseDTO course = mapper.map(this.courseService.editCourse(code, destination), CourseDTO.class);
        return ResponseEntity.ok().body(course);
    }

    @DeleteMapping({"/courses/{code}"})
    public ResponseEntity<Map<String, Boolean>> deleteCourse(@PathVariable("code") Long code) throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap();
        response.put("deleted" , this.courseService.deleteCourse(code) );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
