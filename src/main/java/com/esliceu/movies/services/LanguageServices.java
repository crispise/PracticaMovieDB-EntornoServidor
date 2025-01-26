package com.esliceu.movies.services;

import com.esliceu.movies.models.Language;
import com.esliceu.movies.models.Person;
import com.esliceu.movies.repos.LanguageRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageServices {
    @Autowired
    LanguageRepo languageRepo;
    @Autowired
    PermissionsServices permissionsServices;

    public Page<Language> findAllLanguages(Pageable pageable) {
        return languageRepo.findAll(pageable);
    }

    public String getLanguageJson() {
        List<Language> languages = languageRepo.findAll();
        List<String> names = languages.stream()
                .map(l -> l.getLanguageName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public Language findLanguageById(Integer id) {
        return languageRepo.findById(id).get();
    }

    public List<Language> findLanguageByName(String languageSearch) {
        return languageRepo.findLanguageByLanguageName(languageSearch);
    }

    public String saveLanguage(String name, String code, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear idiomas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El lenguaje no puede estar vacío.";
        if (code == null || code.trim().isEmpty()) return "El código no puede estar vacío.";
        if (languageRepo.findLanguageByLanguageName(name).size() >= 1) return "Ya existe un lenguaje con ese nombre.";
        Language language = new Language();
        language.setLanguageName(name);
        language.setLanguageCode(code);
        languageRepo.save(language);
        return null;
    }


    public String deleteLanguage(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar idiomas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            languageRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar el idioma";
        }
    }

    public String updateLanguage(Integer id, String name, String code, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar idiomas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<Language> sameName = languageRepo.findLanguageByLanguageName(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<Language> existingLanguage = languageRepo.findById(id);
        if (existingLanguage.isEmpty()) return  "No existe esa idioma";
        Language updatedLanguage = existingLanguage.get();
        if (name != null && !name.isEmpty()) updatedLanguage.setLanguageName(name);
        if (code != null && !code.isEmpty()) updatedLanguage.setLanguageCode(code);
        languageRepo.save(updatedLanguage);
        return null;
    }

}
