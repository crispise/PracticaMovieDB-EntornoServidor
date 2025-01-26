package com.esliceu.movies.services;


import com.esliceu.movies.models.Department;
import com.esliceu.movies.models.Person;
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

    @Autowired
    PermissionsServices permissionsServices;

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


    public Department findDeparmentById(Integer id) {
        return departmentRepo.findById(id).get();
    }

    public List<Department> findDeparmentsByName(String name) {
        return departmentRepo.findDepartmentByDepartmentName(name);
    }

    public String saveDepartment(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear departamentos");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El nombre del departamento no puede estar vacío.";
        if (departmentRepo.findDepartmentByDepartmentName(name).size() >= 1)
            return "Ya existe un departamento con ese nombre.";
        Department department = new Department();
        department.setDepartmentName(name);
        departmentRepo.save(department);
        return null;
    }


    public String deleteDepartment(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar departamentos");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            departmentRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar el departamento";
        }
    }

    public String updateDeparment(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar departamentos");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<Department> sameName = departmentRepo.findDepartmentByDepartmentName(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<Department> existingDepartment = departmentRepo.findById(id);
        if (existingDepartment.isEmpty()) return "No existe ese departamento";
        Department updatedDepartment = existingDepartment.get();
        updatedDepartment.setDepartmentName(name);
        departmentRepo.save(updatedDepartment);
        return null;
    }
}
