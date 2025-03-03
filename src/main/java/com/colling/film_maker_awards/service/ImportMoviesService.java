package com.colling.film_maker_awards.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.Reader;
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

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class ImportMoviesService {

    @Autowired
    private NominatedMovieRepository nominatedMovieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;


    public void importMovieRegisters(Reader reader) throws Exception {
        List<ImportMovieDTO> csvRegisters = this.readCsvRegisters(reader);
        Set<NominatedMovie> entities = convertToEntity(csvRegisters);
        saveAll(entities);
    }

    public List<ImportMovieDTO> readCsvRegisters(Reader reader) throws Exception { 
        CsvToBean<ImportMovieDTO> cb = new CsvToBeanBuilder<ImportMovieDTO>(reader)
            .withType(ImportMovieDTO.class)
            .withSeparator(';')
            .withSkipLines(1)
            .build();
        return cb.parse();  
    }

    private Set<NominatedMovie> convertToEntity(List<ImportMovieDTO> csvRegisters) {
        return csvRegisters.stream()
            .map(csvRegister -> NominatedMovie.builder()
                .movieYear(csvRegister.getYear())
                .title(csvRegister.getTitle())
                .studios(extractStudios(csvRegister.getStudios()))
                .producers(extractProducers(csvRegister.getProducers()))
                .winner("yes".equalsIgnoreCase(csvRegister.getWinner()))
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
