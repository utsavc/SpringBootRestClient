package com.buildtmwlearning.springbootrestclient.controllers;

import com.buildtmwlearning.springbootrestclient.entities.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @InjectMocks
    ApiController controller;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    private static String REST_SERVICE_URL ="http://localhost:8000/restservice/";

    @Test
    void getObjectsTest() {
        User user = new User("John", "Snow");
        when(restTemplate.getForEntity(REST_SERVICE_URL+"/firstName", User.class))
                .thenReturn(new ResponseEntity(user, HttpStatus.OK));
        User usr=controller.getUserData();
        assertEquals(user,usr);

    }

    @Test
    void displayFirstNameTest() {

        String name="John";
        when(restTemplate.getForEntity(REST_SERVICE_URL+"/firstName", String.class))
                .thenReturn(new ResponseEntity(name, HttpStatus.OK));
        String retName=controller.displayFirstName();
        assertEquals(name,retName);

    }

    @Test
    void displayLastNameTest() {


        String name="Snow";
        when(restTemplate.getForEntity(REST_SERVICE_URL+"/lastName", String.class))
                .thenReturn(new ResponseEntity(name, HttpStatus.OK));
        String retName=controller.displayLastName();
        assertEquals(name,retName);
    }

    @Test
    void postDataTest() {

    }

    @Test
    void postData2test() {
    }
}