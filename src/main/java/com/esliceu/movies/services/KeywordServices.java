package com.esliceu.movies.services;


import com.esliceu.movies.models.Keyword;
import com.esliceu.movies.models.Person;
import com.esliceu.movies.repos.KeywordRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeywordServices {
    @Autowired
    KeywordRepo keywordRepo;

    @Autowired
    PermissionsServices permissionsServices;

    public Page<Keyword> findAllKeywords(Pageable pageable) {
        return keywordRepo.findAll(pageable);
    }

    public String getKeywordJson() {
        List<Keyword> keywords = keywordRepo.findAll();
        List<String> names = keywords.stream()
                .map(p -> p.getKeywordName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public Keyword findKeywordById(Integer id) {
        return keywordRepo.findById(id).get();
    }

    public List<Keyword> findKeywordsByName(String keywordSearch) {
        return keywordRepo.findKeywordByKeywordName(keywordSearch);
    }

    public String saveKeyword(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear palabras clave");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El nombre de la keyword no puede estar vacío.";
        if (keywordRepo.findKeywordByKeywordName(name).size() >= 1) return "Ya existe una keyword con ese nombre.";
        Keyword keyword = new Keyword();
        keyword.setKeywordName(name);
        keywordRepo.save(keyword);
        return null;
    }

    public String deleteKeyword(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar palabras clave");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            keywordRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar la palabra clave";
        }

    }

    public String updateKeyword(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar palabras clave");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<Keyword> sameName = keywordRepo.findKeywordByKeywordName(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<Keyword> existingKeyword = keywordRepo.findById(id);
        if (existingKeyword.isEmpty()) return "No existe esa palabra clave";
        Keyword updatedKeyword = existingKeyword.get();
        updatedKeyword.setKeywordName(name);
        keywordRepo.save(updatedKeyword);
        return null;
    }
}
