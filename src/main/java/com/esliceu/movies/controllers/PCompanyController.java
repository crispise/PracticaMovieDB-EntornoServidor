package com.esliceu.movies.controllers;

import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.services.PCompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PCompanyController {

    @Autowired
    PCompanyServices pCompanyServices;

    @GetMapping("/productionCompany")
    public String getProductionCompany() {
           return "productionCompany";
    }

    @PostMapping("/productionCompany")
    public String actionProductionCompany(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allCompanies";
            case "search-by-name":
                return "redirect:/searchCompanies";
            case "create-new":
                return "redirect:/createCompany";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "productionCompany";
    }

    @GetMapping("/allCompanies")
    public String getAllCompanies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductionCompany> companiPage = pCompanyServices.findAllCompanies(pageable);
        model.addAttribute("companies", companiPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companiPage.getTotalPages());
        model.addAttribute("size", size);
        return "productionCompany";
    }

    @GetMapping("/searchCompanies")
    public String searchCompanies(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "productionCompany";
    }

    @PostMapping("/searchCompanies")
    public String getCompanyWanted(@RequestParam String companyName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<ProductionCompany> companiesFound = pCompanyServices.findCompaniesByName(companyName);
        model.addAttribute("companiesFound", companiesFound);
        return "productionCompany";
    }


    @GetMapping("/createCompany")
    public String createCompany(Model model) {
        model.addAttribute("createNew", true);
        return "productionCompany";
    }


    @PostMapping("/createCompany")
    public String savePerson(@RequestParam String companyName, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =pCompanyServices.saveCompany(companyName);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡Compañia creada exitosamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage); // El mensaje de error es proporcionado por el servicio
        }
        return "productionCompany";
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(@RequestParam Integer companyId, @RequestParam(required = false) Integer currentPage, Model model){
        String message = pCompanyServices.deleteCompany(companyId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "La compañia se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar a la compañia");
        }
        if (currentPage != null){
            return "redirect:/allCompanies?page=" + currentPage + "&size=" + 20;
        }else {
            return "productionCompany";
        }
    }

    @GetMapping("/updateCompany/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        ProductionCompany company = pCompanyServices.findCompanyById(id);
        if (company != null) {
            model.addAttribute("company", company);
            model.addAttribute("update", true);
            return "productionCompany";
        } else {
            model.addAttribute("errorMessage", "La compañia no fue encontrada");
            return "productionCompany";
        }
    }

   @PostMapping("/updateCompany/{companyId}")
    public String updateCompany(@RequestParam Integer companyId, @RequestParam String companyName, Model model){
        ProductionCompany companyUpdate = pCompanyServices.updateCompany(companyId, companyName);
        model.addAttribute("company", companyUpdate);
        model.addAttribute("update", true);
        return "productionCompany";
   }


}
