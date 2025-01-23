package com.esliceu.movies.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class MovieCastId implements Serializable {
    private Integer movieId;
    private Integer genderId;
    private Integer personId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCastId that = (MovieCastId) o;
        return movieId.equals(that.movieId) &&
                genderId.equals(that.genderId) &&
                personId.equals(that.personId);
    }

    @Override
    public int hashCode() {
        return 31 * (movieId.hashCode() + genderId.hashCode() + personId.hashCode());
    }
}
