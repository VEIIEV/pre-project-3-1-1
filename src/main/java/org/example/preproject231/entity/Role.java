package org.example.preproject231.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(nullable = false, unique = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private enumRole name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    @Getter
    public enum enumRole {
        USER("user"),
        ADMIN("admin");

        private final String value;

        enumRole(String value) {
            this.value = value;
        }

    }
}
