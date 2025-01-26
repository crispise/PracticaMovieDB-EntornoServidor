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
    @Autowired
    PermissionsServices permissionsServices;


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

    public ProductionCompany findCompanyById(Integer id) {
        return productionCompanyRepo.findById(id).get();
    }

    public List<ProductionCompany> findCompaniesByName(String name) {
        return productionCompanyRepo.findProductionCompanyByCompanyName(name);
    }

    public String saveCompany(String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear compañias");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        if (name == null || name.trim().isEmpty()) return "El nombre de la compañia no puede estar vacío.";
        if (productionCompanyRepo.findProductionCompanyByCompanyName(name).size() >= 1)
            return "Ya existe una compañia con ese nombre.";
        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setCompanyName(name);
        productionCompanyRepo.save(productionCompany);
        return null;
    }

    public String deleteCompany(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar compañias");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            productionCompanyRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar a la compañia";
        }
    }

    public String updateCompany(Integer id, String name, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Modificar compañias");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        Optional<ProductionCompany> existingCompany = productionCompanyRepo.findById(id);
        if (existingCompany.isEmpty()) return "No existe esa compañia";
        ProductionCompany updatedCompany = existingCompany.get();
        updatedCompany.setCompanyName(name);
        productionCompanyRepo.save(updatedCompany);
        return null;
    }
}