package com.agilityio.departmentservice.repositories;

import com.agilityio.departmentservice.models.DepartmentInternal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<DepartmentInternal, String> {
}
