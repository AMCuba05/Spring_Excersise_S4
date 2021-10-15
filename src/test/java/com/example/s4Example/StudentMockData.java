package com.example.s4Example;

import com.example.s4Example.model.Student;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class StudentMockData {
    public static Student mockStudent(){
        Faker faker = new Faker();
        return Student.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .courses(new ArrayList<>())
                .build();
    }
}
