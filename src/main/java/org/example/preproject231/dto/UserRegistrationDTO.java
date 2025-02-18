package org.example.preproject231.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRegistrationDTO implements Serializable {
    private String username;
    private String password;
}
