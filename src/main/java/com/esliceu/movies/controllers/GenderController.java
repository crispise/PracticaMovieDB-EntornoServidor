package com.esliceu.movies.controllers;


import com.esliceu.movies.models.Gender;
import com.esliceu.movies.services.GenderServices;
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
public class GenderController {

    @Autowired
    GenderServices genderServices;

    @GetMapping("/genders")
    public String getGenders() {
           return "genders";
    }

    @PostMapping("/genders")
    public String newPerson(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allGenders";
            case "search-by-name":
                return "redirect:/searchGenders";
            case "create-new":
                return "redirect:/createGender";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "genders";
    }

    @GetMapping("/allGenders")
    public String getAllGenders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Gender> genderPages = genderServices.findAllGenders(pageable);
        model.addAttribute("genders", genderPages.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genderPages.getTotalPages());
        model.addAttribute("size", size);
        return "genders";
    }

    @GetMapping("/searchGenders")
    public String searchGenders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = genderServices.getGenderJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "genders";
    }

    @PostMapping("/searchGenders")
    public String getGenderWanted(@RequestParam String gender, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = genderServices.getGenderJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Gender> genderFound = genderServices.findGenderByName(gender);
        model.addAttribute("genderFound", genderFound);
        return "genders";
    }


    @GetMapping("/createGender")
    public String createGender(Model model) {
        model.addAttribute("createNew", true);
        return "genders";
    }


    @PostMapping("/createGender")
    public String saveGender(@RequestParam String gender, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =genderServices.saveGender(gender);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Género creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        return "genders";
    }

    @PostMapping("/deleteGender")
    public String deleteGender(@RequestParam Integer genderId, @RequestParam(required = false) Integer currentPage, Model model){
        String message = genderServices.deleteGender(genderId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "El género se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar el género");
        }
        if (currentPage != null){
            return "redirect:/allGenders?page=" + currentPage + "&size=" + 20;
        }else {
            return "genders";
        }

    }

    @GetMapping("/updateGender/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
       Gender gender = genderServices.findGenderById(id);
        if (gender != null) {
            model.addAttribute("gender", gender);
            model.addAttribute("update", true);
            model.addAttribute("updateForm", true);
            return "genders";
        } else {
            model.addAttribute("errorMessage", "EL género no fue encontrado");
            return "genders";
        }
    }

   @PostMapping("/updateGender")
    public String updateGender(@RequestParam Integer genderId, @RequestParam String gender, Model model){
        Gender genderUpdate = genderServices.updateGender(genderId, gender);
        model.addAttribute("gender", genderUpdate);
        model.addAttribute("update", true);
        return "genders";
   }


}
