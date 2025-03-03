package com.colling.film_maker_awards.repository;

import java.util.Set;

import com.colling.film_maker_awards.model.FilmMakerAward;

public interface AwardsRepository {
    Set<FilmMakerAward> getAwards();
}
