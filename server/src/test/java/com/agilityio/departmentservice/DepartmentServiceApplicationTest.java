package com.agilityio.departmentservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableFeignClients(clients = { DepartmentClient.class })
public class DepartmentServiceApplicationTest {

    @Test
    public void contextLoads() {
    }
}