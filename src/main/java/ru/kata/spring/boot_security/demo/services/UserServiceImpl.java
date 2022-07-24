package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void updateUser(Long id, User user) {
        User userUp = findById(id);
        userUp.setUsername(user.getUsername());
        userUp.setPassword(user.getPassword());
        userUp.setRoles(user.getRoles());
        userRepository.save(userUp);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Role> findRolesByName(String name) {
        List<Role> roles = new ArrayList<>();
        for (Role role : findAllRoles()) {
            if (name.contains(role.getName()))
                roles.add(role);
        }
        return roles;
    }

    @Override
    public User findUserByUsername(String username) {
        User userDb = userRepository.findByUsername(username);
        if (userDb == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return userDb;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDb = userRepository.findByUsername(username);
        if (userDb == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return userDb;
    }
}
