package com.esliceu.movies.repos;

import com.esliceu.movies.models.ProductionCountry;
import com.esliceu.movies.models.ProductionCountryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCountryRepo extends JpaRepository<ProductionCountry, ProductionCountryId> {
}
