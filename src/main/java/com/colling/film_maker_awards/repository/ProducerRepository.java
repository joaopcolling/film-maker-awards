package com.colling.film_maker_awards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colling.film_maker_awards.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Optional<Producer> findByName(String name);
}