package com.esliceu.movies.services;

import com.esliceu.movies.models.Authorization;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Permission;
import com.esliceu.movies.models.User;
import com.esliceu.movies.repos.AuthorizationRepo;
import com.esliceu.movies.repos.CountryRepo;
import com.esliceu.movies.repos.PermissionRepo;
import com.esliceu.movies.repos.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionsServices {
    @Autowired
    AuthorizationRepo authorizationRepo;
    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    UserRepo userRepo;

    public String getUserType(String username) {
        User user = userRepo.findByUsername(username);
        return String.valueOf(user.getRole());
    }

    public List<Permission> getAllPermissions() {
        return permissionRepo.findAll();
    }

    public List<Authorization> getAuthoritzationsForAdmin() {
        return authorizationRepo.findAll();
    }

    public String handlePermission(String action, String username, Long authoritzationId) {
        if (!getUserType(username).equals("ADMIN")) return "No tienes acceso a esta gestión";
        Optional<Authorization> authorization = authorizationRepo.findById(authoritzationId);
        if (authorization.isEmpty()) {
            return "No existe esta solicitud de permiso";
        }
        return changePermissionStatus(action, authorization);
    }

    private String changePermissionStatus(String action, Optional<Authorization> authorization) {
        if (action.equals("accept")) {
            authorization.get().setStatus(Authorization.Status.APROVADO);
            authorizationRepo.save(authorization.get());
            return null;
        } else if (action.equals("reject")) {
            authorization.get().setStatus(Authorization.Status.RECHAZADO);
            authorizationRepo.save(authorization.get());
            return null;
        }else{
            authorizationRepo.delete(authorization.get());
            return null;
        }
    }

    public List<Authorization> getUserAuthoritzations(String username) {
        User user = userRepo.findByUsername(username);
        return authorizationRepo.findByUser(user);
    }

    public List<Permission> getPermissionsNotAssignedToUser(String username) {
        User user = userRepo.findByUsername(username);
        List<Permission> assignedPermissions = permissionRepo.findByAuthorizationsUserId(user.getId());
        List<Permission> allPermissions = permissionRepo.findAll();
        allPermissions.removeAll(assignedPermissions);
        return allPermissions;
    }

    public String requestPermission(String username, Long permissionId) {
        if (getUserType(username).equals("ADMIN")) return "No necesitas permisos";
        Permission permission = permissionRepo.findById(permissionId).get();
        User user = userRepo.findByUsername(username);
        Authorization authorization = new Authorization();
        authorization.setUser(user);
        authorization.setPermission(permission);
        authorization.setStatus(Authorization.Status.PENDIENTE);
        authorizationRepo.save(authorization);
        return null;
    }
}
