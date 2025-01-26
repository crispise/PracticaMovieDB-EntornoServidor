package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Authorization;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.Permission;
import com.esliceu.movies.services.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthoritzationController {

    @Autowired
    PermissionsServices permissionsServices;

    @GetMapping("/permissions")
    public String seePermissions (HttpSession session, Model model) {
        String username = (String) session.getAttribute("user");
        String userType = permissionsServices.getUserType(username);
        if (userType.equals("ADMIN")){
            List<Authorization> authorizations = permissionsServices.getAuthoritzationsForAdmin();
            model.addAttribute("authoritzations", authorizations);
            return "adminPermissions";
        }
        List<Authorization> userAutho = permissionsServices.getUserAuthoritzations(username);
        List<Permission> restPermissions = permissionsServices.getPermissionsNotAssignedToUser(username);
        model.addAttribute("userAuthoritzations", userAutho);
        model.addAttribute("restPermissions", restPermissions);
        return "permissions";
    }

    @GetMapping("/acceptPermission/{id}")
    public String acceptPermissions (@PathVariable("id") Long authoritzationId, HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("user");
        String message = permissionsServices.handlePermission("accept", username, authoritzationId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "El permiso ha sido aceptado correctamente");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:/permissions";
    }

    @GetMapping("/rejectPermission/{id}")
    public String rejectPermission (@PathVariable("id") Long authoritzationId,HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("user");
        String message = permissionsServices.handlePermission("reject",username, authoritzationId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "El permiso ha sido rechazado correctamente");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:/permissions";
    }

    @GetMapping("/deletePermission/{id}")
    public String deletePermission (@PathVariable("id") Long authoritzationId,HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("user");
        String message = permissionsServices.handlePermission("delete", username, authoritzationId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "El permiso ha sido quitado correctamente");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:/permissions";
    }

    @GetMapping("/requestPermission/{id}")
    public String requestPermission (@PathVariable("id") Long permissionId, HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("user");
        String message = permissionsServices.requestPermission(username, permissionId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "El permiso ha sido solicitado correctamente");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:/permissions";
    }



}
