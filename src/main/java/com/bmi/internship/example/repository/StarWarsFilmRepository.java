package com.bmi.internship.example.repository;

import com.bmi.internship.example.entity.StarWarsFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarWarsFilmRepository extends JpaRepository<StarWarsFilm, Long> {
    Optional<StarWarsFilm> findByTitleAndEpisodeId(String title, int episodeId);
}
