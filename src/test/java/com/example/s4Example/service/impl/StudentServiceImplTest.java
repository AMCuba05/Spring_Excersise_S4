package com.example.s4Example.service.impl;

import com.example.s4Example.StudentMockData;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import com.example.s4Example.repository.StudentRepository;
import com.example.s4Example.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentService studentService = new StudentServiceImpl();

    @Mock
    private StudentRepository studentRepository;

    @Test
    public void testGetAllStudents(){
        Student student = StudentMockData.mockStudent().builder().build();
        Student student1 = StudentMockData.mockStudent().builder().build();
        Student student2 = StudentMockData.mockStudent().builder().build();

        List<Student> studentList = new ArrayList<>();

        studentList.add(student);
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> results = studentService.getAllStudents();
        assertEquals(results.size() , studentList.size() );
        assertEquals(results.get(0).getId() , studentList.get(0).getId());
    }

    @Test
    public void testGetStudentById() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student studentSaved = studentService.getStudentById(student.getId());

        assertEquals(student.getId() ,studentSaved.getId());
    }

    @Test
    public void testCreateStudent() {
        Student student = StudentMockData.mockStudent().builder().build();

        when(studentRepository.save(student)).thenReturn(student);

        Student studentCreated = studentService.createStudent(student);

        assertEquals(studentCreated.getId(), student.getId());
    }

    @Test
    public void testEditStudent() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        student.setFirstName("new first name");
        student.setLastName("new last name");
        student.setCourses(new ArrayList<>());

        when(studentRepository.save(student)).thenReturn(student);

        Student editedStudent = studentService.editStudent(student.getId() ,student);

        assertEquals(editedStudent.getFirstName(), student.getFirstName());
        assertEquals(editedStudent.getLastName(), student.getLastName());
        assertEquals(editedStudent.getCourses(), student.getCourses());
    }

    @Test
    public void testDeleteStudent() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        doNothing().when(studentRepository).delete(student);

        boolean deleted = studentService.deleteStudent(student.getId());
        assertEquals(deleted , Boolean.TRUE);
    }

    @Test
    public void testDeleteStudentError(){
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
            Student student = StudentMockData.mockStudent().builder().build();
            boolean deleted = studentService.deleteStudent(student.getId());

            doNothing().when(studentRepository).delete(student);
            when(studentRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);

        });
    }

    @Test
    public void testEditStudentError(){
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
            Student student = StudentMockData.mockStudent().builder().build();
            student.setFirstName("new first name");
            student.setLastName("new last name");
            student.setCourses(new ArrayList<>());
            Student editStudent = studentService.editStudent( student.getId(),student);

            when(studentRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);
        });
    }

    @Test
    public void testGetStudentByIdError(){
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
            Student student = StudentMockData.mockStudent().builder().build();
            Student studentSaved = studentService.getStudentById(student.getId());

            when(studentRepository.findById(student.getId())).thenThrow(ResourceNotFoundException.class);
        });
    }

}
