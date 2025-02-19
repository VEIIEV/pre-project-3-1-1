package org.example.preproject231.controller;


import jakarta.servlet.Registration;
import org.example.preproject231.dao.UserDao;
import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.mapper.UserMapper;
import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @GetMapping("/users/{username}")
    public String getUserById(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "user";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    //todo толя, этот контроллер выполнится, или spring security перехватит управление и запустит свою логику?
    @PostMapping("/login")
    public String sendLoginForm(
            @Validated(UserAuthDTO.Login.class) @ModelAttribute("user") UserAuthDTO userAuthDTO) {
        return "redirect:users/";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String sendRegistrationForm(@Validated(UserAuthDTO.Registration.class) UserAuthDTO userAuthDTO,
                                       BindingResult result,
                                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());

            return "registration";
        }
        User user = userMapper.toEntity(userAuthDTO);
        userService.addUser(user);
        return "redirect:/login";
    }


}
