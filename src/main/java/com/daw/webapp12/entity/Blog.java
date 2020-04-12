package com.daw.webapp12.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    private String title;
    @Lob 
    @Column(name="CONTENT", length=512)
    private String text;

    @ElementCollection
    private List<String> images = new ArrayList<>(5);
    
    public Blog(){}

    public Blog(String title, String text) {
        this.title = title;
        this.text = text;
        this.images =new ArrayList<>(5);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImages(){ return images;}

    public void setImages(List<String> images){
        this.images = images;
    }
}





