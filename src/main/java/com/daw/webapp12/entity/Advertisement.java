

package com.daw.webapp12.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "advertisement")
public class Advertisement
 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    private String type;

    private String property;

    private Integer rooms;

    private Integer bathrooms;

    private Integer  squareMeters;

    private String location;

    private String address;

    private double price;

    private String picture;

    /*
    @ElementCollection
    private List<String> images = new ArrayList<>(5);*/
    @ElementCollection
    private List<String> images = new ArrayList<>(5);


    @OneToMany
    private List<Comment> comments;


    public Advertisement(){}

    public Advertisement(String type, String property,  Integer rooms, Integer bathrooms, int squareMeters,  String location, String address, double price) {
        this.type = type;
        this.property = property;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.squareMeters = squareMeters;
        this.location = location;
        this.address = address;
        this.price = price;
        this.comments = new ArrayList<Comment>();
    }

     public Advertisement(String type, String property,  Integer rooms, Integer bathrooms, int squareMeters,  String location, String address, double price, String picture) {
         this.type = type;
         this.property = property;
         this.rooms = rooms;
         this.bathrooms = bathrooms;
         this.squareMeters = squareMeters;
         this.location = location;
         this.address = address;
         this.price = price;
         this.comments = new ArrayList<Comment>();
         this.picture = picture;
     }

     public Advertisement(String type, String property,  Integer rooms, Integer bathrooms, int squareMeters,  String location, String address, double price, List<String> images) {
         this.type = type;
         this.property = property;
         this.rooms = rooms;
         this.bathrooms = bathrooms;
         this.squareMeters = squareMeters;
         this.location = location;
         this.address = address;
         this.price = price;
         this.images = images;
         this.comments = new ArrayList<Comment>();
     }

     public Advertisement(@NotNull String type, String property, Integer rooms, Integer bathrooms, Integer squareMeters, String location, String address, double price, String picture, List<String> images, List<Comment> comments) {
         this.type = type;
         this.property = property;
         this.rooms = rooms;
         this.bathrooms = bathrooms;
         this.squareMeters = squareMeters;
         this.location = location;
         this.address = address;
         this.price = price;
         this.picture = picture;
         this.images = images;
         this.comments = comments;
     }

     public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getproperty() {
        return property;
    }
    public void setComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void deleteComment(Long id){
        for(int i=0;i<comments.size();i++){
            if(comments.get(i).getId()==id){
                comments.remove(i);
            }
        }
    }

    public List<Comment> getComments(int page, int number) {
        List<Comment> pagedList = new ArrayList<>();
        int start = page * number;
        int end = start + number;
        if((comments.size() > 0) && (end - comments.size()) < number){
            for(int i = start;i< end;i++){
                if(i+1 > comments.size()){
                    break;
                }
                pagedList.add(comments.get(i));
            }
            return pagedList;
        }else{
            return null;
        }
    }

    public void setproperty(String property) {
        this.property = property;
    }

    public Integer getrooms() {
        return rooms;
    }

    public void setrooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getbathrooms() {
        return bathrooms;
    }

    public void setbathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getsquareMeters() {
        return squareMeters;
    }

    public void setsquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public double getprice() {
        return price;
    }

    public void setprice(double price) {
        this.price = price;
    }

    public List<String> getImages(){ return images;}

    public void setImages(List<String> images){this.images = images;}

     public String getPicture(){ return picture;}

     public void setPicture(String picture){this.picture = picture;}
}
