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
@RequestMapping("/example/employee")
public class MainController {
    @Autowired
    CRUDEmployeeService crudEmployeeService;

    @Autowired
    RestCallApiSample restCallApiSampleService;

    @PostMapping("/create")
    public GlobalResponse tambahPegawai(@RequestBody EmployeeDTO body) {
        GlobalResponse response = new GlobalResponse();
        try {
            response = crudEmployeeService.createEmployee(body);
        } catch (Exception e) {
            response.setStatus("malfunction");
            response.setDescription("something went wrong");
            response.setDetails(e.getMessage());
        }
        
        return response;
    }

    @PostMapping("/view")
    public GlobalResponse lihatPegawai() {
        GlobalResponse response = new GlobalResponse();
        try {
            response = crudEmployeeService.viewEmployee();
        } catch (Exception e) {
            response.setStatus("malfunction");
            response.setDescription("something went wrong");
            response.setDetails(e.getMessage());

        }
        
        return response;
    }
    
  
}
