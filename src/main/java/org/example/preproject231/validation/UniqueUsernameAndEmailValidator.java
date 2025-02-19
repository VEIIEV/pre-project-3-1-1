package org.example.preproject231.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.preproject231.dao.UserDao;
import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.entity.User;
import org.example.preproject231.validation.annotation.UniqueEmail;
import org.example.preproject231.validation.annotation.UniqueUsernameAndEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UniqueUsernameAndEmailValidator implements ConstraintValidator<UniqueUsernameAndEmail, UserAuthDTO> {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean isValid(UserAuthDTO userDto, ConstraintValidatorContext constraintValidatorContext) {

        Optional<User> userOptional = userDao.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
        return userOptional.isEmpty();
    }
}
