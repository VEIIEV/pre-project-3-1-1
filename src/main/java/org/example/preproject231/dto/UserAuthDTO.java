package org.example.preproject231.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAuthDTO implements Serializable {

    //todo настроить валидацию так, что при регистрации была обязаловка  всех полей
    // а при логите только первые 2
    private String username;
    private String password;
    private String repeatPassword;

    public static class Login {
    }

    public static class Registration {

    }
}
