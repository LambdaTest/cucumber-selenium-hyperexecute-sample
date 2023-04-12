Feature: BingSearchLT

@BingSearch
  Scenario: Perform Bing Search for LambdaTest
    Given that I am on the Bing app
    When search for LambdaTest
    Then click on the first result