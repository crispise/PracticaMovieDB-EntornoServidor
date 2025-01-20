package com.esliceu.movies.controllers;


import com.esliceu.movies.models.LanguageRole;
import com.esliceu.movies.services.LanguageRoleServices;
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
public class LanguageRoleController {

    @Autowired
    LanguageRoleServices languageRoleServices;

    @GetMapping("/languageRoles")
    public String getLanguageRole() {
           return "languageRoles";
    }

    @PostMapping("/languageRoles")
    public String newLanguageRole(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allLanguageRoles";
            case "search-by-name":
                return "redirect:/searchLanguageRoles";
            case "create-new":
                return "redirect:/createLanguageRole";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "languageRoles";
    }

    @GetMapping("/allLanguageRoles")
    public String getAllLanguageRoles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<LanguageRole> languageRolePage = languageRoleServices.findAllLanguageRoles(pageable);
        model.addAttribute("languageRoles", languageRolePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languageRolePage.getTotalPages());
        model.addAttribute("size", size);
        return "languageRoles";
    }

    @GetMapping("/searchLanguageRoles")
    public String searchLanguageRoles(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = languageRoleServices.getLanguageRoleJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "languageRoles";
    }

    @PostMapping("/searchLanguageRoles")
    public String getLanguageRoleWanted(@RequestParam String languageRole, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = languageRoleServices.getLanguageRoleJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<LanguageRole> languageRolesFound = languageRoleServices.findLanguageRolesByName(languageRole);
        model.addAttribute("languageRolesFound", languageRolesFound);
        return "languageRoles";
    }



    @GetMapping("/createLanguageRole")
    public String createLanguageRole(Model model) {
        model.addAttribute("createNew", true);
        return "languageRoles";
    }


    @PostMapping("/createLanguageRole")
    public String saveLanguageRole(@RequestParam String languageRole, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =languageRoleServices.saveLanguageRole(languageRole);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Rol de lenguaje creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        return "languageRoles";
    }

    @PostMapping("/deleteLanguageRole")
    public String deleteLanguageRole(@RequestParam Integer roleId, @RequestParam(required = false) Integer currentPage, Model model){
        String message = languageRoleServices.deleteLanguageRole(roleId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "El rol del lenguaje se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar el rol del lenguaje");
        }
        if (currentPage != null){
            return "redirect:/allLanguageRoles?page=" + currentPage + "&size=" + 20;
        }else {
            return "languageRoles";
        }

    }

    @GetMapping("/updateLanguageRole/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LanguageRole languageRole = languageRoleServices.findLanguageRoleById(id);
        if (languageRole != null) {
            model.addAttribute("languageRole", languageRole);
            model.addAttribute("update", true);
            model.addAttribute("updateForm", true);
            return "languageRoles";
        } else {
            model.addAttribute("errorMessage", "El rol del lenguaje no fue encontrada");
            return "languageRoles";
        }
    }

   @PostMapping("/updateLanguageRole")
    public String updateLanguageRole(@RequestParam Integer roleId, @RequestParam String languageRole, Model model){
        LanguageRole languageRoleUpdate = languageRoleServices.updateLanguageRole(roleId, languageRole);
        model.addAttribute("languageRole", languageRoleUpdate);
        model.addAttribute("update", true);
        return "languageRoles";
   }


}
