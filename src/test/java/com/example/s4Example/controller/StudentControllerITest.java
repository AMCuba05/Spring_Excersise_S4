package com.example.s4Example.controller;

import java.nio.charset.Charset;
import com.example.s4Example.StudentMockData;
import com.example.s4Example.dto.StudentDTO;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().is(200));
    }

    @Test
    void testCreateStudent() throws Exception {
        // given
        Student student = StudentMockData.mockStudent().builder().build();
        StudentDTO studentDTO  = mapper.map(student, StudentDTO.class);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(studentDTO);
        // when then
        mockMvc.perform(post("/api/v1/students").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().is(204));
    }



}
