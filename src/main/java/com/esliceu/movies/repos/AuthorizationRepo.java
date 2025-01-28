package com.esliceu.movies.repos;

import com.esliceu.movies.models.Authorization;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Permission;
import com.esliceu.movies.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorizationRepo extends JpaRepository<Authorization, Long> {
    List<Authorization> findByUser(User user);
    Optional<Authorization> findByUserAndPermission(User user, Permission permission);
}
