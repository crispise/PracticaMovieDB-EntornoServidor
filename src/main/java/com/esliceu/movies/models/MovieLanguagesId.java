package com.esliceu.movies.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieLanguagesId implements Serializable {
    private Integer movieId;
    private Integer languageId;
    private Integer languageRoleId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLanguageRoleId() {
        return languageRoleId;
    }

    public void setLanguageRoleId(Integer languageRoleId) {
        this.languageRoleId = languageRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieLanguagesId that = (MovieLanguagesId) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(languageId, that.languageId) &&
                Objects.equals(languageRoleId, that.languageRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, languageId, languageRoleId);
    }
}
