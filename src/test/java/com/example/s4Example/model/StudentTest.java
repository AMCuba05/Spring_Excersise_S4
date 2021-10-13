package com.example.s4Example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    @Test
    public void testAddStudent(){
        Student student1 = new Student("John" , "Doe" , null);
        Student student2 = new Student("James", "Doe", null);
        Student student3 = new Student("Jack", "Doe", null);
        Course course1 = new Course("Math", "Math class of 5th grade", null);
        Course course2 = new Course("History", "History class of 5th grade", null);
        student1.addCourse(course1);
        student1.addCourse(course2);
        student2.addCourse(course1);
        student3.addCourse(course1);
        assertEquals(student1.getCourses().size(), 2);
        assertEquals(student2.getCourses().size(), 1);
        assertEquals(student3.getCourses().size(), 1);
    }
}
