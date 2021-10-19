package com.example.s4Example.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.example.s4Example.CourseMockData;
import com.example.s4Example.dto.CourseDTO;
import com.example.s4Example.model.Course;
import com.example.s4Example.service.impl.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CourseControllerITest {

        public MockMvc mockMvc;

        @Autowired
        private ModelMapper mapper;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private CourseController courseController;

        @MockBean
        private CourseServiceImpl courseService;

        public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        }

        @Test
        void testGetCourse() throws Exception {
                // given
                Course course = CourseMockData.mockCourse().builder().build();
                course.setCode(1L);

                when(courseService.getCourseByCode(1L))
                        .thenReturn(course);

                // when then
                MvcResult result = mockMvc.perform(get("/api/v1/courses/1"))
                        .andExpect(status().is(200)).andReturn();
                String content = result.getResponse().getContentAsString();

                assertNotNull(content);
                assertThat(content, containsString(String.format("%d",course.getCode())));
        }

        @Test
        void testGetAllCourses() throws Exception {
                // given
                Course course = CourseMockData.mockCourse().builder().build();
                Course course1 = CourseMockData.mockCourse().builder().build();
                Course course2 = CourseMockData.mockCourse().builder().build();

                List<Course> courseList = new ArrayList<>();

                courseList.add(course);
                courseList.add(course1);
                courseList.add(course2);

                when(courseService.getAllCourses())
                        .thenReturn(courseList);

                // when then
                MvcResult result = mockMvc.perform(get("/api/v1/courses"))
                        .andExpect(status().is(200)).andReturn();

                String content = result.getResponse().getContentAsString();

                assertNotNull(content);
                assertThat(content, containsString(String.format("%d",courseList.get(0).getCode())));
        }

        @Test
        void testEditCourse() throws Exception {
                // given
                Course course = CourseMockData.mockCourse().builder().build();
                course.setCode(1L);
                course.setTitle("brand new title");
                when(courseService.editCourse(course.getCode(), course)).thenReturn(course);
                CourseDTO courseDTO  = mapper.map(course, CourseDTO.class);

                objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
                ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
                String requestJson = ow.writeValueAsString(courseDTO);
                // when then
                MvcResult result = mockMvc.perform(put("/api/v1/courses/1").contentType(APPLICATION_JSON_UTF8)
                                .content(requestJson))
                        .andExpect(status().is(200)).andReturn();

                String content = result.getResponse().getContentAsString();

                assertNotNull(content);
                assertThat(content, containsString("brand new title"));
        }

        @Test
        void testCreateCourse() throws Exception {
                // given
                Course course = CourseMockData.mockCourse().builder().build();
                when(courseService.createCourse(course)).thenReturn(course);
                CourseDTO courseDTO  = mapper.map(course, CourseDTO.class);
                objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
                ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
                String requestJson = ow.writeValueAsString(courseDTO);
                // when then
                MvcResult result = mockMvc.perform(post("/api/v1/courses").contentType(APPLICATION_JSON_UTF8)
                                .content(requestJson))
                        .andExpect(status().is(201)).andReturn();

                String content = result.getResponse().getContentAsString();

                assertNotNull(content);
                assertThat(content, containsString(String.format("%d",course.getCode())));
        }

        @Test
        void testDeleteCourse() throws Exception {
                // given
                Course course = CourseMockData.mockCourse().builder().build();
                course.setCode(1L);

                when(courseService.getCourseByCode(1L))
                        .thenReturn(course);

                // when then
                MvcResult result = mockMvc.perform(delete("/api/v1/courses/1"))
                        .andExpect(status().is(200)).andReturn();

                String content = result.getResponse().getContentAsString();

                assertNotNull(content);
                assertThat(content, containsString("deleted"));
        }

}
