package com.colling.film_maker_awards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colling.film_maker_awards.model.NominatedMovie;

@Repository
public interface NominatedMovieRepository extends JpaRepository<NominatedMovie, Integer> {

    Optional<NominatedMovie> findByTitle(String title);

}