package com.esliceu.movies.controllers;

import com.esliceu.movies.models.User;
import com.esliceu.movies.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model m, HttpSession session, @RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        User user = loginService.checkUser(username, password);
        if (user != null) {
            session.setAttribute("user", username);
            return "redirect:/moviesQuerys";
        } else {
            m.addAttribute("errorMessage", "Usuario i/o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/closeSesion")
    public String closeSesion(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        session.invalidate();
        return "redirect:/moviesQuerys";
    }

}
