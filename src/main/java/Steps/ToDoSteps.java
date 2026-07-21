package Steps;

import Runner.TestRunner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ToDoSteps extends TestRunner {

    public RemoteWebDriver driver = this.connection;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    @Given("^user is on Selenium Playground$")
    public void user_is_on_selenium_playground() {

        System.out.println(driver.getCapabilities());

        driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-message")));
    }

    @When("^user enters a message$")
    public void user_enters_a_message() {

        String message = "Hello TestMu AI";

        driver.findElement(By.id("user-message")).sendKeys(message);

        driver.findElement(By.id("showInput")).click();
    }

    @Then("^the message should be displayed$")
    public void the_message_should_be_displayed() {

        WebElement output = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("message"))
        );

        Assert.assertEquals(output.getText(), "Hello TestMu AI");
    }
}