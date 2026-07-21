package Steps;

import Runner.TestRunner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class LambdaTestSearchSteps extends TestRunner {

    public RemoteWebDriver driver = this.connection;

    @Given("^that I am on the DuckDuckGo Search Page$")
    public void user_on_duck_duck_go_page() {
        System.out.println(driver.getCapabilities());

        driver.get("https://duckduckgo.com/");

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
    }

    @Then("^search for LambdaTest Blog$")
    public void search_for_lambdatest_blog() {

        WebElement searchBox = driver.findElement(By.name("q"));

        searchBox.sendKeys("LambdaTest Blog");
        searchBox.sendKeys(Keys.ENTER);
    }

    @Then("^click on the available result$")
    public void click_n_available_result() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement result = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(.,'LambdaTest')]")
                )
        );

        result.click();
    }

    @Then("^compare results$")
    public void compare_result() {

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.titleContains("LambdaTest"));

        assertTrue(driver.getTitle().contains("LambdaTest"));
    }
}