package com.bmi.internship.example.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;
// import org.hibernate.mapping.List;
import java.util.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.bmi.internship.example.Utils.JsonUtil;
import com.bmi.internship.example.model.GlobalResponse;
import com.bmi.internship.example.model.sampleGet.SampleResponseGet;
import com.bmi.internship.example.model.samplePost.SampleRequestAPIPost;
import com.bmi.internship.example.model.samplePost.SampleResponseAPIPost;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Service
public class RestCallApiSample {
    
        public GlobalResponse samplePost(SampleRequestAPIPost data) throws JsonProcessingException{
        System.out.println("Prepare to execute samplePost");

        GlobalResponse response = new GlobalResponse();
        HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<SampleRequestAPIPost> req = new HttpEntity<SampleRequestAPIPost>(data, headers);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Prepare to execute samplePost " + JsonUtil.convertObjectToString(req));
        try {
            ResponseEntity<SampleResponseAPIPost> responseEntity = restTemplate.postForEntity("https://reqres.in/api/users",req, SampleResponseAPIPost.class);
            SampleResponseAPIPost res = responseEntity.getBody();
            System.out.println("Raw Response samplePost " + JsonUtil.convertObjectToString(res));

            response.setStatus("success");
            response.setDescription("Impressive Guys");
            response.setDetails(res);
            System.out.println("Final Response samplePost " + JsonUtil.convertObjectToString(response));

        } catch (Exception e) {
            response.setStatus("failed");
            response.setDescription("something went wrong when call API Post");
            response.setDetails(e);
            System.out.println("Final Response samplePost " + JsonUtil.convertObjectToString(response));

            // TODO: handle exception
        }
        return response;
    }

    public GlobalResponse sampleGet() throws JsonProcessingException{
        
        GlobalResponse response = new GlobalResponse();
        RestTemplate restTemplate = new RestTemplate();
        // System.out.println("Prepare to execute samplePost");
        
		ResponseEntity<SampleResponseGet> responseEntity = restTemplate.getForEntity("https://reqres.in/api/users", SampleResponseGet.class);
        System.out.println("Prepare to execute sampleGet " + JsonUtil.convertObjectToString(responseEntity));
		Object res = responseEntity.getBody();
        System.out.println("Raw Response sampleGet " + JsonUtil.convertObjectToString(res));

        try {
            
            response.setStatus("success");
            response.setDescription("Impressive Guys");
            response.setDetails(res);
            System.out.println("Final Response sampleGet " + JsonUtil.convertObjectToString(response));


        } catch (Exception e) {
            response.setStatus("failed");
            response.setDescription("something went wrong when call API Get");
            response.setDetails(e);
            System.out.println("Final Response sampleGet " + JsonUtil.convertObjectToString(response));

            // TODO: handle exception
        }
        return response;
    }

        //  @GetMapping("/download-csv")
    public ResponseEntity<byte[]> downloadCsv() throws IOException {

        // Untuk kasus yang ada disoal, maka Object 'people' dapat diganti menjadi
        // List<SampleRequestAPIPost> people = startWarsService.findAll();
        // silakan disesuaikan sesuai kebutuhan
        List<SampleRequestAPIPost> people = Arrays.asList(
                new SampleRequestAPIPost("Diennul Chayra", "Learning Development"),
                new SampleRequestAPIPost("Anton Hendrianto", "Head of People Development Culture")
        );

        // Buat CsvMapper dan CsvSchema
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(SampleRequestAPIPost.class).withHeader();

        // Tulis data ke CSV
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        csvMapper.writer(csvSchema).writeValue(outputStream, people);

        // Buat ResponseEntity dengan file CSV
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=people.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }
  
}
