package Runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;
import java.util.HashMap;

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"Steps"},
        tags = "not @Ignore",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })

public class TestRunner {
    private TestNGCucumberRunner testNGCucumberRunner;

    public static RemoteWebDriver connection;

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "version", "platform"})
    public void setUpClass(String browser, String version, String platform) throws Exception {
        String username = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");

        MutableCapabilities browserOptions;
        if (browser.equalsIgnoreCase("chrome")) {
            browserOptions = new ChromeOptions();
        } else if (browser.equalsIgnoreCase("firefox")) {
            browserOptions = new FirefoxOptions();
        } else if (browser.equalsIgnoreCase("Microsoft Edge")) {
            browserOptions = new EdgeOptions();
        } else {
            browserOptions = new ChromeOptions();
        }

        browserOptions.setCapability("platformName", platform);
        browserOptions.setCapability("browserVersion", version);

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("build", "Demo: Cucumber TestNG on HyperTest Grid");
        ltOptions.put("network", true);
        ltOptions.put("video", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
        connection = new RemoteWebDriver(new URL(gridURL), browserOptions);
        System.out.println(connection.getSessionId());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}
