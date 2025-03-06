package com.colling.film_maker_awards.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.colling.film_maker_awards.model.NominatedMovie;
import com.colling.film_maker_awards.repository.NominatedMovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvLoaderTest {

    @Autowired
    private NominatedMovieRepository nominatedMovieRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    void testInitializationImportCsv() throws Exception {

        assertEquals(206, nominatedMovieRepository.count());

        Optional<NominatedMovie> movieByTitle = nominatedMovieRepository.findByTitle("Can't Stop the Music");
        assertTrue(movieByTitle.isPresent());
        assertEquals(movieByTitle.get().getMovieYear(), 1980);
        assertEquals(movieByTitle.get().getTitle(), "Can't Stop the Music");
        assertEquals(movieByTitle.get().getStudios().size(), 1);
        assertEquals(movieByTitle.get().getProducers().size(), 1);
        assertEquals(movieByTitle.get().getWinner(), Boolean.TRUE);
        
        // Novos asserts baseados nas linhas do CSV
        Optional<NominatedMovie> movieByTitleCruising = nominatedMovieRepository.findByTitle("Cruising");
        assertTrue(movieByTitleCruising.isPresent());
        assertEquals(movieByTitleCruising.get().getMovieYear(), 1980);
        assertEquals(movieByTitleCruising.get().getTitle(), "Cruising");
        assertEquals(movieByTitleCruising.get().getStudios().size(), 2);
        assertEquals(movieByTitleCruising.get().getProducers().size(), 1);
        assertEquals(movieByTitleCruising.get().getWinner(), Boolean.FALSE);

        Optional<NominatedMovie> movieByTitleTheFormula = nominatedMovieRepository.findByTitle("The Formula");
        assertTrue(movieByTitleTheFormula.isPresent());
        assertEquals(movieByTitleTheFormula.get().getMovieYear(), 1980);
        assertEquals(movieByTitleTheFormula.get().getTitle(), "The Formula");
        assertEquals(movieByTitleTheFormula.get().getStudios().size(), 2);
        assertEquals(movieByTitleTheFormula.get().getProducers().size(), 1);
        assertEquals(movieByTitleTheFormula.get().getWinner(), Boolean.FALSE);

        Optional<NominatedMovie> movieByTitleFridayThe13th = nominatedMovieRepository.findByTitle("Friday the 13th");
        assertTrue(movieByTitleFridayThe13th.isPresent());
        assertEquals(movieByTitleFridayThe13th.get().getMovieYear(), 1980);
        assertEquals(movieByTitleFridayThe13th.get().getTitle(), "Friday the 13th");
        assertEquals(movieByTitleFridayThe13th.get().getStudios().size(), 1);
        assertEquals(movieByTitleFridayThe13th.get().getProducers().size(), 1);
        assertEquals(movieByTitleFridayThe13th.get().getWinner(), Boolean.FALSE);

        Optional<NominatedMovie> movieByTitleTheNudeBomb = nominatedMovieRepository.findByTitle("The Nude Bomb");
        assertTrue(movieByTitleTheNudeBomb.isPresent());
        assertEquals(movieByTitleTheNudeBomb.get().getMovieYear(), 1980);
        assertEquals(movieByTitleTheNudeBomb.get().getTitle(), "The Nude Bomb");
        assertEquals(movieByTitleTheNudeBomb.get().getStudios().size(), 1);
        assertEquals(movieByTitleTheNudeBomb.get().getProducers().size(), 1);
        assertEquals(movieByTitleTheNudeBomb.get().getWinner(), Boolean.FALSE);
    }
}
