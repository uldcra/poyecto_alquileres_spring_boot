package com.daw.webapp12.rest;

import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.entity.Comment;
import com.daw.webapp12.entity.Search;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.repository.CommentRepository;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.AdvertisementService;
import com.daw.webapp12.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/advertisements")
public class AdvertisementRestController {

    @Autowired
    AdvertisementService advertisementService;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserComponent userComponent;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/list")
    public List<Advertisement> list() {
		List<Advertisement> ads = advertisementService.findAll();
		return ads;
		}
    
    @GetMapping("/")
    public List<Advertisement> allAdvertisement(/*@RequestParam("id") long idAdver, */@RequestParam(value="page") int page,@RequestParam(value="number") int number) {
    //    Users users = userService.findById(idAdver);
    //     List<Advertisement> myAds = users.getMyAdvertisements();
    //     return myAds;
    	System.out.println("userComponent.getLoggedUser().getName(): "+ userComponent.getLoggedUser().getName());
        Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
        List<Advertisement> myAds = user.getMyAdvertisements(page,number);
        if(myAds != null){
            return myAds;
        }else{
            return null;
        }
    }
    // public List<Advertisement> allAdvertisement(@RequestParam("id") long id) {
    //     Users users = userService.findById(id);
    //     List<Advertisement> myAds = users.getMyAdvertisements();
    //     return myAds;
    // }

