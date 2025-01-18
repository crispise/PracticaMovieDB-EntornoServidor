package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.services.CRUDServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
public class PersonController {

    @Autowired
    CRUDServices crudServices;

    @GetMapping("/persons")
    public String getPersons() {
           return "persons";
    }

    @PostMapping("/persons")
    public String newPerson(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allPersons";
            case "search-by-name":
                return "redirect:/searchPersons";
            case "create-new":
                return "redirect:/createPerson";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "persons";
    }

    @GetMapping("/allPersons")
    public String getAllPersons(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = crudServices.findAllPersons(pageable);
        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());
        model.addAttribute("size", size);
        return "persons";
    }

    @GetMapping("/searchPersons")
    public String searchPersons(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = crudServices.getJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "persons";
    }

    @PostMapping("/searchPersons")
    public String getPersonWanted(@RequestParam String personName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = crudServices.getJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Person> peopleFound = crudServices.findPersonsByName(personName);
        model.addAttribute("peopleFound", peopleFound);
        return "persons";
    }



    @GetMapping("/createPerson")
    public String createPerson(Model model) {
        model.addAttribute("createNew", true);
        return "persons";
    }


    @PostMapping("/createPerson")
    public String savePerson(@RequestParam String personName, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =crudServices.savePerson(personName);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Persona creada exitosamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage); // El mensaje de error es proporcionado por el servicio
        }
        return "persons";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam Integer personId, @RequestParam int currentPage, Model model){
        String message = crudServices.deletePerson(personId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "La persona se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar a la persona");
        }
        return "redirect:/allPersons?page=" + currentPage + "&size=" + 20;
    }

    @GetMapping("/updatePerson/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Person person = crudServices.findPersonById(id);
        if (person != null) {
            model.addAttribute("person", person);
            model.addAttribute("update", true);
            model.addAttribute("updateForm", true);
            return "persons";
        } else {
            model.addAttribute("errorMessage", "La persona no fue encontrada");
            return "persons";
        }
    }

   @PostMapping("/updatePerson")
    public String updatePerson(@RequestParam Integer personId, @RequestParam String personName, Model model){
        Person personUpdate = crudServices.updatePerson(personId, personName);
        model.addAttribute("person", personUpdate);
        model.addAttribute("update", true);
        return "persons";
   }


}
