package com.esliceu.movies.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    @Column(length = 100)
    private String genreName;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenres> movieGenres = new ArrayList<>();

    public Integer getGenreId() {
        return genreId;
    }

    public List<MovieGenres> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<MovieGenres> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
