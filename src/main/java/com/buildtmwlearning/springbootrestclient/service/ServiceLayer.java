package com.buildtmwlearning.springbootrestclient.service;

import com.buildtmwlearning.springbootrestclient.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceLayer {
    private final RestTemplate restTemplate;

    @Autowired
    public ServiceLayer(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }


    String url="https://breaking-bad-quotes.herokuapp.com/v1/quotes";


    public User consumeAPI(){
        User forObject = restTemplate.getForObject(url, User.class);
        System.out.println(forObject);
        return forObject;
    }


}
