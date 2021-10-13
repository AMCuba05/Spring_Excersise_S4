package com.example.s4Example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "students"
)
@Data
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long id;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public void addCourse(Course course){
        if(this.courses == null) {
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }
}
