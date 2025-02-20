package org.example.preproject231.service;


import org.example.preproject231.dao.RoleDao;
import org.example.preproject231.dao.UserDao;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.entity.Role;
import org.example.preproject231.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    public List<User> getUsersList() {
        return userDao.findAll();
    }

    public User getUserById(long id) {
        return userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("User with id " + id + " not found", 1));
    }

    public UserDto addUser(User user) {
        Role role = roleDao.getRoleByName(Role.enumRole.USER);
        user.setRoles(Collections.singleton(role));

        User userFromDb = userDao.save(user);
        return new UserDto(userFromDb);
    }

    public UserDto updateUser(long id, UserDto user, Set<String> roles) {
        User userFromDb = userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("User with id " + id + " not found", 1));

        if (!userFromDb.getEmail().equals(user.getEmail())) {
            userFromDb.setEmail(user.getEmail());
        }
        addRoles(roles, userFromDb);
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userDao.save(userFromDb);
        return user;
    }

    private void addRoles(Set<String> roles, User userFromDb) {
        if (roles != null) {
            userFromDb.setRoles(roleDao.findByNameIn(roles));
        } else {
            userFromDb.setRoles(Collections.emptySet());
        }
    }

    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    public Object getUserByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(
                () -> new EmptyResultDataAccessException("User with username {" + username + "} not found", 1));

    }
}
