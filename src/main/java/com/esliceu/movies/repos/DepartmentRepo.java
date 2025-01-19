package com.esliceu.movies.repos;

import com.esliceu.movies.models.Department;
import com.esliceu.movies.models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    List<Department> findDepartmentByDepartmentName(String departmentName);
}
