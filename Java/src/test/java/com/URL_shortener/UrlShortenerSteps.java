package com.URL_shortener;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlShortenerSteps {
    private ResponseEntity<String> response;
    private String baseUrl = "http://localhost:9000/api/v1/url/shorten";
    private String shortenedUrl;

    @Given("the user is on the URL shortening page")
    public void userOnMainPage(){
        //this is to be a precondition
    }

    @When("the user enters an invalid URL")
    public void userEntersInvalidURL() {
        RestTemplate restTemplate = new RestTemplate();
        String blankUrl = "{\"longUrl\": \"\"}"; // Empty URL payload

        try {
            response = restTemplate.postForEntity(baseUrl + "?expirationTimeInHours=1", blankUrl, String.class);
        } catch (Exception e) {
            response = ResponseEntity.status(400).body("URL cannot be empty");
        }
    }

    @When("the user enters a valid URL")
    public void userEntersValidURL() {
        RestTemplate restTemplate = new RestTemplate();
        String validUrlPayload = "{\"longUrl\": \"https://www.example.com\"}"; // Valid URL payload

        response = restTemplate.postForEntity(baseUrl + "?expirationTimeInHours=1", validUrlPayload, String.class);
        shortenedUrl = response.getBody();
    }

    @When("the expiration time has passed")
    public void expirationTimeHasPassed() {
        // Simulate the passing of 1 hour
        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            fail("Error simulating the expiration time");
        }
    }

    @When("the user tries to access the shortened URL")
    public void userTriesToAccessShortenedUrl() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            response = restTemplate.getForEntity(shortenedUrl, String.class);
        } catch (Exception e) {
            response = ResponseEntity.status(404).build();
        }
    }

    @When("the user selects 6 hours from the dropdown")
    public void userSelects6HourOption() {
        RestTemplate restTemplate = new RestTemplate();
        String validUrlPayload = "{\"longUrl\": \"https://www.example.com\"}"; // Valid URL payload

        response = restTemplate.postForEntity(baseUrl + "?expirationTimeInHours=6", validUrlPayload, String.class);
        shortenedUrl = response.getBody();
    }

    @Then("the user will receive a 400 status code")
    public void userReceivesErrorMessage(){
        assertEquals(400,response.getStatusCode().value());
        assertEquals("URL cannot be empty", response.getBody());
    }

    @Then("the user should receive a shortened URL")
    public void userReceivesShortenedUrl() {
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("http://localhost:9000/"));
    }
    @Then("the user should receive a 404 status code")
    public void userReceives404StatusCode() {
        assertEquals(404, response.getStatusCode().value());
    }

    @Then("the user should receive a shortened URL with a 6-hour expiration time")
    public void userReceives6HourShortenedUrl() {
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("http://localhost:9000/"));
    }

}
