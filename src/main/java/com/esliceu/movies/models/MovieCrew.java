package com.esliceu.movies.models;

import jakarta.persistence.*;

@Entity
public class MovieCrew {

    @EmbeddedId
    private MovieCrewId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "department_id")
    private Department department;

    public String getJob() {
        return id != null ? id.getJob() : null;
    }

    public void setJob(String job) {
        if (id == null) {
            id = new MovieCrewId();
        }
        id.setJob(job);
    }


    public MovieCrewId getId() {
        return id;
    }

    public void setId(MovieCrewId id) {
        this.id = id;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
