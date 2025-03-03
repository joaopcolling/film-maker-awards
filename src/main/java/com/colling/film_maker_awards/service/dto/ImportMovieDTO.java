package com.colling.film_maker_awards.service.dto;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ImportMovieDTO {    @CsvBindByPosition(position = 0)
    @CsvBindByPosition(position = 0)
    private Integer year;

    @CsvBindByPosition(position = 1)
    private String title;
    
    @CsvBindByPosition(position = 2)
    private String studios;
    
    @CsvBindByPosition(position = 3)
    private String producers;
    
    @CsvBindByPosition(position = 4)
    private String winner;
}
