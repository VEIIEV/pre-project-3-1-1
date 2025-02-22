package org.example.preproject231.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(nullable = false, unique = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private enumRole name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    @Getter
    public enum enumRole {

        ADMIN("ADMIN"),
        USER("USER");

        private final String value;

        enumRole(String value) {
            this.value = value;
        }

    }
}
