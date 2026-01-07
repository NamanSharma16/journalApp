package com.example.JournalApp.services;

import com.example.JournalApp.entities.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private static final String apiUrl = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponseDTO getWeather(String city) {
        String finalUrl = apiUrl.replace("API_KEY", apiKey).replace("CITY", city);
        ResponseEntity<WeatherResponseDTO> weatherResponse = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponseDTO.class);
        return weatherResponse.getBody();

    }
}
