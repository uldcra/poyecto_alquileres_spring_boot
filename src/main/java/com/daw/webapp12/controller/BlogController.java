package com.daw.webapp12.controller;

import java.util.ArrayList;
import java.util.List;

import com.daw.webapp12.entity.Blog;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class BlogController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private BlogService blogService;
	@Autowired
	UserComponent userComponent;

	@RequestMapping(value = "/blog")
    public String blogs(Model model) {
	 	model.addAttribute("blogs", blogService.findAll());
        return "blog";
	}
	@RequestMapping(value = "/blog/{id}")
    public String blogSingle(Model model, @PathVariable Long id) {
	 	model.addAttribute("Blog", blogService.findById(id));
        return "blog-single";
    }


	@RequestMapping(value = "/new-blog")
    public String newBlog(Model model) {
	 	//model.addAttribute("blogs", blogService.findAll());
        return "blog-upload";
	}


	@PostMapping(value = "/blog-upload")
    public String blogUpload(Model model, @RequestParam("file") MultipartFile[] multipartFile, @RequestParam String title, @RequestParam String description){
        List<String> files = new ArrayList<String>(5);
        for (int i = 0; i < multipartFile.length; i++) {
            if (!multipartFile[i].isEmpty()) {
                Path directorioRecursos = Paths.get("daw.webapp12//src//main/resources//static//images");
                String rootPath = directorioRecursos.toFile().getAbsolutePath();
                log.info("rootPath: " + rootPath);
                try {
                    byte[] bytes = multipartFile[i].getBytes();
                    Path rupacompleta = Paths.get(rootPath + "//" + multipartFile[i].getOriginalFilename());
                    Files.write(rupacompleta, bytes);
                    files.add(multipartFile[i].getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		Blog blog = new Blog(title,description);
		blog.setImages(files);
		blogService.addBlog(blog);
        return "redirect:/blog";
	}

	@RequestMapping("/deleteBlog/{id}")
    public String deleteBlog(Model model,@PathVariable long id){
		blogService.deleteBlog(id);
        return "redirect:/blog";
	}

	@RequestMapping("/editBlog/{id}")
    public String editBlog(Model model, @PathVariable  long id) {
		model.addAttribute("editBlog", blogService.findById(id));
        return "blog-edit";
	}

	@PostMapping("/editBlog/{id}")
    public String editBlog(Model model,Blog blog,@PathVariable  long id,@RequestParam("file") MultipartFile[] multipartFile, @RequestParam String title,@RequestParam String description) {
		List<String> files = new ArrayList<>(5);
        for (int i = 0; i < multipartFile.length; i++) {
            if (!multipartFile[i].isEmpty()) {
                Path directorioRecursos = Paths.get("daw.webapp12//src//main/resources//static//images");
                String rootPath = directorioRecursos.toFile().getAbsolutePath();
                try {
                    byte[] bytes = multipartFile[i].getBytes();
                    Path rupacompleta = Paths.get(rootPath + "//" + multipartFile[i].getOriginalFilename());
                    Files.write(rupacompleta, bytes);
                    files.add(multipartFile[i].getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        blog.setImages(files);
		blogService.addBlog(blog);
        return "redirect:/blog";
	}
	
	@ModelAttribute
    public void addUserToModel(Model model){
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        if(logged){
            model.addAttribute("admin",userComponent.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
            model.addAttribute("user",userComponent.getLoggedUser().getRoles().contains("ROLE_USER"));
        }
	}
}