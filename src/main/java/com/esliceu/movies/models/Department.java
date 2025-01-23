package com.esliceu.movies.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @Column(length = 200)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCrew> movieCrew = new ArrayList<>();

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<MovieCrew> getMovieCrew() {
        return movieCrew;
    }

    public void setMovieCrew(List<MovieCrew> movieCrew) {
        this.movieCrew = movieCrew;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", movieCrew=" + movieCrew +
                '}';
    }
}
