package org.example.preproject231.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.preproject231.validation.annotation.ExistInDb;
import org.example.preproject231.validation.annotation.UniqueUsernameAndEmail;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ExistInDb(groups = {UserAuthDTO.AdminCreation.class})
@UniqueUsernameAndEmail(groups = {UserAuthDTO.Registration.class, UserAuthDTO.AdminCreation.class})
public class UserAuthDTO implements Serializable {

    @NotEmpty(groups = {AdminCreation.class})
    private String firstName;

    @NotEmpty(groups = {AdminCreation.class})
    private String lastName;

    @NotEmpty(groups = {Login.class, Registration.class, AdminCreation.class})
    private String username;

    @NotEmpty(groups = {Login.class, Registration.class, AdminCreation.class})
    private String password;

    @Email
    @NotEmpty(groups = {Registration.class, AdminCreation.class})
    private String email;

    @NotEmpty(groups = {Registration.class})
    private String repeatPassword;

    private List<String> authorities;

    public interface Login {
    }

    public interface Registration {

    }

    public interface AdminCreation {

    }
}
