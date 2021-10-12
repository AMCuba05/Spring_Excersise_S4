package com.example.s4Example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "courses"
)
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long code;
    private String title;
    private String description;
    @JoinTable(
            name = "rel_courses_students",
            joinColumns = @JoinColumn(name = "FK_COURSE", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_STUDENT", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Student> students;

    public Course() {
    }

    public Course(String title, String description, List<Student> students) {
        this.title = title;
        this.description = description;
        this.students = students;
    }

    public void addStudent(Student student){
        if(this.students == null) {
            this.students = new ArrayList<>();
        }
        this.students.add(student);
    }

    public long getCode() {
        return this.code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Column(
            name = "title"
    )
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(
            name = "description"
    )
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(
            name = "students"
    )
    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}

