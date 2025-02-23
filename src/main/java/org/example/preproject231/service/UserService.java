package org.example.preproject231.service;


import org.example.preproject231.dao.RoleDao;
import org.example.preproject231.dao.UserDao;
import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.dto.mapper.UserMapper;
import org.example.preproject231.entity.Role;
import org.example.preproject231.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("dbUserDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserMapper userMapper;


    public List<User> getUsersList() {
        return userDao.findAll(Sort.by(Sort.Order.asc("id")));
    }

    public User getUserById(long id) {
        return userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("User with id " + id + " not found", 1));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public UserDto addUser(UserAuthDTO userAuthDTO) {

        User user = userMapper.toEntity(userAuthDTO);

        User userFromDb = userDao.save(user);
        return new UserDto(userFromDb);
    }

    public UserAuthDTO updateUser(long id, UserAuthDTO userAuthDTO) {
        User userFromDb = userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("User with id " + id + " not found", 1));

        User user = userMapper.toEntity(userAuthDTO);


        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setRoles(user.getRoles());
        return userMapper.toAuthDto(userDao.save(userFromDb));
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

    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(Role.enumRole.valueOf(name));
    }
}
