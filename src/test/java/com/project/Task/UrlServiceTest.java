package com.project.Task;

import com.project.Task.exception.UrlNotFoundException;
import com.project.Task.model.UrlEntity;
import com.project.Task.repository.UrlRepository;
import com.project.Task.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Mock
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
        assertEquals(7, shortId.length());
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

    @Test
    public void testResolveShortUrlThrowsExceptionWhenNotFound() {
        when(urlRepository.findByShortId("invalid")).thenReturn(Optional.empty());
        Exception exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.getOriginalUrl("invalid");
        });
        assertEquals("Short URL not found", exception.getMessage());
    }

    @Test
    public void testGetOriginalCaching(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortId("abc123");
        urlEntity.setOriginalUrl("https://test12.com");

        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("urls")).thenReturn(cache);

        // Simulate cache miss on first call
        when(cache.get("abc123", String.class)).thenReturn(null);

        // Simulate repository returning the URL entity
        when(urlRepository.findByShortId("abc123")).thenReturn(Optional.of(urlEntity));

        // First call should hit the repository
        String originalUrl = urlService.getOriginalUrl("abc123");
        assertEquals("https://test12.com", originalUrl);

        // Simulate cache hit on second call
        when(cache.get("abc123", String.class)).thenReturn("https://test12.com");

        // Second call should return the cached value
        String cachedUrl = urlService.getOriginalUrl("abc123");
        assertEquals("https://test12.com", cachedUrl);

        //todo
        verify(urlRepository, times(2)).findByShortId("abc123");
    }

}
