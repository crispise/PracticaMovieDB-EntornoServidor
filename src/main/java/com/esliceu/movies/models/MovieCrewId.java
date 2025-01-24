package com.esliceu.movies.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MovieCrewId implements Serializable {

    private Integer movieId;
    private Integer departmentId;
    private Integer personId;

    public MovieCrewId() {
    }

    public MovieCrewId(Integer movieId, Integer departmentId, Integer personId) {
        this.movieId = movieId;
        this.departmentId = departmentId;
        this.personId = personId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
        MovieCrewId that = (MovieCrewId) o;
        return (movieId != null && movieId.equals(that.movieId)) &&
                (departmentId != null && departmentId.equals(that.departmentId)) &&
                (personId != null && personId.equals(that.personId));
    }

    @Override
    public int hashCode() {
        return 31 * (movieId.hashCode() + departmentId.hashCode() + personId.hashCode());
    }
}
