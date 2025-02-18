package org.example.preproject231.dao;

import org.example.preproject231.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

     @Query("select u from User u where u.email = ?1")
     Optional<User> findByEmail(String email);

}
