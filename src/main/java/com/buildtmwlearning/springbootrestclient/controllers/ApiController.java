package com.buildtmwlearning.springbootrestclient.controllers;

import com.buildtmwlearning.springbootrestclient.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@RestController
public class ApiController {
private static String REST_SERVICE_URL ="http://localhost:8000/restservice/";
private RestTemplate restTemplate;

@Autowired
public ApiController(RestTemplate restTemplate){
    this.restTemplate=restTemplate;
}

    @GetMapping("/all")
    public User getUserData(){
        String url=REST_SERVICE_URL + "retrieve";
        //todo move this to properties file
        try{
            ResponseEntity resp=restTemplate.getForEntity(url, User.class);
            return resp.getStatusCode() == HttpStatus.OK ? (User) resp.getBody() : null;
        }catch(Exception ex){
            return null ;
        }
    }


    @GetMapping("/firstName")
    public String displayFirstName(){
        String url=REST_SERVICE_URL + "firstName";
        try{
            String name = restTemplate.getForObject(url, String.class);
            return name;
        }catch(Exception ex){
            return "Service Down"+ ex ;
        }
    }


    @GetMapping("/lastName")
    public String displayLastName(){
        String url=REST_SERVICE_URL + "lastName";
        try{
            String lastname = restTemplate.getForObject(url, String.class);
            return lastname;
        }catch(Exception ex){
            return "Service Down "+ex ;
        }
    }


    @PostMapping(value = "/post",consumes = {"application/json"})
    public String postData(@RequestBody User user,@RequestHeader("Content-type") String contentType) throws IOException, InterruptedException {
        
        ObjectMapper objectMapper= new ObjectMapper();
        String requestBody=objectMapper.writeValueAsString(user);
        HttpClient client= HttpClient.newBuilder().build();
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(REST_SERVICE_URL+"inputdata"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200 ){
          throw new ResponseStatusException( HttpStatus.valueOf( response.statusCode()));
        }
        return response.body();
    }


    @PostMapping(value = "/restassured",consumes = {"application/json"})
    public String postData2(@RequestBody User user,@RequestHeader("Content-type") String contentType) throws IOException, InterruptedException {
        RestAssured.baseURI=REST_SERVICE_URL;
        String msg="Success";

        try {

            ObjectMapper objectMapper= new ObjectMapper();
            String requestBody=objectMapper.writeValueAsString(user);

            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.header("Content-Type","application/json");
            Response response = httpRequest.body(requestBody).post("/inputdata");
            int statusCode = response.getStatusCode();
            System.out.println("Response status code is "+statusCode);
            System.out.println(requestBody);

        }catch (ResponseStatusException exception){
            System.out.println(" Error " + exception);
            msg="Error "+exception;
        }

        return msg;
    }




}
