package com.example.s4Example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(
        name = "students"
)
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long id;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public Student() {
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(
            name = "first_name",
            nullable = false
    )
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(
            name = "last_name",
            nullable = false
    )
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
