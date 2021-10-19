package com.example.s4Example.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.example.s4Example.CourseMockData;
import com.example.s4Example.StudentMockData;
import com.example.s4Example.dto.CourseDTO;
import com.example.s4Example.dto.StudentDTO;
import com.example.s4Example.model.Course;
import com.example.s4Example.model.Student;
import com.example.s4Example.service.impl.StudentServiceImpl;
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
public class StudentControllerITest {

    public MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentServiceImpl studentService;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void testGetStudent() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        student.setId(1L);

        when(studentService.getStudentById(1L))
                .thenReturn(student);

        // when then
        MvcResult result = mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().is(200)).andReturn();
        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content, containsString(String.format("%d",student.getId())));
    }

    @Test
    void testGetAllStudentss() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        Student student1 = StudentMockData.mockStudent().builder().build();
        Student student2 = StudentMockData.mockStudent().builder().build();

        List<Student> studentList = new ArrayList<>();

        studentList.add(student);
        studentList.add(student1);
        studentList.add(student2);

        when(studentService.getAllStudents())
                .thenReturn(studentList);

        // when then
        MvcResult result = mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().is(200)).andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content, containsString(String.format("%d",studentList.get(0).getId())));
    }

    @Test
    void testEditStudent() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        student.setId(1L);
        student.setFirstName("brand new name");
        when(studentService.editStudent(student.getId(), student)).thenReturn(student);
        StudentDTO studentDTO  = mapper.map(student, StudentDTO.class);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(studentDTO);
        // when then
        MvcResult result = mockMvc.perform(put("/api/v1/students/1").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().is(200)).andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content, containsString("brand new name"));
    }

    @Test
    void testCreateStudent() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        when(studentService.createStudent(student)).thenReturn(student);
        StudentDTO studentDTO  = mapper.map(student, StudentDTO.class);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(studentDTO);
        // when then
        MvcResult result = mockMvc.perform(post("/api/v1/students").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().is(201)).andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content, containsString(String.format("%d",student.getId())));
    }

    @Test
    void testDeleteStudent() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        student.setId(1L);

        when(studentService.getStudentById(1L))
                .thenReturn(student);

        // when then
        MvcResult result = mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().is(200)).andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content, containsString("deleted"));
    }



}
