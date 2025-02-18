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
@RequestMapping("/admin/users")
public class AdminPageController {


    @Autowired
    private UserService userService;


    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("isEdit", false);
        return "users";
    }

    @GetMapping("/{id}/update-form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("isEdit", true);
        return "user-form";
    }

    @GetMapping("/create-form")
    public String getCreateForm() {
        return "user-form";
    }

    @PostMapping
    public String addUser(@Validated @ModelAttribute UserDto user,
                          BindingResult result,
                          Model model) {
        model.addAttribute("isEdit", false);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());

            return "user-form";
        }

        userService.addUser(user);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Validated @ModelAttribute UserDto user,
                             BindingResult result,
                             Model model) {
        model.addAttribute("isEdit", true);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user-form";
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
