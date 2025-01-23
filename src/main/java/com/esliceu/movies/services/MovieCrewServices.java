package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieCrewServices {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    MovieCrewRepo movieCrewRepo;


    public Page<MovieCrew> getMovieCrew(Pageable pageable, Movie movie) {
        Page<MovieCrew> movieCrewList = movieCrewRepo.findByMovie(movie, pageable);
        return movieCrewList;
    }

    public String addMovieCrew(String personName, String departmentName, String job, Movie movie) {
        List<Person> persons = personRepo.findPersonByPersonName(personName);
        if (persons.size() == 1) {
            Person person = persons.get(0);
            boolean exists = checkIfMovieCrewAlredyExists(movie, person, departmentName, job);
            if (exists) {
                return "Este miembro del equipo ya esta añadido";
            }
            return createAndSaveMovieCrew(movie, person, departmentName, job);
        }
        return "Hay más de una compañia con ese nombre";
    }

    private static boolean checkIfMovieCrewAlredyExists(Movie movie, Person person, String departmentName, String job) {
        for (MovieCrew mc : movie.getMovieCrew()) {
            if (mc.getPerson().getPersonId().equals(person.getPersonId())) {
                if (mc.getDepartment().equals(departmentName)) {
                    if (mc.getJob().equals(job))return true;
                }
            }
        }
        return false;
    }

    private String createAndSaveMovieCrew(Movie movie, Person person, String departmentName, String job) {
        List<Department> departments = departmentRepo.findDepartmentByDepartmentName(departmentName);
        Department department = departments.get(0);

        MovieCrew movieCrew = new MovieCrew();

        movieCrew.setMovie(movie);
        movieCrew.setDepartment(department);
        movieCrew.setPerson(person);
        movieCrew.setJob(job);

        MovieCrewId movieCrewId = new MovieCrewId();
        movieCrewId.setMovieId(movie.getMovieId());
        movieCrewId.setPersonId(person.getPersonId());
        movieCrewId.setDepartmentId(department.getDepartmentId());

        movieCrew.setId(movieCrewId);
        movieCrewRepo.save(movieCrew);
        return null;
    }

}
