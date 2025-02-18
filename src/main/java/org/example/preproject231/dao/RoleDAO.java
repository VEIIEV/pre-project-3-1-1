package org.example.preproject231.dao;


import org.example.preproject231.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleDAO extends JpaRepository<Role, Long> {

    Role getRoleByName(Role.enumRole name);

}
