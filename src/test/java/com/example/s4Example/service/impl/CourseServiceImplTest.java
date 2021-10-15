package com.example.s4Example.service.impl;

import com.example.s4Example.CourseMockData;
import com.example.s4Example.StudentMockData;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseServiceImplTest {
    @Autowired
    private CourseServiceImpl courseService;

    @Test
    public void testGetAllCourses() throws ResourceNotFoundException{
        Course course = CourseMockData.mockCourse().builder().build();
        Course course1 = CourseMockData.mockCourse().builder().build();
        Course course2 = CourseMockData.mockCourse().builder().build();

        courseService.createCourse(course);
        courseService.createCourse(course1);
        courseService.createCourse(course2);

        int studentsSaved = courseService.getAllCourses().size();
        assertEquals(3,studentsSaved );

        courseService.deleteCourse(course.getCode());
        courseService.deleteCourse(course1.getCode());
        courseService.deleteCourse(course2.getCode());

    }

    @Test
    public void testGetCourseById() throws ResourceNotFoundException {
        Course course = CourseMockData.mockCourse().builder().build();
        Course course1 = CourseMockData.mockCourse().builder().build();
        Course course2 = CourseMockData.mockCourse().builder().build();

        courseService.createCourse(course);
        courseService.createCourse(course1);
        courseService.createCourse(course2);

        Course studentsSaved = courseService.getCourseByCode(course.getCode());
        Course studentsSaved1 = courseService.getCourseByCode(course1.getCode());
        Course studentsSaved2 = courseService.getCourseByCode(course2.getCode());

        assertEquals(studentsSaved.getCode() ,course.getCode());
        assertEquals(studentsSaved1.getCode() ,course1.getCode());
        assertEquals(studentsSaved2.getCode() ,course2.getCode());

        courseService.deleteCourse(course.getCode());
        courseService.deleteCourse(course1.getCode());
        courseService.deleteCourse(course2.getCode());
    }

    @Test
    public void testCreateCourse() throws ResourceNotFoundException {
        Course course = CourseMockData.mockCourse().builder().build();
        courseService.createCourse(course);
        int studentsSaved = courseService.getAllCourses().size();
        assertEquals(1,studentsSaved );
        courseService.deleteCourse(course.getCode());
    }

    @Test
    public void testEditCourse() throws ResourceNotFoundException {
        Course student = CourseMockData.mockCourse().builder().build();
        courseService.createCourse(student);

        student.setTitle("EditedTitle");
        student.setDescription("EditedDescription");

        Course editedStudent = courseService.editCourse(student.getCode(), student);

        assertEquals(editedStudent.getTitle(), student.getTitle());
        assertEquals(editedStudent.getDescription(), student.getDescription());

        courseService.deleteCourse(student.getCode());
    }

    @Test
    public void testDeleteCourse() throws ResourceNotFoundException {
        Course student = CourseMockData.mockCourse().builder().build();
        courseService.createCourse(student);
        Boolean deleted = courseService.deleteCourse(student.getCode());
        assertEquals( Boolean.TRUE, deleted);
    }
}
