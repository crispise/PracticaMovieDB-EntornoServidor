package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieKeywords {
    @EmbeddedId
    private MovieKeywordId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("keywordId")
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public MovieKeywordId getId() {
        return id;
    }

    public void setId(MovieKeywordId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }


}
