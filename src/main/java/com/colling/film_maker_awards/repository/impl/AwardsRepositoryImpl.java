package com.colling.film_maker_awards.repository.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.colling.film_maker_awards.model.FilmMakerAward;
import com.colling.film_maker_awards.repository.AwardsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AwardsRepositoryImpl implements AwardsRepository {


    @PersistenceContext
    private EntityManager entityManager;

    String QUERY = ""
    + "WITH INTERVALS AS ( "
    + "  SELECT "
    + "    PRODUCER_ID, "
    + "    PRODUCER_NAME, "
    + "    WINNING_YEAR AS PREVIOUS_WIN, "
    + "    NEXT_WINNING_YEAR AS FOLLOWING_WIN, "
    + "    (NEXT_WINNING_YEAR - WINNING_YEAR) AS WINNING_INTERVAL "
    + "  FROM ( "
    + "    SELECT "
    + "      P.ID AS PRODUCER_ID, "
    + "      P.NAME AS PRODUCER_NAME, "
    + "      NM.MOVIE_YEAR AS WINNING_YEAR, "
    + "      LEAD(NM.MOVIE_YEAR) OVER (PARTITION BY P.ID ORDER BY NM.MOVIE_YEAR) AS NEXT_WINNING_YEAR "
    + "    FROM "
    + "      NOMINATED_MOVIE NM "
    + "      INNER JOIN NOMINATED_MOVIE_PRODUCER NMP ON NM.ID = NMP.MOVIE_ID "
    + "      INNER JOIN PRODUCER P ON NMP.PRODUCER_ID = P.ID "
    + "    WHERE WINNER = TRUE "
    + "  ) SUB "
    + "  WHERE NEXT_WINNING_YEAR IS NOT NULL "
    + "), "
    + "MIN_MAX_INTERVALS AS ( "
    + "  SELECT "
    + "    MIN(WINNING_INTERVAL) AS MIN_INTERVAL, "
    + "    MAX(WINNING_INTERVAL) AS MAX_INTERVAL "
    + "  FROM INTERVALS "
    + ") "
    + "SELECT "
    + "  I.PRODUCER_NAME, "
    + "  I.PREVIOUS_WIN, "
    + "  I.FOLLOWING_WIN, "
    + "  I.WINNING_INTERVAL, "
    + "  (I.WINNING_INTERVAL = M.MIN_INTERVAL) AS MIN "
    + "FROM INTERVALS I "
    + "JOIN MIN_MAX_INTERVALS M "
    + "  ON I.WINNING_INTERVAL = M.MIN_INTERVAL OR I.WINNING_INTERVAL = M.MAX_INTERVAL;";
    @Override
    public Set<FilmMakerAward> getAwards() {
    
        List<Object[]> results = entityManager.createNativeQuery(QUERY).getResultList();

        return results.stream()
        .map(row -> new FilmMakerAward(
                (String) row[0],       // producerName
                (Integer) row[1],      // previousWin
                (Integer) row[2],      // followingWin
                (Integer) row[3],      // intervalWinner
                (Boolean) row[4]       // min
        ))
        .collect(Collectors.toSet());
    }
} 