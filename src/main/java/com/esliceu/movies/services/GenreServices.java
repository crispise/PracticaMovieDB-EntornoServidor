package com.esliceu.movies.services;


import com.esliceu.movies.models.Genre;
import com.esliceu.movies.models.Person;
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
    @Autowired
    PermissionsServices permissionsServices;

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

    public Genre findGenreById(Integer id) {
        return genreRepo.findById(id).get();
    }

    public List<Genre> findGenresByName(String genreSearch) {
        return genreRepo.findGenreByGenreName(genreSearch);
    }

    public String saveGenre(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear géneros de película");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El nombre del género no puede estar vacío.";
        if (genreRepo.findGenreByGenreName(name).size() >= 1) return "Ya existe un género con ese nombre.";
        Genre genre = new Genre();
        genre.setGenreName(name);
        genreRepo.save(genre);
        return null;
    }


    public String deleteGenre(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar géneros de película");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            genreRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar el género";
        }

    }

    public String updateGenre(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar géneros de película");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<Genre> sameName = genreRepo.findGenreByGenreName(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<Genre> existingGenre = genreRepo.findById(id);
        if (existingGenre.isEmpty()) return "No existe ese género";
        Genre updatedGenre = existingGenre.get();
        updatedGenre.setGenreName(name);
        genreRepo.save(updatedGenre);
        return null;
    }
}


