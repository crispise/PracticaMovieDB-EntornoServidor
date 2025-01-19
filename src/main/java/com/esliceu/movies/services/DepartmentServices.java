package com.esliceu.movies.services;


import com.esliceu.movies.models.Department;
import com.esliceu.movies.repos.DepartmentRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServices {
    @Autowired
    DepartmentRepo departmentRepo;

    public Page<Department> findAllDepartments(Pageable pageable) {
        return departmentRepo.findAll(pageable);
    }

    public String getDepartmentJson() {
        List<Department> departments = departmentRepo.findAll();
        List<String> names = departments.stream()
                .map(p -> p.getDepartmentName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String saveDepartment (String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre del departamento no puede estar vacío.";
        }
        if (departmentRepo.findDepartmentByDepartmentName(name).size() > 1) {
            return "Ya existe un departamento con ese nombre.";
        }
        Department department = new Department();
        department.setDepartmentName(name);
        departmentRepo.save(department);
        return null;
    }


    public Department findDeparmentById(Integer id) {
        return departmentRepo.findById(id).get();
    }

    public List<Department> findDeparmentsByName(String name) {
        return departmentRepo.findDepartmentByDepartmentName(name);
    }

    public String deleteDepartment(Integer id) {
        try {
            departmentRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public Department updateDeparment(Integer id, String name) {
        Optional<Department> existingDepartment = departmentRepo.findById(id);
        if (existingDepartment.isPresent()) {
           Department updatedDepartment = existingDepartment.get();
            updatedDepartment.setDepartmentName(name);
            departmentRepo.save(updatedDepartment);
            return updatedDepartment;
        } else {
            return null;
        }
    }

}
