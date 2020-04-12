package com.daw.webapp12.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.AdvertisementService;
import com.daw.webapp12.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    UserComponent userComponent;

    @Autowired
    AdvertisementService advertisementService;

    @GetMapping("/list")
    public List<Users> getUsers(){
    	return userService.findAll() ;
    }
    
    @GetMapping(path = "/loginDos")
   	@ResponseStatus(code = HttpStatus.OK)
   	@ResponseBody
   	public Optional<Users> findByEmailOrName(@RequestParam String email, @RequestParam String password) {
   		System.out.println("email o nombre" + email);
   		//String data ="email: "+ email+ " name: " + name;
   		
   		return  userService.findByEmailAddress(email, password);
   		
   		//return  null;
   		
   	}
    
    @GetMapping(path = "/loginTres")
   	@ResponseStatus(code = HttpStatus.OK)
   	@ResponseBody
   	public Users findByEmail(@RequestParam String email, @RequestParam String password) {
    	String encryptPassw =  new BCryptPasswordEncoder().encode(password);
    	if(userService.findByEmail(email, password) == null ) {
    		System.out.println("no hay resultados");
    		return null;
    	}
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	//Users user = userService.findByEmail(email, password);
    	Users user = userService.findById( userService.findByEmail(email, password).getId() );
    	
    	System.out.println("encoder.matches(password, user.getPassword(): " + encoder.matches(password, user.getPassword() ));
    	
    	
    	
    	if(encoder.matches(password, user.getPassword())) {
    	   user.setPassword("XD");
    	   return user;
    	}else{
    		System.out.println("no coinciden los passwords");
    	   return null;
    	}
   		//String encryptPassw =  new BCryptPasswordEncoder().encode(password);
   		//System.out.println("email o nombre: " + email +" password: "+ encryptPassw);
   		//System.out.println("userServiceEmail: " + userService.findByEmail(email, password) );
   		//return userService.findById(userService.findByEmail(email, password).getId());
   		//return  userService.findByEmail(email, password);
   		
   		//return  null;
   		
   	}
  @GetMapping("/{id}")
  public Users encontrarId(@PathVariable long id) {
	 
	  return userService.findById(id);
  }
  
  @GetMapping("/favoritos/{id}")
  public List<Advertisement> encontrarFavoritos(@PathVariable long id) {
	 
	  Users user= userService.findById(id);
	  return user.getMyFavourites();
  }
  
  @PostMapping(path = "/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Users create(@RequestBody Users user) {
	  	List<String> roles = new ArrayList<String>();
	  	roles.add("ROLE_USER");
	  	
	  	user.setRoles(roles);
		return userService.addUser(user);
		
	}
  
	@GetMapping(path = "/addfavo")
	@ResponseStatus(code = HttpStatus.OK)
	public Users updateFavo(@RequestParam Long id, @RequestParam Long id_advfo) {
		Users user1 = userService.findById(id);
		Advertisement advert = advertisementService.findById(id_advfo);
      //Advertisement anun2 = new Advertisement("Venta","Casa",(Integer)4,(Integer)2,120,"Madrid","calle azul,2",(double)200000);
		if (user1.getMyAdvertisements().contains(advert) ) {
			return null;
		}
		List<Advertisement> listAdv = user1.getMyAdvertisements();
		listAdv.add(advert);
		user1.setMyAdvertisements(listAdv);
		return userService.addUser(user1);
	}
	
	@GetMapping(path = "/removeFavo")
	@ResponseStatus(code = HttpStatus.OK)
	public Users removeFavo(@RequestParam Long id, @RequestParam Long id_advfo) {
		Users user1 = userService.findById(id);
		
		Advertisement advert = advertisementService.findById(id_advfo);
		System.out.println("user1 " + user1.getName() );

		System.out.println("advertisementService.findById(id_advfo) " + advertisementService.findById(id_advfo).getaddress() );
		if (user1.getMyAdvertisements().contains(advert) ) {
			user1.getMyAdvertisements().remove(advert);
			return userService.addUser(user1);
			
			 
		}
		//user1.removeMyAdvertisements(advert);
		return null;
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<Users> addFavorite(@PathVariable long id) {
    	System.out.println("Dentro de favoritos");
        if (userComponent.isLoggedUser()) {
            String userName = userComponent.getLoggedUser().getName();
            Optional<Users> user = userService.findByName(userName);
            Advertisement adv = advertisementService.findById(id);
            if (user.get().getMyFavourites().contains(adv)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                user.get().addFavourite(adv);
                userService.addUser(user.get());
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/favourites/{id}")
    public ResponseEntity<Users> deleteFromFavourite(@PathVariable long id) {

        String userName = userComponent.getLoggedUser().getName();
        Optional<Users> user = userService.findByName(userName);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.get().deleteFavourite(id);
        userService.addUser(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("/advertisements/{id}")
    public ResponseEntity<Users> deleteMyAdvertisement(@PathVariable long id) {

        String userName = userComponent.getLoggedUser().getName();
        Optional<Users> user = userService.findByName(userName);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.get().deleteOneAdvertisement(id);
        userService.addUser(user.get());
        advertisementService.deleteAdvertisement(id);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // public ResponseEntity<List<Advertisement>> favAdvertisements(@PathVariable long id) {
    //     Optional<Users> user = Optional.ofNullable(userService.findById(id));
    //     if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     return new ResponseEntity<>(user.get().getMyFavourites(), HttpStatus.OK);
    // }

    @RequestMapping(value = "/favourites", method = RequestMethod.GET)
    public ResponseEntity<List<Advertisement>> favAdvertisements(/*@RequestParam("id") long idAdver, */@RequestParam(value="page") int page,@RequestParam(value="number") int number) {
    System.out.println("userComponent.getLoggedUser().getName(): " + userComponent.getLoggedUser().getName());        
    return null;
   /* Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
         List<Advertisement> myFavs = user.getMyFavourites(page,number);
         if(myFavs != null){
             return new ResponseEntity<>(myFavs, HttpStatus.OK);
         }else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }*/
     }
   
}





