package com.project.Task;

import com.project.Task.model.UrlEntity;
import com.project.Task.repository.UrlRepository;
import com.project.Task.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testShortenUrl() {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setId(123L);
        urlEntity.setOriginalUrl("https://test.com");
        urlEntity.setCreatedAt(LocalDateTime.now());

        when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);

        String shortId = urlService.shortenUrl("https://test.com");

        assertNotNull(shortId);
        assertEquals(2, shortId.length());
    }

    @Test
    public void testResolveShortUrl() {

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortId("ab123");
        urlEntity.setOriginalUrl("https://test.com");


        when(urlRepository.findByShortId("ab123")).thenReturn(Optional.of(urlEntity));


        String originalUrl = urlService.getOriginalUrl("ab123");


        assertEquals("https://test.com", originalUrl);
    }

}
