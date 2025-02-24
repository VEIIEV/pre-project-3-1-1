package org.example.preproject231.service;


import jakarta.validation.ValidationException;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        if (!isValidRole(userAuthDTO)) {
            throw new ValidationException("Role is not valid");
        }
        if (!isUniqueUserNameAndMail(userAuthDTO)) {
            throw new ValidationException("Username and mail are not unique");
        }

        User user = userMapper.toEntity(userAuthDTO);

        User userFromDb = userDao.save(user);
        return new UserDto(userFromDb);
    }


    public UserAuthDTO updateUser(long id, UserAuthDTO userAuthDTO) {
        if (!isValidRole(userAuthDTO)) {
            throw new ValidationException("Role is not valid");
        }
        if (!isUniqueUserNameAndMail(userAuthDTO)) {
            throw new ValidationException("Username and mail are not unique");
        }


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


    private static boolean isValidRole(UserAuthDTO userAuthDTO) {
        List<String> rolesName = Stream.of(Role.enumRole.values()).map(Role.enumRole::getValue).toList();
        return new HashSet<>(rolesName).containsAll(userAuthDTO.getAuthorities());
    }

    private boolean isUniqueUserNameAndMail(UserAuthDTO userAuthDTO) {
        Long currentUserid = -1L;
        Optional<User> user = userDao.findByEmail(userAuthDTO.getEmail());
        if (user.isPresent()) {
            currentUserid = user.get().getId();
        }

        Optional<User> userOptional = userDao.findByUsernameOrEmailExcludingCurrent(
                userAuthDTO.getUsername(),
                userAuthDTO.getEmail(),
                currentUserid);
        return userOptional.isEmpty();
    }
}
