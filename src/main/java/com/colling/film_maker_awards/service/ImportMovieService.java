package com.colling.film_maker_awards.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import com.colling.film_maker_awards.model.NominatedMovie;
import com.colling.film_maker_awards.model.Producer;
import com.colling.film_maker_awards.model.Studio;
import com.colling.film_maker_awards.repository.NominatedMovieRepository;
import com.colling.film_maker_awards.repository.ProducerRepository;
import com.colling.film_maker_awards.repository.StudioRepository;
import com.colling.film_maker_awards.service.dto.ImportMovieDTO;


@Service
public class ImportMovieService  {

    @Autowired
    private NominatedMovieRepository nominatedMovieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;


    public void importMovieRegisters(List<ImportMovieDTO> registers) {
        Set<NominatedMovie> entities = convertToEntity(registers);
        saveAll(entities);
    }


    private Set<NominatedMovie> convertToEntity(List<ImportMovieDTO> registers) {
        return registers.stream()
            .map(register -> NominatedMovie.builder()
                .movieYear(register.getYear())
                .title(register.getTitle())
                .studios(extractStudios(register.getStudios()))
                .producers(extractProducers(register.getProducers()))
                .winner("yes".equalsIgnoreCase(register.getWinner()))
                .build())
            .collect(Collectors.toSet());
    }

    private Set<Studio> extractStudios(String studiosStr){
        return parseCommaSeparated(studiosStr)
            .stream()
            .map(this::saveOrUpdateStudio)
            .collect(Collectors.toSet());
    }

    private Set<Producer> extractProducers(String producersStr){
        return parseCommaAndAndSeparated(producersStr)
            .stream()
            .map(this::saveOrUpdateProducer)
            .collect(Collectors.toSet());
    }

    // Método para separar por ","
    public static Set<String> parseCommaSeparated(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    // Método para separar por "," e "and"
    public static Set<String> parseCommaAndAndSeparated(String input) {
        return Arrays.stream(input.split("\\s*,\\s*|\\s+and\\s+"))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    public void saveAll(Set<NominatedMovie> entities) {
        nominatedMovieRepository.saveAll(entities);
    }

    private Producer saveOrUpdateProducer(String name) {
        return producerRepository.findByName(name)
                .orElseGet(() -> producerRepository.save(new Producer(name)));
    }

    private Studio saveOrUpdateStudio(String name) {
        return studioRepository.findByName(name)
                .orElseGet(() -> studioRepository.save(new Studio(name)));
    }

}
