package com.daw.webapp12.service;

import com.daw.webapp12.entity.Users;


import java.util.List;
import java.util.Optional;

public interface UserInterface {

    public List<Users> findAll();

    public Users addUser (Users users);

    public void deleteUser (Users users);

    public Users findById(Long id);

    public Optional<Users> findByName(String string);

    public Optional<Users> findByEmailAddress(String email, String name);

	Users findByEmail(String email, String password);
    
    
}
