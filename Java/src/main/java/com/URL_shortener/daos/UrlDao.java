package com.URL_shortener.daos;

import com.URL_shortener.models.URL;

import java.util.List;

public interface UrlDao {

     void createShortenedUrl(URL url);

     URL getShortenedUrl(String shortUrl);

     List<URL> getAllUrls();
}
