package com.esliceu.movies.repos;

import com.esliceu.movies.models.MovieKeywords;
import com.esliceu.movies.models.MovieKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieKeywordRepo extends JpaRepository<MovieKeywords, MovieKeywordId> {
}
