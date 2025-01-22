package com.esliceu.movies.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(length = 200)
    private String companyName;

    @OneToMany(mappedBy = "productionCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCompany> movieCompanies = new ArrayList<>();


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<MovieCompany> getMovieCompanies() {
        return movieCompanies;
    }

    public void setMovieCompanies(List<MovieCompany> movieCompanies) {
        this.movieCompanies = movieCompanies;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ProductionCompany{" +
                "companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
