package com.daw.webapp12.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String name;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Advertisement> myFavourites;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Search> mySearches;

    @ManyToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Advertisement> myAdvertisements;

    public Users(){
        this.myFavourites = new ArrayList<Advertisement>();
        this.myAdvertisements = new ArrayList<Advertisement>();
        this.mySearches = new ArrayList<Search>();
    }

    public Users(String name, String email, String password,String... roles) {
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
		this.roles = new ArrayList<>(Arrays.asList(roles));
        this.myFavourites = new ArrayList<Advertisement>();
        this.myAdvertisements = new ArrayList<Advertisement>();
        this.mySearches = new ArrayList<Search>();
    }

    public void addFavourite(Advertisement advertisement){
        this.myFavourites.add(advertisement);
    }

    public void addMyAdvertisement(Advertisement advertisement){
        this.myAdvertisements.add(advertisement);
    }

    public void deleteFavourite(Long id){
        for(int i=0;i<myFavourites.size();i++){
            if(myFavourites.get(i).getId()==id){
                myFavourites.remove(i);
            }
        }
    }


    public void deleteOneAdvertisement(Long id){
        for(int i=0;i<myAdvertisements.size();i++){
            if(myAdvertisements.get(i).getId()==id){
                myAdvertisements.remove(i);
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public List<Advertisement> getMyFavourites() {
        return myFavourites;
    }

    public List<Advertisement> getMyFavourites(int page, int number) {
        List<Advertisement> pagedList = new ArrayList<>();
        int start = page * number;
        int end = start + number;
        if((myFavourites.size() > 0) && (end - myFavourites.size()) < number){
            for(int i = start;i< end;i++){
                if(i+1 > myFavourites.size()){
                    break;
                }
                pagedList.add(myFavourites.get(i));
            }
            return pagedList;
        }else{
            return null;
        }
    }

    public void setMyFavourites(List<Advertisement> list) {
        this.myFavourites = list;
    }

    public List<Advertisement> getMyAdvertisements() {
        return myAdvertisements;
    }
    public List<Advertisement> getMyAdvertisements(int page, int number) {
        List<Advertisement> pagedList = new ArrayList<Advertisement>();
        int start = page * number;
        int end = start + number;
        return getMyAdvertisements();
       /* if((myAdvertisements.size() > 0) && (end - myAdvertisements.size()) < number){
            for(int i = start;i< end;i++){
            	Advertisement advertisement = new Advertisement();
                if(i+1 > myAdvertisements.size()){
                    break;
                }
                pagedList.add(myAdvertisements.get(i));
            }
            return pagedList;
        }else{
            return null;
        }*/
    }

    public void setMyAdvertisements(List<Advertisement> list) {
        this.myAdvertisements = list;
    }

    public List<Search> getMySearches() {
        return mySearches;
    }

    public void setMySearches(List<Search> list) {
        this.mySearches = list;
    }
}
