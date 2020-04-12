package com.daw.webapp12.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRep;
    @Autowired
    private UserComponent userComponent;


    @Override
     public Authentication authenticate(Authentication authentication)throws AuthenticationException {
       String username=authentication.getName();
       String password=(String)authentication.getCredentials();

        Optional<Users> user = userRep.findByName(username);

       if(user==null){
           System.out.println("No autenticado");
           throw new BadCredentialsException("User dont exist");
       }

       if (!new BCryptPasswordEncoder().matches(password,user.get().getPassword())){
           throw new BadCredentialsException("Fail password");
       }else{
           userComponent.setLoggedUser(user.get());
           List<GrantedAuthority> roles=new ArrayList<>();
           for(String role:user.get().getRoles()){
               roles.add(new SimpleGrantedAuthority(role));
           }
           return new UsernamePasswordAuthenticationToken(username,password,roles);
       }
    }
    
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}