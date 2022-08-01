package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    List<User> findAllUsers();

    void deleteUser(Long id);

    void addUser(User user);

    void update(User user);

    User findUserByName(String username);

    List<Role> findRoles();

    Role getRoleByName(String name);
}
