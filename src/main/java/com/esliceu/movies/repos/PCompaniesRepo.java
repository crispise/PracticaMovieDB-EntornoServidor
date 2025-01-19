package com.esliceu.movies.repos;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.models.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PCompaniesRepo extends JpaRepository<ProductionCompany, Integer> {
    List<ProductionCompany> findProductionCompanyByCompanyName(String personSearch);
}
