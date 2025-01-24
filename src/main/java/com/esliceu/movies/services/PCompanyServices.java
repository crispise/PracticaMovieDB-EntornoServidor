package com.esliceu.movies.services;

import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.PCompaniesRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PCompanyServices {
    @Autowired
    PCompaniesRepo productionCompanyRepo;


    public Page<ProductionCompany> findAllCompanies(Pageable pageable) {
        return productionCompanyRepo.findAll(pageable);
    }

    public String getCompaniesJson() {
        List<ProductionCompany> companies = productionCompanyRepo.findAll();
        List<String> names = companies.stream()
                .map(p -> p.getCompanyName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String saveCompany(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre de la compañia no puede estar vacío.";
        }

        if (productionCompanyRepo.findProductionCompanyByCompanyName(name).size() >= 1) {
            return "Ya existe una compañia con ese nombre.";
        }
        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setCompanyName(name);
        productionCompanyRepo.save(productionCompany);
        return null;
    }

    public ProductionCompany findCompanyById(Integer id) {
        return productionCompanyRepo.findById(id).get();
    }

    public List<ProductionCompany> findCompaniesByName(String name) {
        return productionCompanyRepo.findProductionCompanyByCompanyName(name);
    }

    public String deleteCompany(Integer id) {
        try {
            productionCompanyRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public ProductionCompany updateCompany(Integer id, String name) {
        Optional<ProductionCompany> existingCompany = productionCompanyRepo.findById(id);
        if (existingCompany.isPresent()) {
           ProductionCompany updatedCompany = existingCompany.get();
            updatedCompany.setCompanyName(name);
            productionCompanyRepo.save(updatedCompany);
            return updatedCompany;
        } else {
            return null;
        }
    }

}
