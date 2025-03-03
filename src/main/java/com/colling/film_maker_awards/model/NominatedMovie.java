package com.colling.film_maker_awards.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "nominated_movie", indexes = @Index(name="INDEX_MOVIE_YEAR", columnList = "MOVIE_YEAR"))
public class NominatedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer movieYear;

    private String title;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="nominated_movie_studio",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id"))
    private Set<Studio> studios = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="nominated_movie_producer",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "producer_id"))
    private Set<Producer> producers = new HashSet<>();

    private Boolean winner;
}
