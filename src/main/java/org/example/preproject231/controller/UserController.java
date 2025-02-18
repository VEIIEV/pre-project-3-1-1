package org.example.preproject231.controller;


import org.example.preproject231.dto.UserDto;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String getCreateForm() {
        return "user-create-form";
    }

//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "users";
//    }

    @PostMapping
    public String addUser(@Validated @ModelAttribute UserDto user,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user-create-form";
        }

        userService.addUser(user);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Validated @ModelAttribute UserDto user,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user-create-form";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }


}
