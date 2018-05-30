package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.DepartmentServiceApplication;
import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.repositories.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = DepartmentServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Clean database
        departmentRepository.deleteAll();
    }

    /**
     * Test create a new department failed by blank name
     */
    @Test
    public void testCreateDepartmentWithBlankNameShouldReturnBadRequest() throws Exception {
        // Test creating department failed by submitting a department with blank name
        Department department = new Department("");
        String json  = springMvcJacksonConverter.getObjectMapper().writeValueAsString(department);

        mockMvc.perform(post("/v1/departments")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void find() throws Exception {
        mockMvc.perform(get("/v1/departments")).andDo(print());
    }

    @Test
    public void findOne() {
    }
}