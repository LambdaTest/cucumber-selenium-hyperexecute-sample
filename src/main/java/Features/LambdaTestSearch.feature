Feature: DuckDuckGoLTBlog
  Open DuckDuckGo
  Search for LambdaTest Blog on the page
  Check results

@LambdaTestBlogSearch
Scenario: Search LambdaTest on DuckDuckGo
    Given that I am on the DuckDuckGo Search Page
    Then search for LambdaTest Blog
    Then click on the available result
    Then compare results