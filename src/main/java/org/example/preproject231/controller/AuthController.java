package org.example.preproject231.controller;


import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{username}")
    public String getUserById(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "users";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String sendLoginForm(
            @Validated(UserAuthDTO.Login.class) @ModelAttribute("user") UserAuthDTO userAuthDTO,
            Model model) {
        return "redirect:/users/{id}";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String sendRegistrationForm(
            @Validated(UserAuthDTO.Registration.class) UserAuthDTO userAuthDTO,
            Model model) {
        return "redirect:/login";
    }


}
