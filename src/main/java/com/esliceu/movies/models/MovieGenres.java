package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieGenres {
    @EmbeddedId
    private MovieGenresId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;


    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieGenresId getId() {
        return id;
    }

    public void setId(MovieGenresId id) {
        this.id = id;
    }

}
