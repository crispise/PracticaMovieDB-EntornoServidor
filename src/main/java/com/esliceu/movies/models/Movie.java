package com.esliceu.movies.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(length = 1000)
    private String title;

    private Integer budget;

    @Column(length = 1000)
    private String homepage;

    @Column(length = 1000)
    private String overview;

    @Column(precision = 12, scale = 6)
    private BigDecimal popularity;

    private LocalDate releaseDate;

    private Long revenue;

    private Integer runtime;

    @Column(length = 50)
    private String movieStatus;

    @Column(length = 1000)
    private String tagline;

    @Column(precision = 4, scale = 2)
    private BigDecimal voteAverage;

    private Integer voteCount;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCompany> movieCompanies = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenres> movieGenres = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference  // Marca la relación como "administrada" (serializable)
    private List<MovieKeywords> movieKeywords = new ArrayList<>();

    public List<MovieGenres> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<MovieGenres> movieGenres) {
        this.movieGenres = movieGenres;
    }



    public List<MovieKeywords> getMovieKeywords() {
        return movieKeywords;
    }

    public void setMovieKeywords(List<MovieKeywords> movieKeywords) {
        this.movieKeywords = movieKeywords;
    }


    public List<MovieCompany> getMovieCompanies() {
        return movieCompanies;
    }

    public void setMovieCompanies(List<MovieCompany> movieCompanies) {
        this.movieCompanies = movieCompanies;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                '}';
    }
}
