package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieCast {

    @EmbeddedId
    private MovieCastId id;

    @Column(name = "cast_order")
    private Integer castOrder;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("genderId")
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    public String getCharacterName() {
        return id != null ? id.getCharacterName() : null;
    }

    public void setCharacterName(String characterName) {
        if (id == null) {
            id = new MovieCastId();
        }
        id.setCharacterName(characterName);
    }


    public MovieCastId getId() {
        return id;
    }

    public void setId(MovieCastId id) {
        this.id = id;
    }

    public Integer getCastOrder() {
        return castOrder;
    }

    public void setCastOrder(Integer castOrder) {
        this.castOrder = castOrder;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
