package com.example.s4Example.service;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentById(Long studentId) throws ResourceNotFoundException;

    Student createStudent(Student student);

    Student editStudent(Long studentId, Student studentDetails) throws ResourceNotFoundException;

    Boolean deleteStudent(Long studentId) throws ResourceNotFoundException;
}
