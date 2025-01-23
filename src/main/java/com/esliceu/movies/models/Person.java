package com.esliceu.movies.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    @Column(length = 500)
    private String personName;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCast> movieCast = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCrew> movieCrew = new ArrayList<>();

    public List<MovieCrew> getMovieCrew() {
        return movieCrew;
    }

    public void setMovieCrew(List<MovieCrew> movieCrew) {
        this.movieCrew = movieCrew;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<MovieCast> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(List<MovieCast> movieCast) {
        this.movieCast = movieCast;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                '}';
    }
}
