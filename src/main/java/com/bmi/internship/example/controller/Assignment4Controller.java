package com.bmi.internship.example.controller;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;
// import org.hibernate.mapping.List;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmi.internship.example.Utils.JsonUtil;
import com.bmi.internship.example.model.EmployeeDTO;
import com.bmi.internship.example.model.GlobalResponse;
import com.bmi.internship.example.model.samplePost.SampleRequestAPIPost;
import com.bmi.internship.example.service.CRUDEmployeeService;
import com.bmi.internship.example.service.RestCallApiSample;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/example/callApi")
public class Assignment4Controller {

    @Autowired
    RestCallApiSample restCallApiSampleService;
  
    @PostMapping("/samplePost")
    public GlobalResponse postMethodName(@RequestBody SampleRequestAPIPost entity) {
        GlobalResponse response = new GlobalResponse();
        System.out.println("API samplePost Hitted : "+ JsonUtil.convertObjectToString(entity));
        try {
        System.out.println("Prepare to execute restCallApiSampleService");
            response = restCallApiSampleService.samplePost(entity);
        } catch (Exception e) {
            response.setStatus("malfunction");
            response.setDescription("something went wrong when call sample service");
            response.setDetails(e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/sampleGet")
    public GlobalResponse getMethodName() {
        GlobalResponse response = new GlobalResponse();
        try {
            response = restCallApiSampleService.sampleGet();
        } catch (Exception e) {
            response.setStatus("malfunction");
            response.setDescription("something went wrong when call sample service");
            response.setDetails(e.getMessage());
        }
        
        return response;
    }

     @GetMapping("/download-csv")
    public Object downloadCsv() throws StreamWriteException, DatabindException, IOException {
        try {
            ResponseEntity<byte[]> response = restCallApiSampleService.downloadCsv();
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest();
            // TODO: handle exception
        }
    }
    
}
