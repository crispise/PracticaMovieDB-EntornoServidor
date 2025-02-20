package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Permission;
import com.esliceu.movies.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionName(String permissionName);
    List<Permission> findByAuthorizationsUserId(Long id);
}
