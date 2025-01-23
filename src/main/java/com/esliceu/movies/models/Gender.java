package com.esliceu.movies.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genderId;

    @Column(length = 20)
    private String gender;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCast> movieCast = new ArrayList<>();

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public List<MovieCast> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(List<MovieCast> movieCast) {
        this.movieCast = movieCast;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
