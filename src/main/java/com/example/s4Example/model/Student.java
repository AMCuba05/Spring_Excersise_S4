package com.example.s4Example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "t_students"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long id;
    @Column( name = "first_name" )
    private String firstName;
    @Column( name = "last_name")
    private String lastName;

    @ManyToMany( targetEntity = Course.class,mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Course> courses;

    public void addCourse(Course course){
        if(this.courses == null) {
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }
}
