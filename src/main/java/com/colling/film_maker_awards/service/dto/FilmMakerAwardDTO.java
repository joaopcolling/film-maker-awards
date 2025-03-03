package com.colling.film_maker_awards.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmMakerAwardDTO {

    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;
}
