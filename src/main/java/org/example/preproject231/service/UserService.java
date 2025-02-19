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
import java.util.HashSet;
import java.util.List;

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

    public UserDto updateUser(long id, UserDto user) {
        User userFromDb = userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("User with id " + id + " not found", 1));
        //внести из дто данные в userFromDb
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setEmail(user.getEmail());
        userDao.save(userFromDb);
        return user;
    }

    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    public Object getUserByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(
                () -> new EmptyResultDataAccessException("User with username {" + username + "} not found", 1));

    }
}
