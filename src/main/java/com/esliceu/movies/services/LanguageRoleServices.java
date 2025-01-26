package com.esliceu.movies.services;


import com.esliceu.movies.models.Language;
import com.esliceu.movies.models.LanguageRole;
import com.esliceu.movies.models.Person;
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
    @Autowired
    PermissionsServices permissionsServices;

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

    public LanguageRole findLanguageRoleById(Integer id) {
        return languageRoleRepo.findById(id).get();
    }

    public List<LanguageRole> findLanguageRolesByName(String languageRoleSearch) {
        return languageRoleRepo.findLanguageRoleByLanguageRole(languageRoleSearch);
    }

    public String saveLanguageRole(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear roles del idioma");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El role del lenguaje no puede estar vacío.";
        if (languageRoleRepo.findLanguageRoleByLanguageRole(name).size() >= 1)
            return "Ya existe un rol con ese nombre.";
        LanguageRole languageRole = new LanguageRole();
        languageRole.setLanguageRole(name);
        languageRoleRepo.save(languageRole);
        return null;
    }


    public String deleteLanguageRole(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar roles del idioma");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            languageRoleRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar el rol del lenguaje";
        }

    }

    public String updateLanguageRole(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar roles del idioma");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<LanguageRole> sameName = languageRoleRepo.findLanguageRoleByLanguageRole(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<LanguageRole> existingLanguageRole = languageRoleRepo.findById(id);
        if (existingLanguageRole.isEmpty()) return "No existe esa rol";
        LanguageRole updatedLanguageRole = existingLanguageRole.get();
        updatedLanguageRole.setLanguageRole(name);
        languageRoleRepo.save(updatedLanguageRole);
        return null;
    }
}
