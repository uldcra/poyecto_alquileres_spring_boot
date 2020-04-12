package com.daw.webapp12.rest;

import java.util.ArrayList;
import java.util.List;

import com.daw.webapp12.entity.Blog;
import com.daw.webapp12.service.BlogService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/blogs")
public class BlogRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
	private BlogService blogService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> getBlog(@PathVariable long id){
        Blog blog = blogService.findById(id);
        if(blog != null){
            return new ResponseEntity<>(blog, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Blog>> getBlogs(@RequestParam(value="page") int page,@RequestParam(value="number") int number){
        List<Blog> blogs = blogService.findAll(page,number);
        //List<Blog> blogs =  blogService.findAll();
        if(blogs.size()>0){
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Blog blogUpload( @RequestParam MultipartFile[] multipartFile,  @RequestParam String title,  @RequestParam String description){
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
        Blog blog = new Blog(title, description);
		blog.setImages(files);
        blogService.addBlog(blog);

        return blog;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable long id){
        Blog blog = blogService.findById(id);
        if(blog != null){
            List<String> auxImages = new ArrayList<String>();
            if(!blog.getImages().isEmpty()){
                auxImages = blog.getImages();      
            }
            blogService.deleteBlog(id);
            blog.setImages(auxImages);

            return new ResponseEntity<>(blog, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Blog> blogUpdate(@PathVariable long id, @RequestBody Blog newBlog){
        Blog blog =  blogService.findById(id);
        if(blog != null){
            newBlog.setId(id);
            blogService.addBlog(newBlog);
            return new ResponseEntity<>(newBlog, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

}
