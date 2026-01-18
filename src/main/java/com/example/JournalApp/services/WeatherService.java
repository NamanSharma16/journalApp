package com.example.JournalApp.services;

import com.example.JournalApp.cache.AppCache;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponseDTO getWeather(String city) {
        String finalUrl = appCache.APP_CACHE.get("weather_api_uri").replace("<apiKey>", apiKey).replace("<city>", city);
        ResponseEntity<WeatherResponseDTO> weatherResponse = restTemplate.exchange(finalUrl,
                HttpMethod.GET, null, WeatherResponseDTO.class);
        return weatherResponse.getBody();

    }
}
