package com.esliceu.movies.services;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.PCompaniesRepo;
import com.esliceu.movies.repos.PersonRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServices {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    PermissionsServices permissionsServices;

    public Page<Person> findAllPersons(Pageable pageable) {
        return personRepo.findAll(pageable);
    }

    public String getPersonJson() {
        List<Person> persons = personRepo.findAll();
        List<String> names = persons.stream()
                .map(p -> p.getPersonName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public Person findPersonById(Integer id) {
        return personRepo.findById(id).get();
    }

    public List<Person> findPersonsByName(String personSearch) {
        return personRepo.findPersonByPersonName(personSearch);
    }

    public String savePerson(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear personas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El nombre de la persona no puede estar vacío.";
        if (personRepo.findPersonByPersonName(name).size() >= 1) return "Ya existe una persona con ese nombre.";
        Person person = new Person();
        person.setPersonName(name);
        personRepo.save(person);
        return null;
    }

    public String deletePerson(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar personas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            personRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar a la persona";
        }
    }

    public String updatePerson(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar personas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        Optional<Person> existingPerson = personRepo.findById(id);
        if (existingPerson.isEmpty()) return "No existe esa persona";
        Person updatedPerson = existingPerson.get();
        updatedPerson.setPersonName(name);
        personRepo.save(updatedPerson);
        return null;
    }
}
