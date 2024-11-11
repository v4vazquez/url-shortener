Feature: Url Shortener testing

  Scenario A URL will not be created if the field is empty or blank
    Given the user is on the URL shortening page
    When the user does not enter a valid URL
    And submits the form
    Then the user will receive a 400 status code

  Scenario A valid URL is successfully shortened
    Given the user is on the URL shortening page
    When the user enters a valid URL
    And submits the form
    Then the user should receive a shortened URL

  Scenario A user tries to access their shortened URL it expires
    Given the user has a shortened URL with the 1 hour expiration choice
    When the expiration time has passed
    And the user tries to access the shortened URL
    Then the user should receive a 404  status code


  Scenario A user selects the 6 hour item from the dropdown
    Given the user is on the URL shortening page
    When the user selects 6 hours from the dropdown
    And submits a valid URL
    Then the user should receive a shortened URL with a 6-hour expiration time