package com.daw.webapp12.repository;

import com.daw.webapp12.entity.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    
}
