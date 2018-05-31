package com.agilityio.departmentservice.models.mappers;

import com.agilityio.departmentservice.models.Department;
import com.agilityio.departmentservice.models.DepartmentInternal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toDepartment(DepartmentInternal departmentInternal);

    @Mapping(target = "id", ignore = true)
    DepartmentInternal toDepartmentInternal(Department department);

    @Mapping(target = "id", ignore = true)
    void update(Department department, @MappingTarget DepartmentInternal departmentInternal);
}
