package org.example.preproject231.dto.mapper;

import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Преобразует UserAuthDTO в User (Entity)
     */
    public User toEntity(UserAuthDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Преобразует User (Entity) в UserAuthDTO
     */
    public UserAuthDTO toAuthDto(User user) {
        if (user == null) {
            return null;
        }
        UserAuthDTO dto = new UserAuthDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
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