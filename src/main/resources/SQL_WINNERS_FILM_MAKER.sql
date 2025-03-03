WITH PRODUCERS_WINNING_YEARS AS (
  SELECT
    P.ID AS PRODUCER_ID,
    P.NAME AS PRODUCER_NAME,
    NM.MOVIE_YEAR AS WINNING_YEAR
  FROM
    NOMINATED_MOVIE NM
    INNER JOIN NOMINATED_MOVIE_PRODUCER NMP ON NM.ID = NMP.MOVIE_ID
    INNER JOIN PRODUCER P ON NMP.PRODUCER_ID = P.ID
  WHERE
    WINNER = TRUE
),
YEAR_DIFFERENCES AS (
  SELECT
    P1.PRODUCER_NAME,
    P1.WINNING_YEAR AS PREVIOUS_WIN,
    P2.WINNING_YEAR AS FOLLOWING_WIN,
    (P2.WINNING_YEAR - P1.WINNING_YEAR) WINNING_INTERVAL
  FROM
    PRODUCERS_WINNING_YEARS P1
    INNER JOIN PRODUCERS_WINNING_YEARS P2 ON P1.PRODUCER_ID = P2.PRODUCER_ID
    AND P1.WINNING_YEAR < P2.WINNING_YEAR
),
MIN_MAX_INTERVAL AS (
  SELECT
    MIN(WINNING_INTERVAL) AS MIN_INTERVAL,
    MAX(WINNING_INTERVAL) AS MAX_INTERVAL
  FROM
    YEAR_DIFFERENCES
)
SELECT
  YD.PRODUCER_NAME,
  YD.PREVIOUS_WIN,
  YD.FOLLOWING_WIN,
  YD.WINNING_INTERVAL,
  TRUE AS MIN
FROM
  YEAR_DIFFERENCES YD
INNER JOIN MIN_MAX_INTERVAL MI ON YD.WINNING_INTERVAL = MI.MIN_INTERVAL
  UNION
SELECT
  YD.PRODUCER_NAME,
  YD.PREVIOUS_WIN,
  YD.FOLLOWING_WIN,
  YD.WINNING_INTERVAL,
  FALSE AS MIN
FROM
  YEAR_DIFFERENCES YD
INNER JOIN MIN_MAX_INTERVAL MI ON YD.WINNING_INTERVAL = MI.MAX_INTERVAL