package com.daw.webapp12.repository;

import java.util.List;

import com.daw.webapp12.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByLocation(String string);
}
