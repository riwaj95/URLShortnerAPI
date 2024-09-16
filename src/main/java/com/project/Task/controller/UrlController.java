package com.project.Task.controller;

import com.project.Task.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        String shortId = urlService.shortenUrl(originalUrl);
        String shortUrl = "http://short.url/" + shortId;
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortId}")
    public void redirectUrl(@PathVariable String shortId, HttpServletResponse response) throws IOException {
        // Resolve the original URL using the service
        String originalUrl = urlService.getOriginalUrl(shortId);

        // Perform a redirect to the original URL
        response.sendRedirect(originalUrl);
    }
}
