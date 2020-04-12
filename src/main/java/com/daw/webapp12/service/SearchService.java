package com.daw.webapp12.service;

import java.util.List;

import com.daw.webapp12.entity.Search;
import com.daw.webapp12.repository.SearchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchService implements SearchInterface {

    @Autowired
    private SearchRepository searchRepository;

    public List<Search> findAll() {
        return searchRepository.findAll();
    }

    @Override
    public Search addSearch(Search search) {
        return searchRepository.save(search);
    }

}