    @PostMapping("/")
    public List<Advertisement> uploadsAdvertisement(Advertisement anuncios, @RequestParam("id") long id) {
        Users users = userService.findById(id);
        List<Advertisement> myAds = users.getMyAdvertisements();
        advertisementRepository.save(anuncios);
        myAds.add(anuncios);
        return myAds;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> editAdvertisement(@PathVariable long id, @RequestBody Advertisement newAdvertisement){
        Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
        if(user.getMyAdvertisements().contains(advertisementService.findById(id))) {
            Advertisement advertisement = advertisementService.findById(id);
            if (advertisement != null) {
                newAdvertisement.setId((int) id);
                advertisementService.addAdvertisement(newAdvertisement);
                return new ResponseEntity<>(newAdvertisement, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{id}")
    public Advertisement getAdvertisement(@PathVariable long id) {
        Advertisement myAds = advertisementService.findById(id);
        return myAds;
    }

    @GetMapping("/recommended")
    public List<Advertisement> recommendeds() {
		List<Advertisement> ads = advertisementService.findAll();
		return ads;
		/*
		List<Advertisement> auxAdvertisements = new ArrayList<Advertisement>();
		for(int i = 0;i<ads.size();i++){
			auxAdvertisements.add(ads.get(i));
		}
		List<Search> searches = userService.findByName(userComponent.getLoggedUser().getName()).get().getMySearches();
		HashMap<Double,Integer> scores = new HashMap<Double,Integer>();
		List<Advertisement> recommendeds = new ArrayList<Advertisement>();
		List<String> typeOfSearches = new ArrayList<String>();
		int roomMean = 0;
		int bathroomMean = 0;
		int squareMetersMean = 0;
		List<String> locationsList = new ArrayList<String>();
		double sellPriceMean = 0;
		int numSells = 0;
		int numRents = 0;
		double rentPriceMean = 0;
		double score = 0;
		String typeOfRecommendation;
		String lastTwoSearches ="";

		String firstSearch = searches.get(searches.size()-1).getType();
		String secondSearch = searches.get(searches.size()-2).getType();

		if(firstSearch.equals("Alquiler") && secondSearch.equals("Alquiler")){
			lastTwoSearches = "Alquiler";
		}else if(firstSearch.equals("Venta") && secondSearch.equals("Venta")){
			lastTwoSearches = "Venta";
		}
		for(int i = 0;i<searches.size();i++){
			
			Search auxSearch = searches.get(i);
			roomMean += auxSearch.getrooms();
			bathroomMean += auxSearch.getbathrooms();
			squareMetersMean += auxSearch.getsquareMeters();
			locationsList.add(auxSearch.getlocation());
			if(auxSearch.getType().equals("Alquiler")){
				rentPriceMean += auxSearch.getprice();
				numRents = numRents+1;
			}else{
				sellPriceMean += auxSearch.getprice();
				numSells = numSells+1;	
			}
			
			if(searches.get(i).getType().equals("Alquiler") && !typeOfSearches.contains("Alquiler")){
				typeOfSearches.add("Alquiler");
			}else if(searches.get(i).getType().equals("Venta") && !typeOfSearches.contains("Venta")){
				typeOfSearches.add("Venta");
			}
		}

		if(typeOfSearches.contains("Alquiler") && typeOfSearches.contains("Venta")){
			typeOfRecommendation = "Both";
		}else if(typeOfSearches.contains("Alquiler") && !typeOfSearches.contains("Venta")){
			typeOfRecommendation = "Alquiler";
		}else{
			typeOfRecommendation = "Venta";
		}

		if(!typeOfRecommendation.equals("Both")){
			for(int i = 0;i<auxAdvertisements.size();i++){
				Advertisement auxAd = auxAdvertisements.get(i);
				if(typeOfRecommendation.equals("Alquiler") && auxAd.gettype().equals("Venta")){
					auxAdvertisements.remove(i);
					i= i-1;
				}else if(typeOfRecommendation.equals("Venta") && auxAd.gettype().equals("Alquiler")){
					auxAdvertisements.remove(i);
					i= i-1;
				}
			}
		}
		
		roomMean = roomMean / searches.size();
		bathroomMean = bathroomMean / searches.size();
		squareMetersMean = squareMetersMean / searches.size();
		rentPriceMean = rentPriceMean / numRents;
		sellPriceMean = sellPriceMean / numSells;

		for(int i = 0;i<auxAdvertisements.size();i++){
			Advertisement auxAd = auxAdvertisements.get(i);
			if(auxAd.getrooms() - roomMean ==0){
				score+= 2;
			}else{
				score+= (auxAd.getrooms() - roomMean) *2;
			}
			
			if(auxAd.getbathrooms() - bathroomMean ==0){
				score+= 2;
			}else{
				score+= (auxAd.getbathrooms() - bathroomMean) *2;
			}
			
			if(auxAd.getsquareMeters() - squareMetersMean ==0){
				score+= 2;
			}else{
				score+= ((auxAd.getsquareMeters() - squareMetersMean)/2) *2;
			}

			if(locationsList.contains(auxAd.getlocation())){
			 	score+=15;
			}

			if(auxAd.gettype().equals("Alquiler")){
				if(rentPriceMean - auxAd.getprice()>0){
					score+= ((rentPriceMean - auxAd.getprice())/50) *4;
				}
				if(lastTwoSearches.equals("Alquiler")){
					score = score *2.5;
				}else if(firstSearch.equals("Alquiler")){
					score = score *1.5;
				}
			}else if(auxAd.gettype().equals("Venta")){
					score+= ((sellPriceMean - auxAd.getprice())/5000) *2;
					if(lastTwoSearches.equals("Venta")){
						score = score * 2.25;
					}else if(firstSearch.equals("Venta")){
						score = score *1.2;
					}
			}
			scores.put(score, i);
			score = 0;
		}
			List<Double> mapKeys = new ArrayList<>(scores.keySet());
			Collections.sort(mapKeys);
			recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-1))));
			recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-2))));
			recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-3))));
		
			HashMap<String, Integer> mostCommonLocations = new HashMap<String, Integer>();
			for(int i = 0; i< ads.size();i++){
				String auxLocation = ads.get(i).getlocation();
				if(mostCommonLocations.containsKey(auxLocation)){
					int value = mostCommonLocations.get(auxLocation);
					mostCommonLocations.replace(auxLocation,value+1);
				}else{
					mostCommonLocations.put(auxLocation, 1);
				}
			}

			List<Map.Entry<String, Integer> > list = 
			new LinkedList<Map.Entry<String, Integer> >(mostCommonLocations.entrySet()); 

	 Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
		 public int compare(Map.Entry<String, Integer> o1,  
							Map.Entry<String, Integer> o2) 
		 { 
			 return (o2.getValue()).compareTo(o1.getValue());
		 } 
	 }); 
	   for(int i = 5;i<list.size();i++){
		list.remove(i);
       }
       return recommendeds;*/
    }
    
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> advertisementComments(@PathVariable long id, @RequestParam(value="page") int page,@RequestParam(value="number") int number) {
        Advertisement advert = advertisementService.findById(id);
        List<Comment> comments = advert.getComments(page, number);
        if(comments != null){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment blogUpload(@PathVariable long id, @RequestBody Comment comment){
        Advertisement advert = advertisementService.findById(id);
        if(userComponent.getLoggedUser()!=null){
            Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
            comment.setAuthor(user.getName());
        } 
        commentRepository.save(comment);
        advert.getComments().add(comment);
        advertisementService.addAdvertisement(advert);
        return comment;
    }

    @DeleteMapping("/{id}/comments/{idComment}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id, @PathVariable long idComment){
        Comment comment = commentRepository.findById(idComment).get();
        if(comment != null){
            if(comment.getAuthor().equals(userComponent.getLoggedUser().getName())){
                Advertisement advert = advertisementService.findById(id);
                advert.deleteComment(idComment);
                advertisementService.addAdvertisement(advert);
                commentRepository.deleteById(idComment);
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}