package com.daw.webapp12.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "search")
public class Search
 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String type;

    private String property;

    private Integer rooms;

    private Integer bathrooms;

    private int squareMeters;

    private String location;

    private String address;

    private double price;

    public Search(){}

    public Search(String type, Integer rooms, Integer bathrooms, int squareMeters,  String location, double price) {
        this.type = type;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.squareMeters = squareMeters;
        this.location = location;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getproperty() {
        return property;
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

    public void setlocalization(String location) {
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
}
