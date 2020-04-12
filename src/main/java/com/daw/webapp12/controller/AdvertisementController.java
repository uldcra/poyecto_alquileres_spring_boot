package com.daw.webapp12.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.entity.Search;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.AdvertisementService;
import com.daw.webapp12.service.SearchService;
import com.daw.webapp12.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdvertisementController{

	@Autowired
	AdvertisementService advertisementService;
	@Autowired
    AdvertisementRepository  advertisementRepository;
	@Autowired
	UserService userService;
	@Autowired
	SearchService searchService;
	@Autowired
	UserComponent userComponent;

	@RequestMapping(value = {"/MainPage", ""})
    public String recommendeds(Model model) {
		List<Advertisement> ads = advertisementService.findAll();
		List<Advertisement> auxAdvertisements = new ArrayList<Advertisement>();
		for(int i = 0;i<ads.size();i++){
			auxAdvertisements.add(ads.get(i));
		}
		List<Search> searches = userService.findByName("Angel").get().getMySearches();
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
	   	model.addAttribute("graphValues", list);
		model.addAttribute("recommendedAds", recommendeds);
		//  if(userComponent.isLoggedUser()){
		// 	model.addAttribute("role", userComponent.getLoggedUser().getRoles().get(0));
		//  }
		 
        return "index";
	}


	@RequestMapping(value = "/properties/{id}")
    public String favAdvertisements(Model model, @PathVariable  long id) {
	 	model.addAttribute("Property", advertisementService.findById(id));
        return "properties-single";
	}


	private List<Advertisement> filters(List<Advertisement> result, int price, int squareMeters, int rooms,
										int bathrooms, String searchType, String propertyType) {
		List <Advertisement> search = new ArrayList<>();
		for (Advertisement advertisement : result) {
			if(advertisement.getbathrooms()>= bathrooms &&advertisement.getrooms()>=rooms
				&&advertisement.gettype().equals(searchType)&&advertisement.getproperty().equals(propertyType)
				&&advertisement.getsquareMeters() >= squareMeters&&advertisement.getprice() <= price)
				search.add(advertisement);	
		}
		return search;	
	
	}


	@RequestMapping(value = "/search")
	public String searchAdvertisement(Model model , @RequestParam  String location , @RequestParam int price, @RequestParam(value="searchType")  String searchType,@RequestParam(value="propertyType") String propertyType,
													@RequestParam  int squareMeters, @RequestParam(value="rooms")  int rooms, @RequestParam(value="bathrooms")  int bathrooms) {
		List<Advertisement> result= advertisementService.findByLocation(location);
		List<Advertisement> aux= result;
		aux= filters(aux, price,squareMeters,rooms,bathrooms,searchType,propertyType);
		if(aux.size()!=0){
			model.addAttribute("Property",aux);
		}else{
			model.addAttribute("Error", "No hay resultados.");
		}

		if(userComponent.getLoggedUser()!=null){
			Search userSearch = new Search(searchType,rooms, bathrooms, squareMeters, location, price);
			searchService.addSearch(userSearch);
			String name = userComponent.getLoggedUser().getName();
			Optional<Users> user = userService.findByName(name);
			if (user.get().getMySearches().size()!= 0){
				user.get().getMySearches().remove(0);
			}
			user.get().getMySearches().add(userSearch);
			userService.addUser(user.get());
		}
        return "properties-search";
	}
	
	@RequestMapping("/editProperties/{id}")
    public String editProperties(Model model, @PathVariable  long id) {
		model.addAttribute("advertisement", advertisementService.findById(id));
		//advertisementRepository.save(advertisement);
        return "properties-edit";
	}
	@PostMapping("/editProperties/{id}")
    public String editProperties(Model model, Advertisement advertisement,@PathVariable  long id,@RequestParam("file") MultipartFile[] multipartFile, @RequestParam String location) {
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
        advertisement.setImages(files);
		advertisementRepository.save(advertisement);
        return "redirect:/properties-modificar";
	}

	@RequestMapping("/deleteAdvertisement/{id}")
    public String deleteAdvertisement(Model model, @PathVariable  long id){
		List<Users> users = userService.findAll();
		for(Users user : users){
			if(user.getMyFavourites() != null){
				List<Advertisement> adverts = user.getMyFavourites();
				if(adverts.size()!=0){
					for(Advertisement ad : adverts){
						if(ad.getId()==id){
							user.deleteFavourite(id);
							break;				
						}
					}
				}	
			}
			if(user.getMyAdvertisements() != null){
				List<Advertisement> adverts = user.getMyAdvertisements();
				if(adverts.size()!=0){
					for(Advertisement ad : adverts){
						if(ad.getId()==id){
							user.deleteOneAdvertisement(id);
							break;
						}
					}
				}	
			}	
		}
        advertisementService.deleteAdvertisement(id);
        model.addAttribute("allProperties",advertisementService.findAll());
        return "redirect:/AllProperties";
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
	
	@RequestMapping(value = "/AllProperties")
    public String allProperties(Model model) {
		if(userComponent.isLoggedUser() && userComponent.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
			if(advertisementService.findAll().size()>0){
				model.addAttribute("allProperties", advertisementService.findAll());
			}else{
				model.addAttribute("Error", "No hay anuncios .");
			}
			return "properties-all";
		}else{
			return "login";
		}	
    }
	
}