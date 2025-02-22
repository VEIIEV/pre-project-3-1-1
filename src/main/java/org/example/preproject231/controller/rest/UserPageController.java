package org.example.preproject231.controller.rest;

import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserPageController {

    @Autowired
    private UserService userService;

    public record UserDetailsDTO(String username, List<String> authorities) {
    }

    @GetMapping("/min")
    public UserDetailsDTO getPrincipal() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }

        return new UserDetailsDTO(((UserDetails) auth.getPrincipal()).getUsername(),
                authorities);
    }

    @GetMapping()
    public User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return (User) userService.getUserByUsername(username);

    }

}
