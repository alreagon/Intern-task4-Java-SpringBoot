package com.bmi.internship.example.controller;

import com.bmi.internship.example.entity.StarWarsFilm;
import com.bmi.internship.example.model.GlobalResponse;
import com.bmi.internship.example.service.StarWarsFilmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/starwars")
public class StarWarsFilmController {

    @Autowired
    private StarWarsFilmService filmService;

    @GetMapping("/fetchAllfilms")
    public List<StarWarsFilm> fetchFilms() throws JsonProcessingException {
        return filmService.fetchFilmsFromAPI();
    }

    @GetMapping("/getAlldatabase")
    public List<StarWarsFilm> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/fetch-and-insert")
    public GlobalResponse fetchAndInsertFilms() {
        try {
            List<StarWarsFilm> films = filmService.fetchFilmsFromAPI();
            return filmService.saveFilms(films);
        } catch (JsonProcessingException e) {
            GlobalResponse response = new GlobalResponse();
            response.setStatus("error");
            response.setDescription("Failed to fetch data from API");
            response.setDetails(e.getMessage());
            return response;
        }
    }

    @GetMapping("/downloadascsv")
    public ResponseEntity<byte[]> downloadCsv() throws IOException {
        List<StarWarsFilm> films = filmService.fetchFilmsFromAPI();

        CsvMapper csvMapper = new CsvMapper(); // untuk mengonversi objek Java ke format CSV
        CsvSchema schema = csvMapper.schemaFor(StarWarsFilm.class).withHeader(); // Menentukan skema CSV yang akan digunakan, termasuk header kolom
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // Menyimpan data ke dalam byte array
        csvMapper.writer(schema).writeValue(outputStream, films); // Menyimpan data ke dalam file CSV

        // Buat ResponseEntity dengan file CSV
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=starwarsfilms.csv") 
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/saveascsv")
    public GlobalResponse saveCsv() {
        GlobalResponse response = new GlobalResponse();
        try {
            List<StarWarsFilm> films = filmService.fetchFilmsFromAPI();

            CsvMapper csvMapper = new CsvMapper(); // untuk mengonversi objek Java ke format CSV
            CsvSchema schema = csvMapper.schemaFor(StarWarsFilm.class).withHeader(); // Menentukan skema CSV yang akan digunakan, termasuk header kolom
            File file = new File("starwarsfilms.csv"); 
            csvMapper.writer(schema).writeValue(file, films); // Menyimpan data ke dalam file CSV

            response.setStatus("success");
            response.setDescription("CSV file saved successfully");
        } catch (IOException e) {
            response.setStatus("error");
            response.setDescription("Failed to save CSV file");
            response.setDetails(e.getMessage());
        }

        return response;
    }
}
