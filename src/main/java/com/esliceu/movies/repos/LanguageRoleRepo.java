package com.esliceu.movies.repos;

import com.esliceu.movies.models.LanguageRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRoleRepo extends JpaRepository<LanguageRole, Integer> {
    List<LanguageRole> findLanguageRoleByLanguageRole(String languageRoleSearch);
}
