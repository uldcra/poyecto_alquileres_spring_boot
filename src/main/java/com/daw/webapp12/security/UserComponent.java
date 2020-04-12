package com.daw.webapp12.security;

import com.daw.webapp12.entity.Users;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserComponent {

	private Users user;

	public Users getLoggedUser() {
		return user;
	}

	public String getUserRole(){
		return getLoggedUser().getRoles().get(0);
	}

	public void setLoggedUser(Users user) {
		this.user = user;
	}

	public boolean isLoggedUser() {
		return this.user != null;
    }
}