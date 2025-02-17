package org.example.preproject231.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.preproject231.entity.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;

    public UserDto(User userFromDb) {
        this.firstName = userFromDb.getFirstName();
        this.lastName = userFromDb.getLastName();
        this.email = userFromDb.getEmail();
    }

    @Override
    public String toString() {
        return "CreatedUserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}