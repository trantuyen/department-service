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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        DepartmentInternal created = createDepartmentInternal("640 Nui Thanh", faker.phoneNumber().phoneNumber());

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
     * Test deleting department success.
     */
    @Test
    public void testDeleteSuccess() throws Exception {
        // Create new department
        DepartmentInternal created = createDepartmentInternal("New department", faker.phoneNumber().phoneNumber());

        // Verify created department
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());

        mockMvc.perform(delete(baseUrlTemplate + "/" + created.getId())
                .content(getJson(created))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test find all departments success.
     */
    @Test
    public void testFindAllDepartmentSuccess() throws Exception {
        // Create new department
        DepartmentInternal created = createDepartmentInternal("Test Find All Departments",
                faker.phoneNumber().phoneNumber());

        // Verify created department
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());

        // Perform get all departments
        mockMvc.perform(get(baseUrlTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Test get a department by id not found.
     */
    @Test
    public void testFindOneFailedByNotFound() throws Exception {
        String invalidId = "invalid";
        mockMvc.perform(get(baseUrlTemplate + "/" + invalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test get a department by id success.
     */
    @Test
    public void testFindOneSuccess() throws Exception {
        // Create new department
        String name = "The One";
        String phoneNumber = faker.phoneNumber().phoneNumber();
        DepartmentInternal created = createDepartmentInternal(name, phoneNumber);

        mockMvc.perform(get(baseUrlTemplate + "/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
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
    private DepartmentInternal createDepartmentInternal(String name, String phoneNumber) {
        DepartmentInternal dpInternal = new DepartmentInternal();
        dpInternal.setName(name);
        dpInternal.setPhoneNumber(phoneNumber);

        return departmentRepository.save(dpInternal);
    }
}