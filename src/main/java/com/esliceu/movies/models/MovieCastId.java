package com.esliceu.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieCastId implements Serializable {

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "gender_id")
    private Integer genderId;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "characterName", length = 400)
    private String characterName;

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieCastId that)) return false;
        return Objects.equals(movieId, that.movieId) && Objects.equals(genderId, that.genderId) && Objects.equals(personId, that.personId) && Objects.equals(characterName, that.characterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, genderId, personId, characterName);
    }
}
