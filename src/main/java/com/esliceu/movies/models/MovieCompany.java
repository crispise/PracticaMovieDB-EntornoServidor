package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieCompany {
    @EmbeddedId
    private MovieCompanyId id;

    @ManyToOne
    @MapsId("movieId") // Mapea el atributo movieId de MovieCompanyId
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("companyId") // Mapea el atributo companyId de MovieCompanyId
    @JoinColumn(name = "company_id")
    private ProductionCompany productionCompany;


    public MovieCompanyId getId() {
        return id;
    }

    public void setId(MovieCompanyId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }

    @Override
    public String toString() {
        return "MovieCompany{" +
                "id=" + id +
                ", movie=" + movie +
                ", productionCompany=" + productionCompany +
                '}';
    }
}
