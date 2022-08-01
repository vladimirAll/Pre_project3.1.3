package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public String adminInfo(Principal principal, Model model, @ModelAttribute("user") User user) {
        model.addAttribute("admin", userServiceImpl.findUserByName(principal.getName()));
        model.addAttribute("users", userServiceImpl.findAllUsers());
        model.addAttribute("roles", userServiceImpl.findRoles());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "roleName", required = false) String roles) {
        user.setRoles(Collections.singleton(userServiceImpl.getRoleByName(roles)));
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deletePlayer(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("users", userServiceImpl.findById(id));
        return "update";
    }

    @PostMapping ("/update/{id}")
    public String update(@ModelAttribute("users") User user,
                         @RequestParam(value = "roleName", required = false) String roles) {
        user.setRoles(Collections.singleton(userServiceImpl.getRoleByName(roles)));
        userServiceImpl.update(user);
        return "redirect:/admin";
    }
}
