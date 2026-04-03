package Steps;

import Runner.TestRunner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BingSearchSteps extends TestRunner {

    public RemoteWebDriver driver = this.connection;
    public static String test_url = "https://www.bing.com";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("^that I am on the Bing app$")
    public void that_I_am_on_the_Bing_app() {
        System.out.println(driver.getCapabilities());
        driver.navigate().to(test_url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @When("^search for LambdaTest$")
    public void click_on_the_text_box() {
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@id='sb_form_q']"));
        searchBox.sendKeys("LambdaTest");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchBox.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^click on the first result$")
    public void click_on_the_first_result() {
        String title = driver.getTitle();
        System.out.println(title);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}