package com.esliceu.movies.repos;

import com.esliceu.movies.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepo extends JpaRepository<Language, Integer> {
    List<Language> findLanguageByLanguageName(String languageSearch);
}
