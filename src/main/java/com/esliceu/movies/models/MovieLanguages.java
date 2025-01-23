package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieLanguages {
    @EmbeddedId
    private MovieLanguagesId id = new MovieLanguagesId();

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("languageId")
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @MapsId("languageRoleId")
    @JoinColumn(name = "language_role_id")
    private LanguageRole languageRole;

    public MovieLanguagesId getId() {
        return id;
    }

    public void setId(MovieLanguagesId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageRole getLanguageRole() {
        return languageRole;
    }

    public void setLanguageRole(LanguageRole languageRole) {
        this.languageRole = languageRole;
    }
}
