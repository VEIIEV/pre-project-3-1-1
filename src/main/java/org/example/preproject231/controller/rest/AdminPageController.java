package org.example.preproject231.controller.rest;


import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.dto.mapper.UserMapper;
import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/create-form")
    public String getCreateForm() {
        return "user-form";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@Validated(UserAuthDTO.AdminCreation.class) @ModelAttribute UserAuthDTO user) {

        return userService.addUser(userMapper.toEntity(user));

    }

    @GetMapping("/{id}/update-form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("isEdit", true);
        return "user-form";
    }


    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Validated @ModelAttribute UserDto user,
                             @RequestParam(name = "roles", required = false) Set<String> roles,
                             BindingResult result,
                             Model model) {
        model.addAttribute("isEdit", true);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user-form";
        }
        userService.updateUser(id, user, roles);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }


}
