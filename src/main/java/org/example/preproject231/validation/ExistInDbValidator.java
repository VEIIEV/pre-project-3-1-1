package org.example.preproject231.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.preproject231.dto.UserAuthDTO;
import org.example.preproject231.entity.Role;
import org.example.preproject231.validation.annotation.ExistInDb;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class ExistInDbValidator implements ConstraintValidator<ExistInDb, UserAuthDTO> {


    @Override
    public boolean isValid(UserAuthDTO userAuthDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<String> rolesName = Stream.of(Role.enumRole.values()).map(Role.enumRole::getValue).toList();
        return new HashSet<>(rolesName).containsAll(userAuthDTO.getAuthorities());
    }
}
