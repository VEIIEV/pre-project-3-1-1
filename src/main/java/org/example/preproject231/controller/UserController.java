package org.example.preproject231.controller;


import org.example.preproject231.dto.UserDto;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping("/{id}/update-form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user-update-form";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model) {
        return "user-create-form";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users";
    }

    @PostMapping
    public String addUser(@ModelAttribute UserDto user, Model model) {
        userService.addUser(user);
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute UserDto user, Model model) {
        userService.updateUser(id, user);
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

//    мне нужны странички с формами для создания и апдейта

}
