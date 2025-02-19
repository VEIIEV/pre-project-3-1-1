package org.example.preproject231.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.preproject231.validation.annotation.UniqueUsernameAndEmail;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UniqueUsernameAndEmail(groups = {UserAuthDTO.Registration.class})
public class UserAuthDTO implements Serializable {

    @NotEmpty(groups = {Login.class, Registration.class})
    private String username;

    @NotEmpty(groups = {Login.class, Registration.class})
    private String password;

    @Email
    @NotEmpty(groups = {Registration.class})
    private String email;

    @NotEmpty(groups = {Registration.class})
    private String repeatPassword;

    public interface Login {
    }

    public interface Registration {

    }
}
