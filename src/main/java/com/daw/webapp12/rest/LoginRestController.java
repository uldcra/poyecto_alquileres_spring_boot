package com.daw.webapp12.rest;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.UserRepository;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
public class LoginRestController {

    private  static final Logger log = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    private UserComponent userComponent;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/login", method=RequestMethod.GET)
    public ResponseEntity<Users>login(){
        if (!userComponent.isLoggedUser()){
            log.info("usuario no registrado");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else {
            Users loggedUser=userService.findByName(userComponent.getLoggedUser().getName()).get();
            log.info("Entra");
            return new ResponseEntity<>(loggedUser,HttpStatus.OK);
        }
    }

    //@GetMapping(value="/login")
    //public ResponseEntity<Users> logIn() {
        //if (!userComponent.isLoggedUser()) {
            //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       // } else {
            //Users loggedUser = userComponent.getLoggedUser();
            //return new ResponseEntity<>(loggedUser, HttpStatus.OK);
       // }
  //  }



    @RequestMapping(value= "/api/logout", method= RequestMethod.GET)
    public ResponseEntity<Boolean>logout(HttpSession session){
        if (!userComponent.isLoggedUser()){
            log.info("Usuario no registrado");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else {
            session.invalidate();
            log.info("sesion finalizada");
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
    }

    // @GetMapping(value="/logOut")
    //public ResponseEntity<Boolean> logOut(HttpSession session) {
        //if (!userComponent.isLoggedUser()) {
            //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //} else {
        //    session.invalidate();
           // return new ResponseEntity<>(true, HttpStatus.OK);
        //}
    //}

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Users> register(@RequestBody Users users){
        System.out.printf(users.toString());
        users.setRoles(users.getRoles());
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        userRepository.save(users);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
