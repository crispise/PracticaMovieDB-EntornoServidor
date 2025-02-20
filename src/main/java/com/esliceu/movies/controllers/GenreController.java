package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Genre;
import com.esliceu.movies.services.GenreServices;
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
public class GenreController {

    @Autowired
    GenreServices genreServices;

    @GetMapping("/genres")
    public String getGenres() {
        return "genres";
    }

    @PostMapping("/genres")
    public String newGenre(@RequestParam String actionSelect, Model model) {
        switch (actionSelect) {
            case "view-all":
                return "redirect:/allGenres";
            case "search-by-name":
                return "redirect:/searchGenres";
            case "create-new":
                return "redirect:/createGenre";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "genres";
    }

    @GetMapping("/allGenres")
    public String getAllGenres(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genrePage = genreServices.findAllGenres(pageable);
        model.addAttribute("genres", genrePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genrePage.getTotalPages());
        model.addAttribute("size", size);
        return "genres";
    }

    @GetMapping("/searchGenres")
    public String searchGenres(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = genreServices.getGenreJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "genres";
    }

    @PostMapping("/searchGenres")
    public String getGenreWanted(@RequestParam String genreName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = genreServices.getGenreJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Genre> genresFound = genreServices.findGenresByName(genreName);
        model.addAttribute("genresFound", genresFound);
        return "genres";
    }


    @GetMapping("/createGenre")
    public String createGenre(Model model) {
        model.addAttribute("createNew", true);
        return "genres";
    }


    @PostMapping("/createGenre")
    public String saveGenre(HttpSession session, @RequestParam String genreName, Model model) {
        String username = (String) session.getAttribute("user");
        String resultMessage = genreServices.saveGenre(genreName, username);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Género creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        model.addAttribute("createNew", true);
        return "genres";
    }

    @PostMapping("/deleteGenre")
    public String deleteGenre(HttpSession session,@RequestParam Integer genreId, Model model) {
        String username = (String) session.getAttribute("user");
        String message = genreServices.deleteGenre(genreId, username);
        if (message == null) {
            model.addAttribute("successMessage", "El género se ha eliminado correctamente");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "genres";
    }

    @GetMapping("/updateGenre/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Genre genre = genreServices.findGenreById(id);
        if (genre != null) {
            model.addAttribute("genre", genre);
            model.addAttribute("update", true);
            return "genres";
        } else {
            model.addAttribute("errorMessage", "El género no fue encontrado");
            return "genres";
        }
    }

    @PostMapping("/updateGenre/{genreId}")
    public String updateGenre(HttpSession session,@RequestParam Integer genreId, @RequestParam String genreName, Model model) {
        Genre genreUpdate = genreServices.findGenreById(genreId);
        String username = (String) session.getAttribute("user");
        String message = genreServices.updateGenre(genreId, genreName, username);
        if (message == null) {
            model.addAttribute("successMessage", "¡Género actualizado correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        model.addAttribute("genre", genreUpdate);
        model.addAttribute("update", true);
        return "genres";
    }
}
