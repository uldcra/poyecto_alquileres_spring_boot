package com.daw.webapp12.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;
    
    

    @Autowired
    public void configurerGlobal (AuthenticationManagerBuilder builder) throws Exception{

        PasswordEncoder encoder = passwordEncoder();
        UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
        builder.inMemoryAuthentication()
                .withUser(users.username("admin").password("admin").roles("ADMIN","USER"))
                .withUser(users.username("angel").password("12345").roles("USER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //users 
        http.authorizeRequests().antMatchers("/MainPage").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/loginDos").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/signUp").permitAll();

        http.authorizeRequests().antMatchers("/signUp").permitAll();
        http.authorizeRequests().antMatchers("/register").permitAll();


        //only admin & register users
        http.authorizeRequests().antMatchers("/property-upload").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/properties-edit").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/properties").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/property-modificar").hasAnyRole("USER", "ADMIN");
        

        http.formLogin().loginPage("/login").permitAll();
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/MainPage");
        http.formLogin().failureUrl("/loginError");
        http.logout().permitAll();

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    throws Exception {
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}

