package com.esliceu.movies.controllers;


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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieCastServices movieCastServices;
    @Autowired
    MovieCrewServices movieCrewServices;
    @Autowired
    GenreServices genreServices;

    @GetMapping("/movies")
    public String getMovies() {
        return "movies";
    }

    @PostMapping("/movies")
    public String newMovie(@RequestParam String actionSelect, Model model) {
        switch (actionSelect) {
            case "view-all":
                return "redirect:/allMovies";
            case "create-new":
                return "redirect:/createMovie";
            default:
                return "redirect:/searchMovies/"+actionSelect;
        }
    }

    @GetMapping("/allMovies")
    public String getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieServices.findAllMovies(pageable);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("size", size);
        return "movies";
    }

    @GetMapping("/searchMovies/{actionSelect}")
    public String searchMovies(@PathVariable("actionSelect") String actionSelect, Model model) {
        model.addAttribute("search", true);
        String jsonToSend = "";
        switch (actionSelect) {
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
        return "movies";
    }

    @PostMapping("/searchMovies/{actionSelect}")
    public String getMovieWanted(@PathVariable("actionSelect") String actionSelect, @RequestParam String condition,RedirectAttributes redirectAttributes) {
        List<Movie> moviesFound = movieServices.findMoviesByActionType(condition,actionSelect);
        redirectAttributes.addFlashAttribute("moviesFound", moviesFound);
        return "redirect:/searchMovies/"+actionSelect;
    }

    @GetMapping("/createMovie")
    public String createPerson(Model model) {
        model.addAttribute("createNew", true);
        return "movies";
    }


    @PostMapping("/createMovie")
    public String saveMovie(@RequestParam String title,
                            @RequestParam Integer budget,
                            @RequestParam String homepage,
                            @RequestParam String overview,
                            @RequestParam BigDecimal popularity,
                            @RequestParam LocalDate releaseDate,
                            @RequestParam Long revenue,
                            @RequestParam Integer runtime,
                            @RequestParam String movieStatus,
                            @RequestParam String tagline,
                            @RequestParam BigDecimal voteAverage,
                            Model model) {
        model.addAttribute("createNew", true);
        String resultMessage = movieServices.saveMovie(title, budget, homepage, overview, popularity,
                releaseDate, revenue, runtime, movieStatus,
                tagline, voteAverage);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Película creada correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        return "movies";
    }

    @PostMapping("/deleteMovie")
    public String deleteMovie(@RequestParam Integer movieId, Model model) {
        String message = movieServices.deleteMovie(movieId);
        if (message.equals("Ok")) {
            model.addAttribute("successMessage", "La película se ha eliminado correctamente");
        } else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar la película");
        }
        return "movies";
    }

    @GetMapping("/updateMovie/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Movie movie = movieServices.findMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            model.addAttribute("update", true);
            return "movies";
        } else {
            model.addAttribute("errorMessage", "La persona no fue encontrada");
            return "movies";
        }
    }

    @PostMapping("/updateMovie/{movieId}")
    public String updateMovie(@RequestParam Integer movieId,
                              @RequestParam String title,
                              @RequestParam Integer budget,
                              @RequestParam String homepage,
                              @RequestParam String overview,
                              @RequestParam BigDecimal popularity,
                              @RequestParam LocalDate releaseDate,
                              @RequestParam Long revenue,
                              @RequestParam Integer runtime,
                              @RequestParam String movieStatus,
                              @RequestParam String tagline,
                              @RequestParam BigDecimal voteAverage, Model model) {
        Movie movieUpdate = movieServices.updateMovie(movieId, title, budget, homepage, overview, popularity,
                releaseDate, revenue, runtime, movieStatus,
                tagline, voteAverage);
        model.addAttribute("movie", movieUpdate);
        model.addAttribute("update", true);
        return "movies";
    }

    @PostMapping("/selectedEntity")
    public String selectedEntity(@RequestParam Integer movieId, @RequestParam String selectedEntity,  RedirectAttributes redirectAttributes) {
        switch (selectedEntity) {
            case "movieInfo":
                redirectAttributes.addFlashAttribute("updateForm", true);
                return "redirect:/updateMovie/" + movieId;
            case "movieCast":
                return "redirect:/movieCast/"+movieId;
            case "movieCrew":
                return "redirect:/movieCrew/"+movieId;
            case "movieCompany":
                return "redirect:movieCompany/"+movieId;
            case "movieKeyword":
                return "redirect:/movieKeyword/"+movieId;
            case "movieGenre":
                return "redirect:/movieGenre/"+movieId;
            case "movieLanguage":
                return "redirect:/movieLanguage/"+movieId;
            case "movieCountry":
                return "redirect:/productionCountry/"+movieId;
        }
        return "movies";
    }





}
