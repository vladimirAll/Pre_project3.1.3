package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("user", userServiceImpl.findAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userServiceImpl.findAllRoles());
        return "new";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") String name ) {
        user.setRoles(userServiceImpl.findRolesByName(name));
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deletePlayer(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServiceImpl.findById(id));
        return "/update";
    }

    @PostMapping("update/{id}")
    public String addUpdateUser(Long  id, User user) {
        userServiceImpl.updateUser(id, user);
        return "redirect:/admin";
    }
}
