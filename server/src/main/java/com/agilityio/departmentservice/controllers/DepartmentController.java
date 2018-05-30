package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.repositories.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    /**
     * Default constructor.
     *
     * @param departmentRepository Department repository
     */
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Get all departments.
     *
     * @return List of departments in system
     */
    @GetMapping
    public ResponseEntity<List<Department>> find() {
        // TODO:: Implement
        return ResponseEntity.ok((List<Department>) new ArrayList<Department>());
    }

    /**
     * Get a single department.
     *
     * @param id Department id
     * @return Department
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Department>> findOne(@PathVariable(value = "id") String id) {
        // TODO:: Implement
        return ResponseEntity.ok(new ArrayList<Department>());
    }

    /**
     * Create a new department.
     *
     * @param department Department to create
     * @return The created department
     */
    @PostMapping
    public ResponseEntity<Department> create(@Valid @RequestBody Department department) {
        // TODO:: Implement
        return ResponseEntity.ok(department);
    }

    /**
     * Update a department.
     *
     * @param department Department
     * @return The updated department
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@Valid @RequestBody Department department) {
        // TODO:: Implement
        return ResponseEntity.ok(department);
    }

    /**
     * Delete a department.
     *
     * @param id Department id
     * @return Response ok if deleting success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        // TODO:: Implement
        return ResponseEntity.ok(ResponseEntity.ok());
    }
}
