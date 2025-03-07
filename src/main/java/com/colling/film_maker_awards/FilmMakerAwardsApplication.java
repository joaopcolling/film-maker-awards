package com.colling.film_maker_awards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.colling.film_maker_awards.service.CsvLoaderService;
import com.colling.film_maker_awards.service.ImportMovieService;
import com.colling.film_maker_awards.service.dto.ImportMovieDTO;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class FilmMakerAwardsApplication {

	@Autowired
	private CsvLoaderService csvLoaderService;
	
	@Autowired
	private ImportMovieService importMovieService;


	public static void main(String[] args) {
		SpringApplication.run(FilmMakerAwardsApplication.class, args);
	}
	
	@PostConstruct
    public void init() {
		List<ImportMovieDTO> reader = csvLoaderService.getMoviesFromFile("classpath:static/movielist.csv");
		importMovieService.importMovieRegisters(reader);
	}

}
