package com.colling.film_maker_awards.service.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupFilmMakerArwardsDTO {
    private Set<FilmMakerAwardDTO> min;
    private Set<FilmMakerAwardDTO> max;
}
