package org.example.preproject231.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.preproject231.entity.User;
import org.example.preproject231.validation.annotation.UniqueEmail;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UniqueEmail
public class UserDto {

    private String firstName;
    private String lastName;

    @Email
    private String email;

    public UserDto(User userFromDb) {
        this.firstName = userFromDb.getFirstName();
        this.lastName = userFromDb.getLastName();
        this.email = userFromDb.getEmail();
    }

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    @Override
    public String toString() {
        return "CreatedUserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDto userDto = (UserDto) object;
        return Objects.equals(getFirstName(), userDto.getFirstName()) && Objects.equals(getLastName(), userDto.getLastName()) && Objects.equals(getEmail(), userDto.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail());
    }
}