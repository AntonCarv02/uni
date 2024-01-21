package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.UserDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




@Controller
public class SpringSecurityController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public String defaultPage(Model model) {

        model.addAttribute("msg", "Welcome to SportsEventsPro");
        return "index";
        
    }


    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out");
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {

        request.getSession().invalidate();

        return "redirect:/login?logout";
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {

        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        
        return "admin";
    }


    @GetMapping("/home")
    public String index1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the username from the authentication object
        String username = authentication.getName();

        model.addAttribute("message", "Welcome, "+ username);

        return "home";
    }



    @GetMapping("/newuser")
    public String newuser(Model model) {

        model.addAttribute("title", "New User");
        model.addAttribute("message", "fill new user's details");

        // DESCOMENTAR ADIANTE
        // List<String> currentUsers = userDao.getUsernameList();
        // model.addAttribute("message", "(we have now " + currentUsers.size() + "
        // users) fill new user's details");
        // System.out.println("\n" + currentUsers.size() + " USERS: " +
        // currentUsers.toString());

        return "newuser";
    }

    @GetMapping("/register")
    public String register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email1,
            Model model) {
        model.addAttribute("message", "registration is OK");

        model.addAttribute("title", "registration page");

        String encodedPassword = new BCryptPasswordEncoder().encode(password);
  
        User u = new User(username, encodedPassword, email1, "ROLE_USER");
        userDao.saveUser(u); // escrever na BD

        System.out.println("GRAVAR na BD: " + u.toString());
        model.addAttribute("user", u);

        /* 
        String url = request.getContextPath() + "/j_security_check";
        response.sendRedirect(url + "?j_username="
                + URLEncoder.encode(username, "UTF-8")
                + "&j_password="
                + URLEncoder.encode(password, "UTF-8"));*/

        return "register";
    }

}
