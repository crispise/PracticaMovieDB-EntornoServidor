package com.esliceu.movies.repos;

import com.esliceu.movies.models.Keyword;
import com.esliceu.movies.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepo extends JpaRepository<Keyword, Integer> {
    List<Keyword> findKeywordByKeywordName(String keywordName);
}
