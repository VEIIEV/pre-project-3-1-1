package org.example.preproject231.dao;

import org.example.preproject231.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    List<User> findAll(Sort sort);



    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    Optional<UserDetails> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE (u.username = ?1 OR u.email = ?2) AND u.id <> ?3")
    Optional<User> findByUsernameOrEmailExcludingCurrent(String username, String email, Long userId);
}
