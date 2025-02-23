package org.example.preproject231.dto.mapper;

import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.entity.Role;
import org.example.preproject231.entity.User;
import org.example.preproject231.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    @Lazy
    @Autowired
    private UserService userService;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Преобразует UserAuthDTO в User (Entity)
     */
    public User toEntity(UserAuthDTO dto) {
        if (dto == null) {
            return null;
        }

        Set<Role> roles = null;
        if (dto.getAuthorities() != null) {
            roles = dto.
                    getAuthorities().
                    stream().map(auth ->
                            userService.getRoleByName(auth))
                    .collect(Collectors.toSet());
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRoles(roles);
        return user;

    }

    /**
     * Преобразует User (Entity) в UserAuthDTO
     */
    public UserAuthDTO toAuthDto(User user) {
        if (user == null) {
            return null;
        }
        List<String> authorities = new ArrayList<>();
        user.getRoles().stream().map(role -> role.getName().getValue()).forEach(authorities::add);

        UserAuthDTO dto = new UserAuthDTO();

        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAuthorities(authorities);
        return dto;
    }

    /**
     * Преобразует UserDto в User (Entity)
     */
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Преобразует User (Entity) в UserDto
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }


}