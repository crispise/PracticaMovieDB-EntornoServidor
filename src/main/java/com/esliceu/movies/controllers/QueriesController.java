package com.esliceu.movies.controllers;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.services.*;
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
public class QueriesController {

    @Autowired
    MovieServices movieServices;
    MovieCastServices movieCastServices;
    @Autowired
    MovieCrewServices movieCrewServices;
    @Autowired
    GenreServices genreServices;

    @GetMapping("/moviesQuerys")
    public String getQuerie() {
           return "consults";
    }

    @GetMapping("/moviesQuerys/{actionSelect}")
    public String setMovieQuery(@PathVariable("actionSelect") String actionSelect, Model model) {
        model.addAttribute("search", true);
        String jsonToSend = "";
        switch (actionSelect) {
            case "view-all":
                return "redirect:/allMovieQuerys";
            case "searchByTitle":
                model.addAttribute("searchByTitle", true);
                jsonToSend = movieServices.getMovieJson();
                break;
            case "searchByActor":
                model.addAttribute("searchByActor", true);
                jsonToSend = movieCastServices.getActorsJson();
                break;
            case "searchByCharacter":
                model.addAttribute("searchByCharacter", true);
                jsonToSend = movieCastServices.getCharactersJson();
                break;
            case "searchByGenre":
                model.addAttribute("searchByGenre", true);
                jsonToSend = genreServices.getGenreJson();
                break;
            case "searchByDirector":
                model.addAttribute("searchByDirector", true);
                jsonToSend = movieCrewServices.getDirectorJson();
                break;
        }
        model.addAttribute("jsonInfo", jsonToSend);
        return "consults";
    }

    @PostMapping("/moviesQuerys/{actionSelect}")
    public String getMovieQuery(@PathVariable("actionSelect") String actionSelect, @RequestParam String condition, RedirectAttributes redirectAttributes) {
        List<Movie> moviesFound = movieServices.findMoviesByActionType(condition,actionSelect);
        redirectAttributes.addFlashAttribute("moviesFound", moviesFound);
        return "redirect:/moviesQuerys/"+actionSelect;
    }

    @GetMapping("/allMovieQuerys")
    public String getAllMoviesQuerys(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieServices.findAllMovies(pageable);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("size", size);
        return "consults";
    }
}
