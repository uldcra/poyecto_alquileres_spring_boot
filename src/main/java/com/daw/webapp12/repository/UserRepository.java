package com.daw.webapp12.repository;

import com.daw.webapp12.entity.Users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByName(String string);
    
    Optional<Users> findByNameOrEmailAndPassword(String name,String email , String Password);
    //Optional<Users> findByEmail(String name,String email , String Password);
    
    @Query(value = "SELECT * FROM clases.users where (email= ?1 or name=?1);", nativeQuery = true)
    Users findByEmail(String email, String password);
    
    @Query(value = "SELECT * FROM clases.users where id= ?1;", nativeQuery = true)
    Optional<Users> findByMyId(Long id);
}
