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
public class AwardsService {

    @Autowired
    AwardsRepository awardsRepository;

    public GroupFilmMakerArwardsDTO getAwards() {
        Set<FilmMakerAward> awards = awardsRepository.getAwards();

        GroupFilmMakerArwardsDTO groupedAwards = new GroupFilmMakerArwardsDTO();
        groupedAwards.setMin(mapAwards(awards, true));
        groupedAwards.setMax(mapAwards(awards, false));

        return groupedAwards;
    }

    private Set<FilmMakerAwardDTO> mapAwards(Set<FilmMakerAward> awards, boolean isMin) {
        return awards.stream()
            .filter(award -> Boolean.valueOf(award.getMin()).equals(isMin))
            .map(this::convertToDTO)
            .collect(Collectors.toSet());
    }

    private FilmMakerAwardDTO convertToDTO(FilmMakerAward award) {
        return FilmMakerAwardDTO.builder()
            .producer(award.getProducerName())
            .interval(award.getIntervalWinner())
            .previousWin(award.getPreviousWin())
            .followingWin(award.getFollowingWin())
            .build();
    }
}
