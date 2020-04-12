package com.daw.webapp12.controller;

import javax.servlet.http.HttpSession;

import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	private UserComponent userComponent;

    @RequestMapping("/login")
    public String login() {

        return "login";
    }
    @RequestMapping("/loginError")
    public String login(Model model) {
        model.addAttribute("Error", "Usuario o contraseña incorrecta");
        return "login";
    }


	@GetMapping("/logOut")
	public ResponseEntity<String> logOut(HttpSession session) {

		if (!userComponent.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>("Logged out", HttpStatus.OK);
		}
	}
}

