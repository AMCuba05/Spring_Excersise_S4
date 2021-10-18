package com.example.s4Example.controller;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import com.example.s4Example.service.StudentService;
import com.example.s4Example.service.impl.StudentServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
public class StudentController {
    @Autowired
    private StudentService studentService;

    public StudentController() {
    }

    @GetMapping({"/students"})
    public List<Student> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    @GetMapping({"/students/{id}"})
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) throws ResourceNotFoundException {
        Student student = this.studentService.getStudentById(studentId);
        return ResponseEntity.ok().body(student);
    }

    @PostMapping({"/students"})
    public Student createStudent(@Valid @RequestBody Student student) {
        return this.studentService.createStudent(student);
    }

    @PutMapping({"/students/{id}"})
    public ResponseEntity<Student> editStudent(@PathVariable("id") Long studentId, @Validated @RequestBody Student studentDetails) throws ResourceNotFoundException {
        Student editedStudent = this.studentService.editStudent(studentId, studentDetails);
        return ResponseEntity.ok().body(editedStudent);
    }

    @DeleteMapping({"students/{id}"})
    public Map<String, Boolean> deleteStudent(@PathVariable("id") Long studentId) throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap();
        response.put("deleted",this.studentService.deleteStudent(studentId));
        return response;
    }
}
