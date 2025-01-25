package com.esliceu.movies.services;

import com.esliceu.movies.exceptions.AdminPasswordException;
import com.esliceu.movies.exceptions.NameTooShortException;
import com.esliceu.movies.exceptions.PasswordTooShortException;
import com.esliceu.movies.exceptions.UserExistsException;
import com.esliceu.movies.models.User;
import com.esliceu.movies.repos.UserRepo;
import com.esliceu.movies.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    Encryptor encryptor;

    public void registerUser(String username, String password, String email, String role, String adminPassw) throws Exception {
        if (role.equals("admin") && !adminPassw.equals("esLiceu")) throw new AdminPasswordException("La contraseña para ser administrador no es correcta");
        boolean correctUsername = checkIfUsernameExists(username);
        boolean correctPassword = checkPasswordLength(password);
        boolean correctName = checkNameLength(username);

        if (!correctUsername) {
            throw new UserExistsException("El username ya existe.");
        } else if (!correctPassword) {
            throw new PasswordTooShortException("La contraseña tiene que tener un mínimo de 5 carácteres.");
        } else if (!correctName) {
            throw new NameTooShortException("El nombre es demasiado corto, tiene que tener 6 carácteres.");
        }
        User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
        User user = new User();
        user.setUsername(username);
        String encryptPasw = encryptor.encryptString(password);
        user.setPassword(encryptPasw);
        user.setEmail(email);
        user.setRole(roleEnum);
        userRepo.save(user);
    }

    private boolean checkNameLength(String name) {
        if (name.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean checkPasswordLength(String password) {
        if (password.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean checkIfUsernameExists(String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return false;
        }
        return true;
    }
}
