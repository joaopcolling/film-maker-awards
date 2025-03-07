package com.colling.film_maker_awards.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.colling.film_maker_awards.service.AwardsService;
import com.colling.film_maker_awards.service.dto.FilmMakerAwardDTO;
import com.colling.film_maker_awards.service.dto.GroupFilmMakerArwardsDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AwardsServiceTest {

    @Autowired
    private AwardsService awardsService;

    @Test
    public void testDefaultCsv(){
        GroupFilmMakerArwardsDTO awards = awardsService.getAwards();

        assertNotNull(awards);
        assertNotNull(awards.getMin());
        assertEquals(awards.getMin().size(), 1);
        
        FilmMakerAwardDTO min = awards.getMin().stream().findFirst().get();

        assertNotNull(min);
        assertEquals(min.getProducer(), "Joel Silver");
        assertEquals(min.getPreviousWin(), 1990);
        assertEquals(min.getFollowingWin(), 1991);
        assertEquals(min.getInterval(), 1);


        assertNotNull(awards.getMax());
        assertEquals(awards.getMax().size(),1);

        FilmMakerAwardDTO max = awards.getMax().stream().findFirst().get();
      
        assertEquals(max.getProducer(), "Matthew Vaughn");
        assertEquals(max.getPreviousWin(), 2002);
        assertEquals(max.getFollowingWin(), 2015);
        assertEquals(max.getInterval(), 13);

    }
}
