package com.example.s4Example.service.impl;

import com.example.s4Example.StudentMockData;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceImplTest {
    @Autowired
    private StudentServiceImpl studentService;

    @Test
    public void testGetAllStudents(){
        Student student = StudentMockData.mockStudent().builder().build();
        Student student1 = StudentMockData.mockStudent().builder().build();
        Student student2 = StudentMockData.mockStudent().builder().build();

        studentService.createStudent(student);
        studentService.createStudent(student1);
        studentService.createStudent(student2);

        int studentsSaved = studentService.getAllStudents().size();
        assertEquals(3,studentsSaved );

    }

    @Test
    public void testGetStudentById() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();
        Student student1 = StudentMockData.mockStudent().builder().build();
        Student student2 = StudentMockData.mockStudent().builder().build();

        studentService.createStudent(student);
        studentService.createStudent(student1);
        studentService.createStudent(student2);

        Student studentsSaved = studentService.getStudentById(student.getId());
        Student studentsSaved1 = studentService.getStudentById(student1.getId());
        Student studentsSaved2 = studentService.getStudentById(student2.getId());

        assertEquals(studentsSaved.getId() ,student.getId());
        assertEquals(studentsSaved1.getId() ,student1.getId());
        assertEquals(studentsSaved2.getId() ,student2.getId());

        studentService.deleteStudent(student.getId());
        studentService.deleteStudent(student1.getId());
        studentService.deleteStudent(student2.getId());
    }

    @Test
    public void testCreateStudent() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();
        studentService.createStudent(student);
        int studentsSaved = studentService.getAllStudents().size();
        assertEquals(1,studentsSaved );
        studentService.deleteStudent(student.getId());
    }

    @Test
    public void testEditStudent() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();
        studentService.createStudent(student);

        student.setLastName("EditedLastName");
        student.setFirstName("EditedFirstName");

        Student editedStudent = studentService.editStudent(student.getId(), student);

        assertEquals(editedStudent.getFirstName(), student.getFirstName());
        assertEquals(editedStudent.getLastName(), student.getLastName());

        studentService.deleteStudent(student.getId());
    }

    @Test
    public void testDeleteStudent() throws ResourceNotFoundException {
        Student student = StudentMockData.mockStudent().builder().build();
        studentService.createStudent(student);
        Boolean deleted = studentService.deleteStudent(student.getId());
        assertEquals( Boolean.TRUE, deleted);
    }
}
