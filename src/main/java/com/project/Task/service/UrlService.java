package com.project.Task.service;

import com.project.Task.model.UrlEntity;
import com.project.Task.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setCreatedAt(LocalDateTime.now());
        UrlEntity savedUrl = urlRepository.save(urlEntity);
        String shortId = encodeBase62(savedUrl.getId());
        savedUrl.setShortId(shortId);
        urlRepository.save(savedUrl);
        return shortId;
    }

    public String getOriginalUrl(String shortId){
        Optional<UrlEntity> optionalUrlEntity = urlRepository.findByShortId(shortId);
        return optionalUrlEntity
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
    }

    private String encodeBase62(Long id) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(characters.charAt((int) (id % 62)));
            id /= 62;
        }
        return shortUrl.reverse().toString();
    }
}
