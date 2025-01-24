package com.esliceu.movies.repos;

import com.esliceu.movies.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    List<Person> findPersonByPersonName(String personSearch);
    List<Person> findByMovieCastIsNotEmpty();
}
