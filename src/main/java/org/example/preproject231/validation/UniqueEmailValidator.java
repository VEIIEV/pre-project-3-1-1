package org.example.preproject231.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.preproject231.dao.UserDao;
import org.example.preproject231.dto.UserDto;
import org.example.preproject231.entity.User;
import org.example.preproject231.validation.annotation.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDto> {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {

        Optional<User> userOptional = userDao.findByEmail(userDto.getEmail());
        return userOptional.isEmpty(); //|| new UserDto(userOptional.get()).equals(userDto);
    }
}
