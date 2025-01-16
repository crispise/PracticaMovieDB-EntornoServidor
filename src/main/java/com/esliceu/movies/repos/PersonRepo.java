package com.esliceu.movies.repos;

import com.esliceu.movies.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {
}
