package Runner;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"Steps"},
        tags = {"~@Ignore"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner
{
    private TestNGCucumberRunner testNGCucumberRunner;
  
    public static RemoteWebDriver connection;
    
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber()
    {
    	 testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "version", "platform" })
    public void setUpClass(String browser, String version, String platform) throws Exception
    {
        String username = System.getenv("LT_USERNAME") == null ? "himanshujlambdatest" : System.getenv("LT_USERNAME");
        String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "s36wNWEKC2mnUe1E0D4k8835V0mIC2KRpCFymPEuPmM03wRszH" : System.getenv("LT_ACCESS_KEY");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version",version);
        capabilities.setCapability("platform", platform);

        capabilities.setCapability("build", "Demo: Cucumber TestNG on HyperTest Grid");

        capabilities.setCapability("network", true);
        capabilities.setCapability("video", true);
        capabilities.setCapability("console", true);
        capabilities.setCapability("visual", true);

        String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
        connection = new RemoteWebDriver(new URL(gridURL), capabilities);
        System.out.println(connection.getSessionId());
}
 
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature)
    {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
 
    @DataProvider
    public Object[][] features()
    {
        return testNGCucumberRunner.provideFeatures();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception
    {
        testNGCucumberRunner.finish();
    }
}