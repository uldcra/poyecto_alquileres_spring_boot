package com.daw.webapp12.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@Order(1)
public class SecurityApiRest extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**");

        // here urls need authentication
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/loginTres").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/loginDos").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/list").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/login").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/login").permitAll();;

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/blogs").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/blogs/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/blogs/{id}").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/blogs/{id}").hasAnyRole("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/advertisements/list").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/advertisements").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/advertisements/{id}/comments").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/advertisements/{id}/comments").hasAnyRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/advertisements/{id}/comments/{id}").hasAnyRole("USER","ADMIN");


        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/main/images").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/main/images/{fileName:.+}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/advertisements").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/advertisements").hasAnyRole("USER", "ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/advertisements/{id}").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/favourites/{id}").hasAnyRole("USER", "ADMIN");
       // http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("USER", "ADMIN");
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "ADMIN");


        // urls not need authentication
        http.authorizeRequests().anyRequest().permitAll();

        // disable csrf
        http.csrf().disable();

        http.httpBasic();

        //do not redirect when logout
        http.logout().logoutSuccessHandler((rq, rs, a) -> {
        });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager.authenticationProvider(authenticationProvider);
    }

}
