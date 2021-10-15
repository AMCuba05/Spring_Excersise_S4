package com.example.s4Example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.example.s4Example.dto.StudentDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
        name = "t_courses"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "code")
public class Course {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long code;
    private String title;
    private String description;
    @JoinTable(
            name = "rel_courses_students",
            joinColumns = @JoinColumn(name = "FK_COURSE", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_STUDENT", nullable = false)
    )
    @ManyToMany( targetEntity = Student.class,cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH} )
    private List<Student> students;

}

