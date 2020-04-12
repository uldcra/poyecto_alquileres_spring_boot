package com.daw.webapp12.service;

import com.daw.webapp12.entity.Advertisement;

import java.util.List;

public interface AdvertisementInterface {

    public List<Advertisement> findAll();

    public Advertisement addAdvertisement (Advertisement advertisement);

    public void deleteAdvertisement (Long id);

    public Advertisement findById(Long id);

    public List<Advertisement> findByLocation(String string);


}