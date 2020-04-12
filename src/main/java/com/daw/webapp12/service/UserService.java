package com.daw.webapp12.service;

import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender sender;
    
    @Override
    public List<Users> findAll() {
        return  userRepository.findAll();
    }

    @Override
    public Optional<Users> findByName(String string) {
        return userRepository.findByName(string);
    }

    @Override
    public Users addUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void deleteUser(Users users) {
        userRepository.delete(users);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void sendEmail(Users userInfo, String pass) throws Exception{
        String email = "Acabas de registrarte como: " + "\nUsuario: " + userInfo.getName() + "\nContraseña: " + pass +
                "\n" + "\nTodos tus facturas serán enviadas al siguiente correo electrónico: " + userInfo.getEmail() +
                "\n" + "\nCualquier duda que tengas no dudes en contactarnos a través de nuestro correo electrónico.";

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
       // ClassPathResource file = new ClassPathResource("/static/img/tick.png");
        //helper.addAttachment("/static/img/tick.png", file);
        helper.setTo(userInfo.getEmail());
        helper.setText(email);
        helper.setSubject("¡Bienvenid@ a Ruvik! ");

        sender.send(message);
    }
   
    @Override
    @Transactional
	public Optional<Users> findByEmailAddress(String email,String password) {
		
		return userRepository.findByNameOrEmailAndPassword(email, email, password);
	}
    
    @Override
    @Transactional
   	public Users findByEmail(String email, String password) {
   		System.out.println("password: " + password );
   		return  userRepository.findByEmail(email, password);
   	}
}
