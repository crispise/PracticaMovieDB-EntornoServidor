package com.esliceu.movies.controllers;


import com.esliceu.movies.models.Department;
import com.esliceu.movies.services.DepartmentServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentServices departmentServices;

    @GetMapping("/departments")
    public String getDepartments() {
        return "departments";
    }

    @PostMapping("/departments")
    public String newDepartment(@RequestParam String actionSelect, Model model) {
        switch (actionSelect) {
            case "view-all":
                return "redirect:/allDepartments";
            case "search-by-name":
                return "redirect:/searchDepartments";
            case "create-new":
                return "redirect:/createDepartment";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "departments";
    }

    @GetMapping("/allDepartments")
    public String getAllDepartments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Department> departmentPage = departmentServices.findAllDepartments(pageable);
        model.addAttribute("departments", departmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("size", size);
        return "departments";
    }

    @GetMapping("/searchDepartments")
    public String searchDepartments(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = departmentServices.getDepartmentJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "departments";
    }

    @PostMapping("/searchDepartments")
    public String getDepartmentWanted(@RequestParam String departmentName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = departmentServices.getDepartmentJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Department> departmentFound = departmentServices.findDeparmentsByName(departmentName);
        model.addAttribute("departmentFound", departmentFound);
        return "departments";
    }


    @GetMapping("/createDepartment")
    public String createDeparment(Model model) {
        model.addAttribute("createNew", true);
        return "departments";
    }


    @PostMapping("/createDepartment")
    public String saveDepartment(HttpSession session, @RequestParam String departmentName, Model model) {
        String username = (String) session.getAttribute("user");
        String resultMessage = departmentServices.saveDepartment(departmentName, username);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Departamento creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        model.addAttribute("createNew", true);
        return "departments";
    }

    @PostMapping("/deleteDepartment")
    public String deleteDepartment(HttpSession session, @RequestParam Integer departmentId, Model model) {
        String username = (String) session.getAttribute("user");
        String message = departmentServices.deleteDepartment(departmentId, username);
        if (message == null) {
            model.addAttribute("successMessage", "El departamento se ha eliminado correctamente");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "departments";
    }

    @GetMapping("/updateDepartment/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Department department = departmentServices.findDeparmentById(id);
        if (department != null) {
            model.addAttribute("department", department);
            model.addAttribute("update", true);
            return "departments";
        } else {
            model.addAttribute("errorMessage", "El departamento no fue encontrado");
            return "departments";
        }
    }

    @PostMapping("/updateDepartment/{departmentId}")
    public String updateDepartment(HttpSession session, @RequestParam Integer departmentId, @RequestParam String departmentName, Model model) {
        Department departmentUpdate = departmentServices.findDeparmentById(departmentId);
        String username = (String) session.getAttribute("user");
        String message = departmentServices.updateDeparment(departmentId, departmentName, username);
        if (message == null) {
            model.addAttribute("successMessage", "¡Departamento actualizado correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        model.addAttribute("department", departmentUpdate);
        model.addAttribute("update", true);
        return "departments";
    }


}
