package com.example.s4Example.service.impl;

import com.example.s4Example.CourseMockData;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.repository.CourseRepository;
import com.example.s4Example.service.CourseService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    protected CourseService courseService = new CourseServiceImpl();
    @Mock
    protected CourseRepository courseRepository;

    @Test
    public void testGetAllCourses(){
        Course course = CourseMockData.mockCourse().builder().build();
        Course course1 = CourseMockData.mockCourse().builder().build();
        Course course2 = CourseMockData.mockCourse().builder().build();

        List<Course> courseList = new ArrayList<>();

        courseList.add(course);
        courseList.add(course1);
        courseList.add(course2);

        when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> results = courseService.getAllCourses();
        assertEquals(results.size() , courseList.size() );
        assertEquals(results.get(0).getCode() , courseList.get(0).getCode());

    }

    @Test
    public void testGetCourseById() throws ResourceNotFoundException {
        Course course = CourseMockData.mockCourse().builder().build();

        when(courseRepository.findById(course.getCode())).thenReturn(java.util.Optional.of(course));

        Course courseSaved = courseService.getCourseByCode(course.getCode());

        assertEquals(courseSaved.getCode() ,course.getCode());

    }

    @Test
    public void testCreateCourse() {
        Course course = CourseMockData.mockCourse().builder().build();

        when(courseRepository.save(course)).thenReturn(course);

        Course courseCreated = courseService.createCourse(course);

        assertEquals(courseCreated.getCode(), course.getCode());

    }

    @Test
    public void testEditCourse() throws ResourceNotFoundException {
        Course course = CourseMockData.mockCourse().builder().build();

        when(courseRepository.findById(course.getCode())).thenReturn(java.util.Optional.of(course));

        Course courseSaved = courseService.getCourseByCode(course.getCode());

        courseSaved.setTitle("new title");
        courseSaved.setDescription("new description");
        courseSaved.setStudents(new ArrayList<>());

        when(courseRepository.save(courseSaved)).thenReturn(courseSaved);

        Course editedCourse = courseService.createCourse(courseSaved);

        assertEquals(editedCourse.getTitle(), courseSaved.getTitle());
        assertEquals(editedCourse.getDescription(), courseSaved.getDescription());
        assertEquals(editedCourse.getStudents(), courseSaved.getStudents());

    }

    @Test
    public void testDeleteCourse() throws ResourceNotFoundException {

        Course course = CourseMockData.mockCourse().builder().build();

        when(courseRepository.findById(course.getCode())).thenReturn(java.util.Optional.of(course));

        Course courseSaved = courseService.getCourseByCode(course.getCode());

        courseRepository.delete(courseSaved);
        boolean deleted = courseService.deleteCourse(courseSaved.getCode());
        assertEquals(deleted , Boolean.TRUE);

    }
}
