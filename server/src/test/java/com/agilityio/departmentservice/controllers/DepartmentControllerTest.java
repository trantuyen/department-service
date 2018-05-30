package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.DepartmentServiceApplication;
import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.models.DepartmentInternal;
import com.agilityio.departmentservice.repositories.DepartmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
     * Test create a new department failed by missing body
     */
    @Test
    public void testCreateDepartmentWithNoBoShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post(baseUrlTemplate).content("").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test create a new department failed by blank name
     */
    @Test
    public void testCreateDepartmentWithBlankNameShouldReturnBadRequest() throws Exception {
        // Test creating department failed by submitting a department with blank name
        Department department = Department.builder().name("").build();
        mockMvc.perform(post(baseUrlTemplate).content(getJson(department)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test create a new department failed by blank phone number
     */
    @Test
    public void testCreateDepartmentWithBlankPhoneShouldReturnBadRequest() throws Exception {
        Department department = Department.builder().name("LightHouse").phoneNumber("").build();
        mockMvc.perform(post(baseUrlTemplate).content(getJson(department)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test update failed by not found the give department in the system
     */
    @Test
    public void testUpdateFailedByNotFoundDepartment() throws Exception {
        String invalidId = "invalid";

        Department department = Department.builder().name("LightHouse").phoneNumber(faker.phoneNumber().phoneNumber()).build();
        department.setId(invalidId);

        mockMvc.perform(put(baseUrlTemplate + "/" + invalidId)
                .content(getJson(department))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test create a new department success
     */
    @Test
    public void testCreateSuccess() throws Exception {
        String dpName = "LightHouse";
        Department department = Department.builder().name(dpName).phoneNumber(faker.phoneNumber().phoneNumber()).build();
        mockMvc.perform(post(baseUrlTemplate).content(getJson(department)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dpName));
    }

    /**
     * Test update success
     */
    @Test
    public void testUpdateSuccess() throws Exception {
        // Create new department
        DepartmentInternal created = createDepartmentInternal();

        // Verify created department
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());

        // Set a new name, then update it
        String newName = "Agilityio";
        created.setName(newName);
        mockMvc.perform(put(baseUrlTemplate + "/" + created.getId())
                .content(getJson(created))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName));
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

    /**
     * Create an internal department.
     *
     * @return DepartmentInternal
     */
    private DepartmentInternal createDepartmentInternal() {
        DepartmentInternal dpInternal = new DepartmentInternal();
        dpInternal.setName("LightHouse");
        dpInternal.setPhoneNumber(faker.phoneNumber().phoneNumber());

        return departmentRepository.save(dpInternal);
    }
}