package org.example.preproject231.dao;


import org.example.preproject231.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role getRoleByName(Role.enumRole name);

    @Query("select r from Role r where r.name in ?1")
    Set<Role> findByNameIn(Set<String> collect);
}
