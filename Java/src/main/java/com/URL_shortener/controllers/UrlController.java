package com.URL_shortener.controllers;

import com.URL_shortener.daos.UrlDao;
import com.URL_shortener.models.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class UrlController {

    @Autowired
    private UrlDao urlDao;

    @PostMapping("/api/v1/url/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody URL url, @RequestParam int expirationTimeInHours) {
        if (url.getLongUrl() == null || url.getLongUrl().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("URL cannot be empty");
        }
        String shortUrl = generateShortUrl();
        url.setShortUrl(shortUrl);
        url.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusHours(expirationTimeInHours)));
        urlDao.createShortenedUrl(url);

        String fullShortenedUrl = "http://localhost:9000/"+shortUrl;
        return ResponseEntity.ok(fullShortenedUrl);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getShortenedUrl(@PathVariable String shortUrl){
        URL url = urlDao.getShortenedUrl(shortUrl);
        if( url!= null){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(url.getLongUrl()))
                    .build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/allUrls")
    public List<URL> getAllUrls(){
        return urlDao.getAllUrls();
    }


    private String generateShortUrl() {
        String randomCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int index = (int)(Math.random()*randomCharacters.length());
            sb.append(randomCharacters.charAt(index));
        }
        return sb.toString();
    }



}
