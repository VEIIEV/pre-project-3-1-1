package org.example.preproject231.controller.rest;


import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.dto.mapper.UserMapper;
import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminPageController {

    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsersList();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserAuthDTO getUser(@PathVariable Long id) {
        return userMapper.toAuthDto(userService.getUserById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@Validated(UserAuthDTO.AdminCreation.class) @ModelAttribute UserAuthDTO user) {
        return userService.addUser(user);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserAuthDTO updateUser(@PathVariable("id") long id,
                             @Validated() @ModelAttribute UserAuthDTO user) {

        return userService.updateUser(id, user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
