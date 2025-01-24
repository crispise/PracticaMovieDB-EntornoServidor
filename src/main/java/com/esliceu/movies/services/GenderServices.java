package com.esliceu.movies.services;

import com.esliceu.movies.models.Gender;
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

    public String saveGender(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El genero no puede estar vacío.";
        }
        if (genderRepo.findGenderByGender(name).size() >= 1) {
            return "Ya existe un genero con ese nombre.";
        }
        Gender gender = new Gender();
        gender.setGender(name);
        genderRepo.save(gender);
        return null;
    }

    public Gender findGenderById(Integer id) {
        return genderRepo.findById(id).get();
    }

    public List<Gender> findGenderByName(String name) {
        return genderRepo.findGenderByGender(name);
    }

    public String deleteGender(Integer id) {
        try {
            genderRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public Gender updateGender(Integer id, String name) {
        Optional<Gender> existingGender = genderRepo.findById(id);
        if (existingGender.isPresent()) {
           Gender updatedGender = existingGender.get();
            updatedGender.setGender(name);
            genderRepo.save(updatedGender);
            return updatedGender;
        } else {
            return null;
        }
    }

}
