package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieCrewServices {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    MovieCrewRepo movieCrewRepo;
    @Autowired
    MovieRepo movieRepo;


    public Page<MovieCrew> getMovieCrew(Pageable pageable, Movie movie) {
        Page<MovieCrew> movieCrewList = movieCrewRepo.findByMovie(movie, pageable);
        return movieCrewList;
    }

    public String addMovieCrew(String personName, String departmentName, String job, Integer movieId) {
        if (personName.isEmpty() || departmentName.isEmpty() || job.isEmpty()) return "Falta rellenar un campo";

        List<Person> persons = personRepo.findPersonByPersonName(personName);
        if (persons.size() > 1) {
            return "Hay más de un miembro del equipo con ese nombre";
        } else if (persons.isEmpty()) {
            return "No existe una persona con ese nombre";
        }
        Person person = persons.get(0);
        List<Department> departments = departmentRepo.findDepartmentByDepartmentName(departmentName);
        if (departments.isEmpty()) return "No existe ese departamento";
        Department department = departments.get(0);
        Movie movie = movieRepo.findById(movieId).get();
        return createKeyAndMovieCrew(movie, person, department, job);
    }

    private String createKeyAndMovieCrew(Movie movie, Person person, Department department, String job) {
        MovieCrewId movieCrewId = new MovieCrewId();
        movieCrewId.setMovieId(movie.getMovieId());
        movieCrewId.setPersonId(person.getPersonId());
        movieCrewId.setDepartmentId(department.getDepartmentId());
        movieCrewId.setJob(job);
        Optional<MovieCrew> movieCrewSearch = movieCrewRepo.findById(movieCrewId);
        if (movieCrewSearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveMovieCrew(movie, person, job, department, movieCrewId);
        return null;
    }

    private void saveMovieCrew(Movie movie, Person person, String job, Department department, MovieCrewId movieCrewId) {
        MovieCrew movieCrew = new MovieCrew();
        movieCrew.setMovie(movie);
        movieCrew.setDepartment(department);
        movieCrew.setPerson(person);
        movieCrew.setJob(job);
        movieCrew.setId(movieCrewId);
        movieCrewRepo.save(movieCrew);
    }

    public String deleteMovieCrew(Integer movieId, Integer deparmentId, Integer personId, String job) {
      MovieCrewId movieCrewId = new MovieCrewId();
      movieCrewId.setPersonId(personId);
      movieCrewId.setDepartmentId(deparmentId);
      movieCrewId.setMovieId(movieId);
      movieCrewId.setJob(job);

        Optional<MovieCrew> movieCrew = movieCrewRepo.findById(movieCrewId);
        if (movieCrew.isPresent()) {
            movieCrewRepo.deleteById(movieCrewId);
            return null;
        }
        return "Ese miembro del equipo no está relacionado con la película";
    }

    public String getDirectorJson() {
        List<String> directors = movieCrewRepo.findById_Job("Director")
                .stream()
                .map(mc -> mc.getPerson().getPersonName()).toList();
        Set set = new HashSet<>(directors);
        Gson gson = new Gson();
        String result = gson.toJson(set);
        return result;
    }
}
