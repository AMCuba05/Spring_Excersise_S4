package com.example.s4Example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Entity
@Table(
        name = "courses"
)
@Data
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long code;
    @Column(
            name = "title"
    )
    private String title;
    @Column(
            name = "description"
    )
    private String description;
    @JoinTable(
            name = "rel_courses_students",
            joinColumns = @JoinColumn(name = "FK_COURSE", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_STUDENT", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    @Column(
            name = "students"
    )
    private List<Student> students;

    public void addStudent(Student student){
        if(this.students == null) {
            this.students = new ArrayList<>();
        }
        this.students.add(student);
    }

}

