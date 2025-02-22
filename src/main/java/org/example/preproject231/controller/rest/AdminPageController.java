package org.example.preproject231.controller.rest;


import org.example.preproject231.dto.UserDto;
import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    //todo переписать под рест, и для single пользователя тоже у
    @GetMapping
    public List<User> getAllUsers() {
//        model.addAttribute("users", userService.getUsersList());
//        model.addAttribute("isEdit", false);

//          <td>${user.id}</td>
//                <td>${user.firstName}</td>
//                <td>${user.lastName}</td>
//                <td>${user.age}</td>
//                <td>${user.email}</td>
//                <td>${Array.isArray(user.role) ? user.role.join(", ") : user.role}</td>
        return userService.getUsersList();
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

        userService.addUser(user.toUser());
        return "redirect:/users";
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
