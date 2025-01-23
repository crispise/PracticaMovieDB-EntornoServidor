package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Keyword;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Keyword> movieKeywords = movieKeywordsServices.getMovieKeywords(movie);
        System.out.println(movieKeywords);
        model.addAttribute("movieKeywords", movieKeywords);
        return "movieKeywords";
    }

    @PostMapping("/addMovieKeyword")
    public String addMovieKeyword(@RequestParam Integer movieId,
                                  @RequestParam String keywordName, Model model) {
        String jsonToSend = keywordServices.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<Keyword> movieKeywords = movieKeywordsServices.getMovieKeywords(movie);
        model.addAttribute("movieKeywords", movieKeywords);

        String message = movieKeywordsServices.addMovieKeyword(keywordName, movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡La clave se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieKeyword/"+movieId;
    }

    /*@PostMapping("/deleteMovieKeyword")
    public String deleteMovieKeyword(@RequestParam Integer movieId,
                                  @RequestParam Integer keywordId, Model model) {
        String jsonToSend = keywordServices.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<Keyword> movieKeywords = movieKeywordsServices.getMovieKeywords(movie);
        model.addAttribute("movieKeywords", movieKeywords);

        String message = movieKeywordsServices.deleteMovieKey(keywordId, movieId);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }*/

}
