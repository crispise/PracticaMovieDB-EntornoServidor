package com.esliceu.movies.controllers;
import com.esliceu.movies.models.Language;
import com.esliceu.movies.services.LanguageServices;
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
public class LanguageController {

    @Autowired
    LanguageServices languageServices;

    @GetMapping("/languages")
    public String getLanguages() {
           return "languages";
    }

    @PostMapping("/languages")
    public String newLanguage(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allLanguages";
            case "search-by-name":
                return "redirect:/searchLanguages";
            case "create-new":
                return "redirect:/createLanguage";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "languages";
    }

    @GetMapping("/allLanguages")
    public String getAllLanguage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Language> languagePage = languageServices.findAllLanguages(pageable);
        model.addAttribute("languages", languagePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languagePage.getTotalPages());
        model.addAttribute("size", size);
        return "languages";
    }

    @GetMapping("/searchLanguages")
    public String searchLanguages(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = languageServices.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "languages";
    }

    @PostMapping("/searchLanguages")
    public String getLanguageWanted(@RequestParam String languageName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = languageServices.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Language> languagesFound = languageServices.findLanguageByName(languageName);
        model.addAttribute("languagesFound", languagesFound);
        return "languages";
    }



    @GetMapping("/createLanguage")
    public String createLanguage(Model model) {
        model.addAttribute("createNew", true);
        return "languages";
    }


    @PostMapping("/createLanguage")
    public String saveLanguage(@RequestParam String languageName, @RequestParam String languageCode, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =languageServices.saveLanguage(languageName, languageCode);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Lenguaje creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        return "languages";
    }

    @PostMapping("/deleteLanguage")
    public String deleteLanguage(@RequestParam Integer languageId, Model model){
        String message = languageServices.deleteLanguage(languageId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "El lenguaje se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar el lenguaje del lenguaje");
        }

            return "languages";


    }

    @GetMapping("/updateLanguage/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Language language = languageServices.findLanguageById(id);
        if (language != null) {
            model.addAttribute("language", language);
            model.addAttribute("update", true);
            return "languages";
        } else {
            model.addAttribute("errorMessage", "El lenguaje no fue encontrada");
            return "languages";
        }
    }

   @PostMapping("/updateLanguage/{languageId}")
    public String updateLanguage(@RequestParam Integer languageId, @RequestParam String languageName,
                                 @RequestParam String languageCode, Model model){
        Language languageUpdate = languageServices.updateLanguage(languageId, languageName,languageCode );
        model.addAttribute("language", languageUpdate);
        model.addAttribute("update", true);
        return "languages";
   }


}
