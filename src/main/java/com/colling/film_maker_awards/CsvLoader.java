package com.colling.film_maker_awards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.colling.film_maker_awards.service.ImportMoviesService;

import jakarta.annotation.PostConstruct;

@Component
public class CsvLoader {
    @Value("${csv.path:classpath:static/movielist.csv}")
    private String csvPath;

    @Autowired
    private ImportMoviesService importMoviesService;

    @PostConstruct
    public void init() {
        System.out.println("🔍 Caminho do CSV recebido: " + csvPath);

        if (csvPath == null || csvPath.isBlank()) {
            System.err.println("❌ Caminho do CSV não informado! Use --csv.path=<absolute_path> ou classpath:<file>");
            return;
        }

        try {
            Reader reader = getCsvReader(csvPath);
            System.out.println("📂 Arquivo carregado com sucesso: " + csvPath);
            importMoviesService.importMovieRegisters(reader);
        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Reader getCsvReader(String filePath) throws Exception {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("O caminho do CSV não foi informado! Use --csv.path=<absolute_path> ou classpath:<file>");
        }

        // Check if the path exists as a file (for volume mounting)
        Path path = Paths.get(filePath.trim());
        if (Files.exists(path) && Files.isRegularFile(path)) {
            System.out.println("⏳ Carregando arquivo informado: " + filePath);
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        }

        if (filePath.startsWith("classpath:")) {
            // Lendo do Classpath
            String classpathLocation = filePath.replace("classpath:", "").trim();
            System.out.println("⏳ Carregando arquivo default: " + classpathLocation);
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
