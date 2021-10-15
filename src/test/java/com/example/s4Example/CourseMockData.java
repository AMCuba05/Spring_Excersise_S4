package com.example.s4Example;

import com.example.s4Example.model.Course;
import org.apache.commons.lang3.RandomUtils;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class CourseMockData {
    public static Course mockCourse(){
        Faker faker = new Faker();
        int randomValue = RandomUtils.nextInt();
        return Course.builder()
                .code(randomValue)
                .title(faker.educator().course())
                .description(faker.pokemon().name())
                .students(new ArrayList<>())
                .build();
    }
}
