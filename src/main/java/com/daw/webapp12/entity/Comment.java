package com.daw.webapp12.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    //@NotNull
    private String author;


    private String message;

    public Comment(String message) {
       // this.author = author;
        this.message = message;
        //this.advertisement = advertisement;
    }

    public Comment() {}

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}