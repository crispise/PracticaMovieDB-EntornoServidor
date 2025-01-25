package com.esliceu.movies.services;

import com.esliceu.movies.models.User;
import com.esliceu.movies.repos.UserRepo;
import com.esliceu.movies.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    Encryptor encryptor;

    public User checkUser(String username, String password) throws NoSuchAlgorithmException {
        User u = userRepo.findByUsername(username);
        String encryptPassw = encryptor.encryptString(password);
        if (u != null) {
            if (u.getPassword().equals(encryptPassw)) {
                return u;
            }
        }
        return null;
    }
}
