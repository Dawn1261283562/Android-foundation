package com.example.demo1.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient {
    public String client(String url, HttpMethod method,
                         MultiValueMap<String,String>params
                         ){
        RestTemplate template =new RestTemplate();
        ResponseEntity<String>response1=template.getForEntity(url,String.class);
        String R=response1.getBody();
        System.out.println(R);
        return response1.getBody();
    }
    

}
