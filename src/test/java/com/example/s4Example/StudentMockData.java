package com.example.s4Example;

import com.example.s4Example.model.Student;

import java.util.ArrayList;

public class StudentMockData {
    public static Student mockStudent(){
        return Student.builder()
                .firstName("Normal first name")
                .lastName("Normal last name")
                .courses(new ArrayList<>())
                .build();
    }
}
