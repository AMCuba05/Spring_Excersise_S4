package com.example.s4Example.service.impl;

import com.example.s4Example.CourseMockData;
import java.util.Optional;
import com.example.s4Example.exceptions.ResourceNotFoundException;
import com.example.s4Example.model.Course;
import com.example.s4Example.repository.CourseRepository;
import com.example.s4Example.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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

        when(courseRepository.findById(course.getCode())).thenReturn(Optional.of(course));

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

        when(courseRepository.findById(course.getCode())).thenReturn(Optional.of(course));

        course.setTitle("new title");
        course.setDescription("new description");
        course.setStudents(new ArrayList<>());

        when(courseRepository.save(course)).thenReturn(course);

        Course editedCourse = courseService.editCourse( course.getCode(),course);

        assertEquals(editedCourse.getTitle(), course.getTitle());
        assertEquals(editedCourse.getDescription(), course.getDescription());
        assertEquals(editedCourse.getStudents(), course.getStudents());

    }

    @Test
    public void testDeleteCourse() throws ResourceNotFoundException {

        Course course = CourseMockData.mockCourse().builder().build();

        when(courseRepository.findById(course.getCode())).thenReturn(Optional.of(course));

        doNothing().when(courseRepository).delete(course);

        boolean deleted = courseService.deleteCourse(course.getCode());
        assertEquals(deleted , Boolean.TRUE);

    }

    @Test
    public void testDeleteCourseError(){
        Exception exception = assertThrows(ResourceNotFoundException.class, ()-> {
            boolean deleted = courseService.deleteCourse(5L);
            when(courseRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);
        });

        assertEquals("Class not found for code 5", exception.getMessage());
    }

    @Test
    public void testEditCourseError(){
        Exception exception = assertThrows(ResourceNotFoundException.class, ()-> {
            Course course = CourseMockData.mockCourse().builder().build();
            course.setTitle("new title");
            course.setDescription("new description");
            course.setStudents(new ArrayList<>());
            Course editedCourse = courseService.editCourse( 5L,course);

            when(courseRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);
        });

        assertEquals("Class not found for code 5", exception.getMessage());
    }

    @Test
    public void testGetCourseByCodeError(){
        Exception exception = assertThrows(ResourceNotFoundException.class, ()-> {
            Course courseSaved = courseService.getCourseByCode(5L);
            when(courseRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);
        });
        assertEquals("Class not found for code 5", exception.getMessage());
    }
}
