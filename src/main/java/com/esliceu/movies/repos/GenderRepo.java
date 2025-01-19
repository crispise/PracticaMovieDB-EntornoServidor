package com.esliceu.movies.repos;

import com.esliceu.movies.models.Gender;
import com.esliceu.movies.models.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenderRepo extends JpaRepository<Gender, Integer> {
    List<Gender> findGenderByGender(String personSearch);
}
