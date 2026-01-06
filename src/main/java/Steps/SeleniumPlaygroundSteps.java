package Steps;

import Runner.TestRunner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumPlaygroundSteps extends TestRunner {
    public RemoteWebDriver driver = this.connection;
    String test_url = "https://www.lambdatest.com/selenium-playground/";

    @Given("^I go to Selenium Playground home page$")
    public void user_on_selenium_playground_page() {
        System.out.println(driver.getCapabilities());
        driver.get(test_url);
    }

    @Then("^I Click on Input Form Link$")
    public void click_on_input_form_link() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//a[.='Input Form Submit']"));
        element.click();
        Thread.sleep(2000);
    }

    @Then("^I enter items in the form$")
    public void ThenEnterItems() throws InterruptedException {
        WebElement name = driver.findElement(By.xpath("//input[@id='name']"));
        name.sendKeys("Testing");
        Thread.sleep(2000);

        WebElement email_address = driver.findElement(By.id("inputEmail4"));
        email_address.sendKeys("testing@testing.com");
        Thread.sleep(2000);

        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("password");
        Thread.sleep(2000);

        WebElement company = driver.findElement(By.cssSelector("#company"));
        company.sendKeys("LambdaTest");
        Thread.sleep(2000);

        WebElement website = driver.findElement(By.cssSelector("#websitename"));
        website.sendKeys("https://wwww.lambdatest.com");
        Thread.sleep(2000);

        WebElement countryDropDown = driver.findElement(By.xpath("//select[@name='country']"));
        Select selectElement = new Select(countryDropDown);
        selectElement.selectByIndex(6);
        Thread.sleep(2000);

        WebElement city = driver.findElement(By.xpath("//input[@id='inputCity']"));
        city.sendKeys("San Jose");
        Thread.sleep(2000);

        WebElement address1 = driver.findElement(By.cssSelector("[placeholder='Address 1']"));
        address1.sendKeys("Googleplex, 1600 Amphitheatre Pkwy");
        Thread.sleep(2000);

        WebElement address2 = driver.findElement(By.cssSelector("[placeholder='Address 2']"));
        address2.sendKeys(" Mountain View, CA 94043");
        Thread.sleep(2000);

        WebElement state = driver.findElement(By.cssSelector("#inputState"));
        state.sendKeys("California");
        Thread.sleep(2000);

        WebElement zipcode = driver.findElement(By.cssSelector("#inputZip"));
        zipcode.sendKeys("94088");
        Thread.sleep(2000);
    }

    @Then("^I click submit button$")
    public void WhenClickSubmitButton() throws InterruptedException {
        /* Click on the Submit button */
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#seleniumform > div.text-right.mt-20 > button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        Thread.sleep(2000);
    }

    @Then("^I should verify if form submission was successful$")
    public void ThenVerifySubmitSuccessful() {
        /* Assert if the page contains a certain text */
        Boolean bValue = driver.getPageSource().contains
                ("Thanks for contacting us, we will get back to you shortly");

        if (bValue) {
            System.out.println("Input Form Demo successful");
        } else {
            System.out.println("Input Form Demo failed");
        }
    }
}
