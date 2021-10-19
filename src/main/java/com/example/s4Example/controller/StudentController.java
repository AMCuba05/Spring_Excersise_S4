package com.example.s4Example.controller;

import com.example.s4Example.dto.StudentDTO;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import com.example.s4Example.service.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ModelMapper mapper;

    public StudentController() {
    }

    @GetMapping({"/students"})
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<Student> students = this.studentService.getAllStudents();
        List<StudentDTO> studentsDto = new ArrayList<>();
        for (Student entity : students) {
            StudentDTO map = mapper.map(entity, StudentDTO.class);
            studentsDto.add(map);
        }
        return new ResponseEntity<>(studentsDto , HttpStatus.OK);
    }

    @GetMapping({"/students/{id}"})
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long studentId) throws ResourceNotFoundException {
        Student student = this.studentService.getStudentById(studentId);
        StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping({"/students"})
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO student) {
        Student source = mapper.map(student, Student.class);
        Student result = this.studentService.createStudent(source);
        StudentDTO body = mapper.map(result, StudentDTO.class);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping({"/students/{id}"})
    public ResponseEntity<StudentDTO> editStudent(@PathVariable("id") Long studentId, @Validated @RequestBody StudentDTO studentDetails) throws ResourceNotFoundException {
        Student source = mapper.map(studentDetails, Student.class);
        Student editedStudent = this.studentService.editStudent(studentId, source);
        StudentDTO body = mapper.map(editedStudent, StudentDTO.class);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping({"students/{id}"})
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable("id") Long studentId) throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap();
        response.put("deleted",this.studentService.deleteStudent(studentId));
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
