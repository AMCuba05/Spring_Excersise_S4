package com.example.s4Example.dto;

import com.example.s4Example.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long code;
    private String title;
    private String description;
    private List<StudentDTO> students;
}
