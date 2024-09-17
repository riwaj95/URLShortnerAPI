package com.project.Task.controller;

import com.project.Task.model.UrlEntity;
import com.project.Task.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        if (originalUrl == null || originalUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid URL");
        }
        String shortId = urlService.shortenUrl(originalUrl);
        String shortUrl = "http://short.url/" + shortId;
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortId}")
    public void redirectUrl(@PathVariable String shortId, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortId);
        response.sendRedirect(originalUrl);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UrlEntity>> getAllUrls() {
        List<UrlEntity> allUrls = urlService.getAllUrls();
        return ResponseEntity.ok(allUrls);
    }
}
