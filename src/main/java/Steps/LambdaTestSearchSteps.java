package Steps;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.testng.Assert.assertEquals;
import org.openqa.selenium.WebElement;

import Runner.TestRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LambdaTestSearchSteps extends TestRunner
{

    public RemoteWebDriver driver = this.connection;
    String test_url = "https://www.duckduckgo.com/";
    String expected_title = "LambdaTest Blogs";

    @Given("^that I am on the DuckDuckGo Search Page$")
    public void user_on_duck_duck_go_page()
    {
        System.out.println(driver.getCapabilities());
        driver.get(test_url);
    }

    @Then("^search for LambdaTest Blog$")
    public void search_for_lambdatest_blog() throws InterruptedException
    {
        WebElement search_box = driver.findElement(By.cssSelector("#search_form_input_homepage"));
        search_box.click();
        search_box.sendKeys("LambdaTest Blog" + Keys.ENTER);
        Thread.sleep(2000);
    }

    @Then("^click on the available result$")
    public void click_n_available_result() throws InterruptedException
    {
        WebElement search_result = driver.findElement(By.xpath("//div[@id='links']/div[1]//a[.='LambdaTest Blogs']"));
        search_result.click();
        Thread.sleep(2000);
    }

    @Then("^compare results$")
    public void compare_result()
    {
        String page_title = driver.getTitle();
        assertEquals(page_title, expected_title);
    }
}