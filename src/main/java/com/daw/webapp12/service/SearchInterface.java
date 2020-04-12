package com.daw.webapp12.service;

import com.daw.webapp12.entity.Search;

import java.util.List;

public interface SearchInterface {

    public List<Search> findAll();

    public Search addSearch(Search search);


}