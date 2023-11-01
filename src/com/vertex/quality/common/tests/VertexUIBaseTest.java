package com.vertex.quality.common.tests;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexFileUtils;
import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Base test for all of our automated tests that use selenium. Handles common functionality such
 * as starting the webdriver, etc.
 *
 * @author dgorecki ssalisbury, dpatel
 */
@Test(groups = {"ui"})
public abstract class VertexUIBaseTest<T extends VertexPage> extends VertexBaseTest {
    //protected static ChromeDriverService service;
    protected WebDriver driver;
    protected final String errorScreenshotsPath = "errorScreenshots";
    protected final String testFailureManualDebuggingEnvVariable = "test_failure_manual_debugging";
    protected final String envVariableEnablingValue = "true";

    //warning- never make this protected, it will break all test subclasses
    public T testStartPage = null;
    public String browser = "chrome";

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * performs setup that's necessary before any selenium test suite
     *
     * @author dgorecki ssalisbury
     */
    @Override
    protected void setupTestRun() {
        try {
            setupScreenshotsDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        browser = getBrowser();

        createDriverService(browser);
    }

    /**
     * This handles setup steps which are necessary for all selenium-based test cases
     * <p>
     * Be careful about overriding this- most or all connectors' base tests
     * should override {@link #loadInitialTestPage()} instead
     *
     * @author ssalisbury
     */
    @Override
    protected void setupTestCase() {
        browser = getBrowser();

        createDriver(browser);

        this.testStartPage = loadInitialTestPage();
    }

    /**
     * performs cleanup that's necessary after any selenium test suite
     *
     * @author dgorecki ssalisbury
     */
    @Override
    protected void cleanupTestRun() {
        stopDriverService();
    }

    /**
     * This handles cleanup steps which are necessary for all selenium-based test cases
     * <p>
     * Be careful about overriding this- most or all connectors' base tests
     * should override {@link #handleLastTestPage(ITestResult)} instead
     *
     * @author dgorecki ssalisbury,dpatel
     */
    @Override
    protected void cleanupTestCase(final ITestResult testResult) {
        try {
            if (testResult.getStatus() != ITestResult.SUCCESS) {
                String testname = testResult.getName();
                takeScreenshot(testname);

                handleManualDebuggingPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            handleLastTestPage(testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            quitDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileUtils.deleteDirectory(new File(VertexPage.DOWNLOAD_DIRECTORY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a stand-in for connector-specific setup that needs to be done before a selenium test case
     * to load the first page of that test
     * Such connectors just override this function to run the necessary helper functions in that connector
     * Note- it's a good idea to call super.loadInitialTestPage() at the start of an override of this function
     *
     * @return the page that the test case will start on
     * @author ssalisbury
     */
    protected T loadInitialTestPage() {
        return null;
    }

    /**
     * This is a stand-in for connector-specific cleanup that needs to be done at the end of each selenium-based  test
     * case
     * Such connectors just override this function to run the necessary helper functions in that connector
     * Note- it's a good idea to call super.handleLastTestPage() at the end of an override of this function
     *
     * @param testResult a representation of the result of the test case
     * @author ssalisbury
     */
    protected void handleLastTestPage(final ITestResult testResult) {
    }

    /**
     * takes a screenshot of the browser window
     *
     * @param testname the name of the test case which is currently running
     * @author dgorecki
     */
    protected void takeScreenshot(final String testname) {
        handleFatalAlertIfPresent();

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String outputFileName = String.format("%s/%s.png", errorScreenshotsPath, testname);
        File outputFile = new File(outputFileName);

        String screenshotMessage = String.format("Taking screenshot and copying to - %s", outputFile.getAbsolutePath());
        VertexLogger.log(screenshotMessage);

        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the server service which the various test cases' ChromeDriver instances run on
     *
     * @author dgorecki, dpatel
     */
    protected void createChromeDriverService() {
        if (isDriverServiceProvisioned) {
            VertexLogger.log("Externally provisioned driver service. Skipping start Chrome driver service.");
            return;
        }
        WebDriverManager.chromedriver().setup();
    }

    /**
     * terminates the server service which the various test cases' ChromeDriver instances run on
     *
     * @author dgorecki,dpatel
     */
    protected void stopDriverService() {
        if (isDriverServiceProvisioned) {
            VertexLogger.log("Externally provisioned driver service. Skipping stop driver service.");
            return;
        }
        VertexLogger.log("Stopping ChromeDriver Service...");
        //service.stop(); @dpatel
    }

    /**
     * initializes the ChromeDriver which interacts with the browser
     *
     * @author dgorecki,dpatel
     */
    protected void createChromeDriver() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        // Use incognito window for ChromeDriver if running tests on local machine, workaround for CONIN-106
        if(!isDriverServiceProvisioned) {
            options.addArguments("--incognito");
            options.addArguments("--remote-debugging-port=9022");
        }
        if(isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new ChromeDriver(options);
    }

    protected void createFirefoxDriverService() {
        if (isDriverServiceProvisioned) {
            VertexLogger.log("Externally provisioned driver service. Skipping start driver service.");
            return;
        }
        WebDriverManager.firefoxdriver().setup();
    }

    protected void createFirefoxDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("profile.default_content_settings.popups", 0);
        profile.setPreference("browser.download.dir",VertexPage.DOWNLOAD_DIRECTORY_PATH);
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(FirefoxDriver.PROFILE, profile);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        if(isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new FirefoxDriver(options);
    }

    protected void createDriver(String browserName)
    {
        if(browserName.equalsIgnoreCase("firefox"))
        {
            createFirefoxDriver();
            return;
        }

        createChromeDriver();
    }

    protected void createDriverService(String browserName)
    {
        if(browserName.equalsIgnoreCase("firefox"))
        {
            createFirefoxDriverService();
            return;
        }

        createChromeDriverService();
    }

    private URL getDriverServiceUrl() {
        try {
            String hostname = "localhost"; 
            if(isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getBrowser()
    {
        String browserValue = System.getProperty("browser");
        if (browserValue == null)
            browserValue = browser;
        return browserValue;
    }

    /**
     * terminates the ChromeDriver which interacted with the browser
     *
     * @author dgorecki
     */
    protected void quitDriver() {
        VertexLogger.log("Quitting ChromeDriver...");
        driver.quit();
    }

    /**
     * Sets up screenshots folder using default path
     *
     * @author dgorecki
     */
    protected void setupScreenshotsDirectory() {
        setupScreenshotsDirectory(errorScreenshotsPath);
    }

    /**
     * Sets up directory for storing screenshots.  Clears any pre-exsiting screenshots if present
     *
     * @param directoryPath a string representing the path of the location to store the screenshots
     * @author ssalisbury dgorecki
     */
    protected void setupScreenshotsDirectory(String directoryPath) {
        File errorScreenShotsDir = new File(directoryPath);

        if (errorScreenShotsDir.exists()) {
            //if the directory already exists, clear all files in it
            VertexFileUtils.deleteFilesInDirectory(errorScreenShotsDir);
        } else {
            //otherwise attempt to create it
            try {
                Files.createDirectory(Paths.get(directoryPath));
            } catch (Exception e) {
                VertexLogger.log("Failed to create error screenshots folder");
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks whether an alert is present when the test has failed and if so logs and clears it
     *
     * @author ssalisbury dgorecki
     */
    private void handleFatalAlertIfPresent() {
        VertexAlertUtilities alertUtil = new VertexAlertUtilities(driver);

        //if there was an unexpected alert that killed the test, we already know an alert was present
        //so no need to wait for one, adding up to 3 seconds to runtime in current implementation
        //just see if one is there or not
        boolean isAlertPresent = alertUtil.isAlertPresent();

        if (isAlertPresent) {
            String alertText = alertUtil.getAlertText();

            String alertWarningMessage = String.format("Test failed due to unexpected alert: [%s]", alertText);
            VertexLogger.log(alertWarningMessage, VertexLogLevel.ERROR);

            alertUtil.acceptAlert();
        }
    }

    /**
     * this checks whether the current test run is configured to stop when a test fails and wait for some time so that
     * the webpage can be thoroughly examined by the human tester before the test case is torn down and the next test
     * case starts
     */
    protected void handleManualDebuggingPause() {
        //support for manual debugging of test failures if the current test run was configured to do that
        String manualDebugEnvVariableValue = System.getenv(testFailureManualDebuggingEnvVariable);
        if (envVariableEnablingValue.equals(manualDebugEnvVariableValue)) {
            VertexLogger.log("starting long sleep for manual debugging purposes");
            final long twoMinutesInSeconds = 120;

            WebDriverWait debuggingWaiter = new WebDriverWait(driver, twoMinutesInSeconds);
            //wait for the whole timeout duration
            try {
                debuggingWaiter.until(driver -> false);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
