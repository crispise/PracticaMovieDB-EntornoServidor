package com.esliceu.movies.controllers;


import com.esliceu.movies.models.Keyword;
import com.esliceu.movies.services.KeywordServices;
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
public class KeywordController {

    @Autowired
    KeywordServices keywordServices;

    @GetMapping("/keywords")
    public String getKeywords() {
        return "keywords";
    }

    @PostMapping("/keywords")
    public String newKeyword(@RequestParam String actionSelect, Model model) {
        switch (actionSelect) {
            case "view-all":
                return "redirect:/allKeywords";
            case "search-by-name":
                return "redirect:/searchKeywords";
            case "create-new":
                return "redirect:/createKeyword";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "keywords";
    }

    @GetMapping("/allKeywords")
    public String getAllKeywords(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Keyword> keywordPage = keywordServices.findAllKeywords(pageable);
        model.addAttribute("keywords", keywordPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", keywordPage.getTotalPages());
        model.addAttribute("size", size);
        return "keywords";
    }

    @GetMapping("/searchKeywords")
    public String searchKeywords(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = keywordServices.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "keywords";
    }

    @PostMapping("/searchKeywords")
    public String getKeywordWanted(@RequestParam String keywordName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = keywordServices.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Keyword> keywordsFound = keywordServices.findKeywordsByName(keywordName);
        model.addAttribute("keywordsFound", keywordsFound);
        return "keywords";
    }


    @GetMapping("/createKeyword")
    public String createKeyword(Model model) {
        model.addAttribute("createNew", true);
        return "keywords";
    }


    @PostMapping("/createKeyword")
    public String saveKeyword(HttpSession session, @RequestParam String keywordName, Model model) {
        String username = (String) session.getAttribute("user");
        String resultMessage = keywordServices.saveKeyword(keywordName, username);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Keyword creada correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        model.addAttribute("createNew", true);

        return "keywords";
    }

    @PostMapping("/deleteKeyword")
    public String deleteKeyword(HttpSession session,@RequestParam Integer keywordId, Model model) {
        String username = (String) session.getAttribute("user");
        String message = keywordServices.deleteKeyword(keywordId, username);
        if (message == null) {
            model.addAttribute("successMessage", "La keyword se ha eliminado correctamente");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "keywords";
    }

    @GetMapping("/updateKeyword/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Keyword keyword = keywordServices.findKeywordById(id);
        if (keyword != null) {
            model.addAttribute("keyword", keyword);
            model.addAttribute("update", true);
            return "keywords";
        } else {
            model.addAttribute("errorMessage", "La keyword no fue encontrada");
            return "keywords";
        }
    }

    @PostMapping("/updateKeyword/{keywordId}")
    public String updateKeyword(HttpSession session,@RequestParam Integer keywordId, @RequestParam String keywordName, Model model) {
        Keyword keywordUpdate = keywordServices.findKeywordById(keywordId);
        String username = (String) session.getAttribute("user");
        String message =  keywordServices.updateKeyword(keywordId, keywordName, username);
        if (message == null) {
            model.addAttribute("successMessage", "¡Palabra clave actualizada correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        model.addAttribute("keyword", keywordUpdate);
        model.addAttribute("update", true);
        return "keywords";
    }


}
