package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Genre;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieGenres;
import com.esliceu.movies.models.MovieKeywords;
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
public class MovieGenresController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieGenresServices movieGenresServices;
    @Autowired
    GenreServices genreServices;

    @GetMapping("/movieGenre/{movieId}")
    public String getMovieGenre(@PathVariable("movieId") Integer movieId, Model model) {
        String jsonToSend = genreServices.getGenreJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieGenres> movieGenres = movieGenresServices.getMovieGenres(movie);
        model.addAttribute("movieGenres", movieGenres);
        return "movieGenres";
    }

    @PostMapping("/addMovieGenre")
    public String addMovieGenre(@RequestParam Integer movieId,
                                @RequestParam String genreName,
                                RedirectAttributes redirectAttributes) {
        String message = movieGenresServices.addMovieGenre(genreName, movieId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡El género se ha añadido correctamente!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieGenre/"+ movieId;
    }

    @PostMapping("/deleteMovieGenre")
    public String deleteMovieGenre(@RequestParam Integer movieId,
                                   @RequestParam Integer genreId,
                                   RedirectAttributes redirectAttributes) {
        String message = movieGenresServices.deleteMovieGenre(movieId, genreId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡El género se ha eliminado correctamente!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieGenre/"+movieId;
    }



}
