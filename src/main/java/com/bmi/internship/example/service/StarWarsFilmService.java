package com.bmi.internship.example.service;

import com.bmi.internship.example.entity.StarWarsFilm;
import com.bmi.internship.example.model.GlobalResponse;
import com.bmi.internship.example.repository.StarWarsFilmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class StarWarsFilmService {

    @Autowired
    private StarWarsFilmRepository filmRepository;

    // public GlobalResponse fetchAndInsertFilms() throws JsonProcessingException {
    //     List<StarWarsFilm> films = fetchFilmsFromAPI();
    //     return saveFilms(films);
    // }

    // public List<StarWarsFilm> fetchFilmsFromDB() {
    //     return filmRepository.findAll();
    // }

    public List<StarWarsFilm> getAllFilms() {
        return filmRepository.findAll();
    }



    
    public List<StarWarsFilm> fetchFilmsFromAPI() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(); // Membuat request HTTP ke API eksternal
        String url = "https://swapi.dev/api/films/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // Mengambil data dari API dalam bentuk String

        ObjectMapper mapper = new ObjectMapper(); // Mengonversi data String menjadi JSON/memparsing JSON response dari API ke dalam objek JsonNode
        JsonNode root = mapper.readTree(response.getBody()); // Mengambil data JSON
        JsonNode results = root.path("results"); // Mengambil data films

        // Mengonversi data JSON ke dalam objek StarWarsFilm
        List<StarWarsFilm> films = new ArrayList<>();

        for (JsonNode node : results) {
            StarWarsFilm film = new StarWarsFilm();
            film.setTitle(node.path("title").asText());
            film.setEpisodeId(node.path("episode_id").asInt());
            film.setDirector(node.path("director").asText());
            film.setProducer(node.path("producer").asText());
            film.setReleaseDate(node.path("release_date").asText());
            films.add(film);
        }
        return films;
    }

    public GlobalResponse saveFilms(List<StarWarsFilm> films) {
        GlobalResponse response = new GlobalResponse();
        List<StarWarsFilm> failedToInsert = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        for (StarWarsFilm film : films) {
            film.setCreated(timestamp);
            if (filmRepository.findByTitleAndEpisodeId(film.getTitle(), film.getEpisodeId()).isPresent()) {
                failedToInsert.add(film);
            } else {
                filmRepository.save(film);
            }
        }

        for (StarWarsFilm film : failedToInsert) {
            response.setDetails(film);
        }

        if(!failedToInsert.isEmpty()){
            response.setStatus("success with some condition");
            response.setDescription("One or More Data Alread Exist on the Table");
            response.setDetails(failedToInsert);
        }else{
            response.setStatus("success");
            response.setDescription("All data inserted successfully");
        }
        return response;
    }
}
