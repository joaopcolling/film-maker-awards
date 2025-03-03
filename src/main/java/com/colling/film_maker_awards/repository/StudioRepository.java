package com.colling.film_maker_awards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colling.film_maker_awards.model.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {
    
    Optional<Studio> findByName(String name);

}
