package com.colling.film_maker_awards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colling.film_maker_awards.service.AwardsService;
import com.colling.film_maker_awards.service.dto.GroupFilmMakerArwardsDTO;


@RestController
@RequestMapping("awards")
public class FilmMakerAwardsController {
    
    @Autowired
    private AwardsService awardsService;

    public FilmMakerAwardsController(AwardsService awardsService) {
        this.awardsService = awardsService;
    }

    @GetMapping()
    public ResponseEntity<GroupFilmMakerArwardsDTO> getAwards() {
        return ResponseEntity.ok().body(awardsService.getAwards());
    }
    
}
