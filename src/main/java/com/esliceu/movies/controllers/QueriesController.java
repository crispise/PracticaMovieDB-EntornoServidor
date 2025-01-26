package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Movie;
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
public class QueriesController {

    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieCastServices movieCastServices;
    @Autowired
    MovieCrewServices movieCrewServices;
    @Autowired
    GenreServices genreServices;

    @GetMapping("/moviesQuerys")
    public String getQuerie(HttpSession session, Model model) {
        String username = (String) session.getAttribute("user");
        if (username == null) model.addAttribute("logReg", true);
        return "consults";
    }

    @PostMapping("/moviesQuerys")
    public String setMovieQuery(HttpSession session, @RequestParam String actionSelect, Model model) {
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
        String username = (String) session.getAttribute("user");
        if (username == null) model.addAttribute("logReg", true);
        model.addAttribute("jsonInfo", jsonToSend);
        return "consults";
    }

    @GetMapping("/searchMovieByType/{actionSelect}")
    public String getMovieQ(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable("actionSelect") String actionSelect,
            @RequestParam String condition,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieServices.findMoviesByActionType(condition, actionSelect, pageable);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("searchType", true);
        return "consults";
    }

    @PostMapping("/searchMovieByType/{actionSelect}")
    public String getMovieQuery(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                @PathVariable("actionSelect") String actionSelect,
                                @RequestParam String condition, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieServices.findMoviesByActionType(condition, actionSelect, pageable);
        if (moviePage.isEmpty()) {
            model.addAttribute("errorMessage", "No se ha encontrado nada con ese nombre");
        } else {
            model.addAttribute("movies", moviePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", moviePage.getTotalPages());
            model.addAttribute("size", size);
            model.addAttribute("searchType", true);
        }
        return "consults";
    }

    @GetMapping("/allMovieQuerys")
    public String getAllMoviesQuerys(HttpSession session, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        String username = (String) session.getAttribute("user");
        if (username == null) model.addAttribute("logReg", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieServices.findAllMovies(pageable);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("allMovies", true);
        return "consults";
    }

    @GetMapping("/seeMovieInfo/{id}")
    public String seeMovieInfo(@PathVariable ("id") Integer movieId, Model model) {
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        return "movieInfo";
    }
}
