package com.daw.webapp12.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UserComponent userComponent;
    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/properties-modificar")
    public String misAnuncios(Model model) {
        if (userComponent.isLoggedUser()) {
            String userName = userComponent.getLoggedUser().getName();
            Optional<Users> user = userService.findByName(userName);
            if(user.get().getMyAdvertisements().size()>0){
                model.addAttribute("myAds", user.get().getMyAdvertisements());
            }else{
                model.addAttribute("Error", "No tienes ningun anuncio publicado.");
            }
            
            return "properties-modificar";
        } else {
            return "login";
        }

    }

    @PostMapping(value = "/property-upload")
    public String misAnuncios(Advertisement advertisement, Model model, @RequestParam("file") MultipartFile[] multipartFile, @RequestParam String location) {
        List<String> files = new ArrayList<>(5);
        for (int i = 0; i < multipartFile.length; i++) {
            if (!multipartFile[i].isEmpty()) {
                //Path rootPath = Paths.get("src//main/resources//static//images").resolve(multipartFile.getOriginalFilename());
                //Path rootAbsolutePath = rootPath.toAbsolutePath();
                //Path directorioRecursos = Paths.get("daw.webapp12//src//main//resources//static//images");
                Path directorioRecursos = Paths.get("src//main//resources//static//images");
                String rootPath = directorioRecursos.toFile().getAbsolutePath();
                log.info("rootPath: " + rootPath);
                //log.info("rootAbsolutePath: " + rootAbsolutePath);
                try {
                    //Files.copy(multipartFile.getInputStream(), rootAbsolutePath);
                    //advertisement.setImages(multipartFile.getOriginalFilename());
                    //C:\Users\Maria\Documents\webapp12\daw.webapp12\src\main\resources\static\images
                    byte[] bytes = multipartFile[i].getBytes();
                    Path rupacompleta = Paths.get(rootPath + "//" + multipartFile[i].getOriginalFilename());
                    Files.write(rupacompleta, bytes);
                    files.add(multipartFile[i].getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        advertisement.setImages(files);
        advertisement.setlocation(location);
        advertisementRepository.save(advertisement);
        Optional<Users> thisUser = userService.findByName(userComponent.getLoggedUser().getName());
        thisUser.get().getMyAdvertisements().add(advertisement);
        userService.addUser(thisUser.get());

        return "redirect:/MainPage";
    }

    @RequestMapping(value = "/property-upload", method = RequestMethod.GET)
    public String ponerAnuncios() {
        return "property-upload";
    }

    @ModelAttribute
    public void addUserToModel(Model model){
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        if(logged){
            model.addAttribute("admin",userComponent.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
            model.addAttribute("user",userComponent.getLoggedUser().getRoles().contains("ROLE_USER"));
           //model.addAttribute("logged", logged);
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String returnAngular(Model model){
        return "../public/new/index";
    }

}
