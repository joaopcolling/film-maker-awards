package com.colling.film_maker_awards.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colling.film_maker_awards.model.FilmMakerAward;
import com.colling.film_maker_awards.repository.AwardsRepository;
import com.colling.film_maker_awards.service.dto.FilmMakerAwardDTO;
import com.colling.film_maker_awards.service.dto.GroupFilmMakerArwardsDTO;

@Service
public class FilmMakerAwardsService {

    @Autowired
    AwardsRepository awardsRepository;


    public GroupFilmMakerArwardsDTO getAwards(){
        Set<FilmMakerAward> awards = awardsRepository.getAwards();

        GroupFilmMakerArwardsDTO groupedAwards = new GroupFilmMakerArwardsDTO();
        Set<FilmMakerAwardDTO> min = awards
            .stream()
            .filter(award -> Boolean.TRUE.equals(award.getMin()))
            .map(award -> 
                FilmMakerAwardDTO
                    .builder()
                    .producer(award.getProducerName())
                    .interval(award.getIntervalWinner())
                    .previousWin(award.getPreviousWin())
                    .followingWin(award.getFollowingWin())
                    .build()
            )
            .collect(Collectors.toSet());

        Set<FilmMakerAwardDTO> max = awards
        .stream()
        .filter(award -> Boolean.FALSE.equals(award.getMin()))
        .map(award -> 
            FilmMakerAwardDTO
                .builder()
                .producer(award.getProducerName())
                .interval(award.getIntervalWinner())
                .previousWin(award.getPreviousWin())
                .followingWin(award.getFollowingWin())
                .build()
        )
        .collect(Collectors.toSet());

        groupedAwards.setMin(min);
        groupedAwards.setMax(max);

        return groupedAwards;
    }

}
