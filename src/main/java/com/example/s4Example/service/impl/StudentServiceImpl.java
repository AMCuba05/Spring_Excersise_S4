package com.example.s4Example.service.impl;

import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import com.example.s4Example.repository.StudentRepository;
import com.example.s4Example.service.StudentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    private StudentRepository studentRepository;

    public StudentServiceImpl() {
    }

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) throws ResourceNotFoundException {
        Student student = (Student)this.studentRepository.findById(studentId).orElseThrow(() -> {
            return new ResourceNotFoundException("Student not found for id: " + studentId);
        });
        return student;
    }

    public Student createStudent(Student student) {
        return (Student)this.studentRepository.save(student);
    }

    public Student editStudent(Long studentId, Student studentDetails) throws ResourceNotFoundException {
        Student student = (Student)this.studentRepository.findById(studentId).orElseThrow(() -> {
            return new ResourceNotFoundException("Student not found for id: " + studentId);
        });
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        Student editedStudent = (Student)this.studentRepository.save(student);
        return editedStudent;
    }

    public Boolean deleteStudent(Long studentId) throws ResourceNotFoundException {
        Student student = (Student)this.studentRepository.findById(studentId).orElseThrow(() -> {
            return new ResourceNotFoundException("Student not found for id: " + studentId);
        });
        this.studentRepository.delete(student);
        return Boolean.TRUE;
    }
}
