package com.esliceu.movies.services;


import com.esliceu.movies.models.Language;
import com.esliceu.movies.models.LanguageRole;
import com.esliceu.movies.repos.LanguageRoleRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageRoleServices {
    @Autowired
    LanguageRoleRepo languageRoleRepo;

    public Page<LanguageRole> findAllLanguageRoles(Pageable pageable) {
        return languageRoleRepo.findAll(pageable);
    }

    public String getLanguageRoleJson() {
        List<LanguageRole> languageRoles = languageRoleRepo.findAll();
        List<String> names = languageRoles.stream()
                .map(p -> p.getLanguageRole())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String saveLanguageRole(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El role del lenguaje no puede estar vacío.";
        }
        if (languageRoleRepo.findLanguageRoleByLanguageRole(name).size() >= 1) {
            return "Ya existe un rol con ese nombre.";
        }
       LanguageRole languageRole = new LanguageRole();
        languageRole.setLanguageRole(name);
        languageRoleRepo.save(languageRole);
        return null;
    }


    public LanguageRole findLanguageRoleById(Integer id) {
        return languageRoleRepo.findById(id).get();
    }

    public List<LanguageRole> findLanguageRolesByName(String languageRoleSearch) {
        return languageRoleRepo.findLanguageRoleByLanguageRole(languageRoleSearch);
    }

    public String deleteLanguageRole(Integer id) {
        try {
            languageRoleRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public LanguageRole updateLanguageRole(Integer id, String name) {
        Optional<LanguageRole> existingLanguageRole = languageRoleRepo.findById(id);
        if (existingLanguageRole.isPresent()) {
            LanguageRole updatedLanguageRole = existingLanguageRole.get();
            updatedLanguageRole.setLanguageRole(name);
            languageRoleRepo.save(updatedLanguageRole);
            return updatedLanguageRole;
        } else {
            return null;
        }
    }

}
