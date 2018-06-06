package com.agilityio.departmentservice;

import com.agilityio.departmentservice.models.Department;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="department-service")
public interface DepartmentClient {

    /**
     * Get all departments.
     *
     * @return List of departments in system
     */
    @GetMapping
    ResponseEntity<List<Department>> find();

    /**
     * Get a single department.
     *
     * @param id Department id
     * @return Department
     */
    @GetMapping("/{id}")
    ResponseEntity<Department> findOne(@PathVariable(value = "id") String id);

    /**
     * Create a new department.
     *
     * @param department Department to create
     * @return The created department
     */
    @PostMapping
    ResponseEntity<Department> create(@Valid @RequestBody Department department);

    /**
     * Update a department.
     *
     * @param department Department
     * @return The updated department
     */
    @PutMapping("/{id}")
    ResponseEntity<Department> update(@PathVariable(value = "id") String id, @Valid @RequestBody Department department);

    /**
     * Delete a department.
     *
     * @param id Department id
     * @return Response ok if deleting success
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") String id);
}
