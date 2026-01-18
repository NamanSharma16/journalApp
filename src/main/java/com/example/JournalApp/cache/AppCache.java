package com.example.JournalApp.cache;

import com.example.JournalApp.entities.ConfigConstants;
import com.example.JournalApp.repository.ConfigConstantsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigConstantsRepository configConstantsRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigConstants> allConfigConstants = configConstantsRepository.findAll();
        APP_CACHE = allConfigConstants.stream()
                .collect(java.util.stream.Collectors.toMap(ConfigConstants::getKey, ConfigConstants::getValue));
    }
}
