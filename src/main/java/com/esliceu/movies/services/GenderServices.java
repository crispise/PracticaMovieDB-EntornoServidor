package com.esliceu.movies.services;

import com.esliceu.movies.models.Gender;
import com.esliceu.movies.models.Person;
import com.esliceu.movies.repos.GenderRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenderServices {
    @Autowired
    GenderRepo genderRepo;
    @Autowired
    PermissionsServices permissionsServices;

    public Page<Gender> findAllGenders(Pageable pageable) {
        return genderRepo.findAll(pageable);
    }

    public String getGenderJson() {
        List<Gender> genders = genderRepo.findAll();
        List<String> names = genders.stream()
                .map(g -> g.getGender())
                .collect(Collectors.toList());
        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public Gender findGenderById(Integer id) {
        return genderRepo.findById(id).get();
    }

    public List<Gender> findGenderByName(String name) {
        return genderRepo.findGenderByGender(name);
    }

    public String saveGender(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear géneros");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El genero no puede estar vacío.";
        if (genderRepo.findGenderByGender(name).size() >= 1) return "Ya existe un genero con ese nombre.";
        Gender gender = new Gender();
        gender.setGender(name);
        genderRepo.save(gender);
        return null;
    }

    public String deleteGender(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar géneros");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            genderRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar el género";
        }
    }

    public String updateGender(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar géneros");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        List<Gender> sameName = genderRepo.findGenderByGender(name);
        if (!sameName.isEmpty()) return "Ya existe un registro con ese nombre";
        Optional<Gender> existingGender = genderRepo.findById(id);
        if (existingGender.isEmpty()) return "No existe ese género";
        Gender updatedGender = existingGender.get();
        updatedGender.setGender(name);
        genderRepo.save(updatedGender);
        return null;
    }
}
