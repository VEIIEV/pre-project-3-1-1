package org.example.preproject231.dao;

import org.example.preproject231.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

}
