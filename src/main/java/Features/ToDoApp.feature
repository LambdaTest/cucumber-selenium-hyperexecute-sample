Feature: Add new item to ToDo list

@ToDo
  Scenario: LambdaTest ToDo Scenario
    Given user is on home Page
    When select First Item
    Then select second item
    Then add new item
    Then verify added item