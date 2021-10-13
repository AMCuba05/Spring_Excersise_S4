package com.example.s4Example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CourseTest {
    @Test
    public void testAddStudent(){
        Course course = new Course("Math" , "Class of 5th grade" , null);
        Student student1 = new Student("John", "Doe", null);
        Student student2 = new Student("James", "Doe", null);
        Student student3 = new Student("Jack", "Doe", null);
        course.addStudent(student1);
        course.addStudent(student2);
        course.addStudent(student3);
        assertEquals(course.getStudents().size(), 3);
    }
}