package com.esliceu.movies.services;


import com.esliceu.movies.models.Genre;
import com.esliceu.movies.repos.GenreRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServices {
    @Autowired
    GenreRepo genreRepo;

    public Page<Genre> findAllGenres(Pageable pageable) {
        return genreRepo.findAll(pageable);
    }

    public String getGenreJson() {
        List<Genre> genres = genreRepo.findAll();
        List<String> names = genres.stream()
                .map(p -> p.getGenreName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String saveGenre(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre del género no puede estar vacío.";
        }
        if (genreRepo.findGenreByGenreName(name).size() > 1) {
            return "Ya existe un género con ese nombre.";
        }
        Genre genre = new Genre();
        genre.setGenreName(name);
        genreRepo.save(genre);
        return null;
    }


    public Genre findGenreById(Integer id) {
        return genreRepo.findById(id).get();
    }

    public List<Genre> findGenresByName(String genreSearch) {
        return genreRepo.findGenreByGenreName(genreSearch);
    }

    public String deleteGenre(Integer id) {
        try {
            genreRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public Genre updateGenre(Integer id, String name) {
        Optional<Genre> existingGenre = genreRepo.findById(id);
        if (existingGenre.isPresent()) {
           Genre updatedGenre = existingGenre.get();
            updatedGenre.setGenreName(name);
            genreRepo.save(updatedGenre);
            return updatedGenre;
        } else {
            return null;
        }
    }

}
