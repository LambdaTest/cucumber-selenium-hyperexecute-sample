package Steps;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

import Runner.TestRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.concurrent.TimeUnit;

public class BingSearchSteps extends TestRunner
{

    public RemoteWebDriver driver = this.connection;
    public static String test_url = "https://www.bing.com";
    WebDriverWait wait = new WebDriverWait(driver, 10);

    @Given("^that I am on the Bing app$")
    public void that_I_am_on_the_Bing_app()
    {
        System.out.println(driver.getCapabilities());
        driver.navigate().to(test_url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //wait.until((ExpectedCondition<Boolean>) wd ->
        //        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    @When("^search for LambdaTest$")
    public void click_on_the_text_box()
    {
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@id='sb_form_q']"));
        searchBox.sendKeys("HyperExecute");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchBox.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^click on the first result$")
    public void click_on_the_first_result()
    {
        WebElement secondCheckBox = driver.findElement(By.xpath("//a[contains(normalize-space(),'HyperExecute - AI-Powered Blazing Fast')]"));
        secondCheckBox.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}