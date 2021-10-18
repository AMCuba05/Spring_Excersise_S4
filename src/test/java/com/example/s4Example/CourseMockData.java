package com.example.s4Example;

import com.example.s4Example.model.Course;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class CourseMockData {
    public static Course mockCourse(){
        int randomValue = RandomUtils.nextInt();
        return Course.builder()
                .code(randomValue)
                .title("Normal Course title")
                .description("normal course description")
                .students(new ArrayList<>())
                .build();
    }
}
