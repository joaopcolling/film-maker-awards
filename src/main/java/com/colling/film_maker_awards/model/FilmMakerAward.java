package com.colling.film_maker_awards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmMakerAward {
    private String producerName;
    private Integer previousWin;
    private Integer followingWin;
    private Integer intervalWinner;
    private Boolean min;
}
