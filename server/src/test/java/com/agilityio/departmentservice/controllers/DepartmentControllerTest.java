package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.DepartmentServiceApplication;
import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.repositories.DepartmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
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

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = DepartmentServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    protected static final Faker faker = new Faker(new Locale("en-US"));

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    protected MockMvc mockMvc;

    private String baseUrlTemplate = "/v1/departments";

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
        String json  = getJson(department);

        mockMvc.perform(post(baseUrlTemplate).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test create a new department failed by blank phoneNumber number
     */
    @Test
    public void testCreateDepartmentWithBlankPhoneShouldReturnBadRequest() throws Exception {
        Department department = new Department("LightHouse", "");
        String json  = getJson(department);

        mockMvc.perform(post(baseUrlTemplate).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test create a new department success
     */
    @Test
    public void testCreateSuccess() throws Exception {
        Department department = new Department("LightHouse", faker.phoneNumber().toString());
        String json  = getJson(department);

        mockMvc.perform(post(baseUrlTemplate).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testGetAllDepartmentSuccess() throws Exception {
        mockMvc.perform(get(baseUrlTemplate))
                .andExpect(status().isOk());
    }

    @Test
    public void findOne() {
    }

    /**
     * Get department as json string.
     *
     * @param department Department instance
     * @return Json object
     * @throws JsonProcessingException Can not parse the given object
     */
    private String getJson(Department department) throws JsonProcessingException {
        return springMvcJacksonConverter.getObjectMapper().writeValueAsString(department);
    }
}