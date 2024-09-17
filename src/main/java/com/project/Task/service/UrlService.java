package com.project.Task.service;

import com.project.Task.exception.UrlNotFoundException;
import com.project.Task.model.UrlEntity;
import com.project.Task.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setCreatedAt(LocalDateTime.now());

        String shortId = encodeBase62(System.currentTimeMillis());
        urlEntity.setShortId(shortId);
        // Save the UrlEntity with the shortId
        urlRepository.save(urlEntity);
        return shortId;
    }

    public List<UrlEntity> getAllUrls() {
        return urlRepository.findAll();
    }

    @Cacheable(value = "urls", key = "#shortId")
    public String getOriginalUrl(String shortId) {
        Optional<UrlEntity> optionalUrlEntity = urlRepository.findByShortId(shortId);
        return optionalUrlEntity
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));
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
