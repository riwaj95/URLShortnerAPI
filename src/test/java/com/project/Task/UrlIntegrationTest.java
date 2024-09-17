package com.project.Task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Task.model.UrlEntity;
import com.project.Task.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        urlRepository.deleteAll();
    }

    @Test
    public void testShortenUrl() throws Exception {
        String originalUrl = objectMapper.writeValueAsString(Collections.singletonMap("originalUrl", "https://example.com"));;

        // Perform POST request to shorten the URL
        mockMvc.perform(post("/api/url/shorten")
                        .content(originalUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetOriginalUrl() throws Exception {
        // First, save a URL entity manually to the database
        UrlEntity savedEntity = new UrlEntity();
        savedEntity.setOriginalUrl("https://example.com");
        savedEntity.setShortId("abc123");
        savedEntity.setCreatedAt(java.time.LocalDateTime.now());
        urlRepository.save(savedEntity);

        // Perform GET request to resolve the short URL
        mockMvc.perform(MockMvcRequestBuilders.get("/api/url/abc123"))
                .andExpect(status().isFound())  // Expect a 302 redirect status
                .andExpect(MockMvcResultMatchers.header().string("Location", "https://example.com"));
    }
}
