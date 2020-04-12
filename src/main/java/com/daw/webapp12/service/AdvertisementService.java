package com.daw.webapp12.service;

import java.util.List;

import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.repository.AdvertisementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementService implements AdvertisementInterface{

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<Advertisement> findAll(){
        return advertisementRepository.findAll();
    }

    @Override
    public Advertisement addAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }
    @Override
    public List<Advertisement> findByLocation(String location) {
        return advertisementRepository.findByLocation(location);
    }


}
