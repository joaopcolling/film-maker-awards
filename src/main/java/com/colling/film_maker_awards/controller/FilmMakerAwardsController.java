package com.colling.film_maker_awards.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colling.film_maker_awards.service.FilmMakerAwardsService;
import com.colling.film_maker_awards.service.dto.GroupFilmMakerArwardsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("awards")
public class FilmMakerAwardsController {
    
    @Autowired
    FilmMakerAwardsService filmMakerAwardsService;

    @GetMapping()
    public ResponseEntity<GroupFilmMakerArwardsDTO> getAwards() {
        return ResponseEntity.ok().body(filmMakerAwardsService.getAwards());
    }
    
}
