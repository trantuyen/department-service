package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.DepartmentServiceApplication;
import com.agilityio.departmentservice.repositories.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = DepartmentServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DepartmentRepository departmentRepository;

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
    public void testCreateFailedByBlankName() {

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void find() {
    }

    @Test
    public void findOne() {
    }
}