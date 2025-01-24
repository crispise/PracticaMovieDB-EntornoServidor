package com.esliceu.movies.controllers;

import com.esliceu.movies.models.*;
import com.esliceu.movies.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MovieKeywordController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieKeywordsServices movieKeywordsServices;
    @Autowired
    KeywordServices keywordServices;

    @GetMapping("/movieKeyword/{movieId}")
    public String getMovieKeyword(@PathVariable("movieId") Integer movieId, Model model) {
        String jsonToSend = keywordServices.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieKeywords> movieKeywords = movieKeywordsServices.getMovieKeywords(movie);
        model.addAttribute("movieKeywords", movieKeywords);
        return "movieKeywords";
    }

    @PostMapping("/addMovieKeyword")
    public String addMovieKeyword(@RequestParam Integer movieId,
                                  @RequestParam String keywordName,
                                  RedirectAttributes redirectAttributes) {
        String message = movieKeywordsServices.addMovieKeyword(keywordName, movieId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡La clave se ha añadido correctamente!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieKeyword/" + movieId;
    }

    @PostMapping("/deleteMovieKeyword")
    public String deleteMovieCompany(@RequestParam Integer movieId,
                                     @RequestParam Integer keywordId,
                                     RedirectAttributes redirectAttributes) {
        String message = movieKeywordsServices.deleteMovieKeyword(movieId, keywordId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡La palabra clave se ha eliminado correctamente!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieKeyword/" + movieId;
    }
}
