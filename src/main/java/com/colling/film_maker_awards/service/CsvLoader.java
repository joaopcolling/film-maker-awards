package com.colling.film_maker_awards.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;



@Component
public class CsvLoader {
    @Value("${csv.path:classpath:static/movielist.csv}")
    private String csvPath;

    @Autowired
    private ImportMoviesService importMoviesService;

    Logger logger = LoggerFactory.getLogger(CsvLoader.class);


    @PostConstruct
    public void init() {
        logger.info("Caminho do CSV recebido: " + csvPath);

        if (csvPath == null || csvPath.isBlank()) {
            logger.error("Caminho do CSV não informado! Use --csv.path=<absolute_path> ou classpath:<file>");
            return;
        }

        try {
            Reader reader = getCsvReader(csvPath);
            logger.info("Arquivo carregado com sucesso: " + csvPath);
            importMoviesService.importMovieRegisters(reader);
        } catch (Exception e) {
            logger.error("Erro ao carregar arquivo CSV: " + e.getMessage());
        }
    }

    public Reader getCsvReader(String filePath) throws Exception {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("O caminho do CSV não foi informado! Use --csv.path=<absolute_path> ou classpath:<file>");
        }

        // Check if the path exists as a file (for volume mounting)
        Path path = Paths.get(filePath.trim());
        if (Files.exists(path) && Files.isRegularFile(path)) {
            logger.info("Carregando arquivo informado: " + filePath);
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        }

        if (filePath.startsWith("classpath:")) {
            // Lendo do Classpath
            String classpathLocation = filePath.replace("classpath:", "").trim();
            logger.info("Carregando arquivo default: " + classpathLocation);
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(classpathLocation);
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo não encontrado no classpath: " + classpathLocation);
            }
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } else {
             throw new FileNotFoundException("Arquivo não encontrado: " + filePath);
        }
    }
}
