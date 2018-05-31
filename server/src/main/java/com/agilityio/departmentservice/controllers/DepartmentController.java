package com.agilityio.departmentservice.controllers;

import com.agilityio.departmentservice.exceptions.NotFoundResourceException;
import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.models.DepartmentInternal;
import com.agilityio.departmentservice.models.mappers.DepartmentMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/departments")
public class DepartmentController {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    /**
     * Default constructor.
     *
     * @param repository Department repository
     */
    public DepartmentController(DepartmentRepository repository, DepartmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Get all departments.
     *
     * @return List of departments in system
     */
    @GetMapping
    public ResponseEntity<List<Department>> find() {
        List<DepartmentInternal> departmentInternals = repository.findAll();

        if (departmentInternals == null || departmentInternals.isEmpty()) {
            throw new NotFoundResourceException();
        }

        List<Department> departments = departmentInternals.stream()
                .map(mapper::toDepartment)
                .collect(Collectors.toList());

        return ResponseEntity.ok(departments);
    }

    /**
     * Get a single department.
     *
     * @param id Department id
     * @return Department
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> findOne(@PathVariable(value = "id") String id) {
        DepartmentInternal dpInternal = repository.findOne(id);
        if (dpInternal == null) {
            throw new NotFoundResourceException();
        }

        return ResponseEntity.ok(mapper.toDepartment(dpInternal));
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
    public ResponseEntity<Department> update(@PathVariable(value = "id") String id,
                                             @Valid @RequestBody Department department) {
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
