package com.vertex.quality.connectors.magentoTap.common.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Test(groups = {"magento"})
public class MagentoBaseTest extends VertexUIBaseTest {

    private static ReadProperties readEnvUrls;
    private static ReadProperties readCredentials;
    private static final String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
    private static final String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
    protected String MAGENTO_ADMIN_URL;
    protected String MAGENTO_ADMIN_USERNAME;
    protected String MAGENTO_ADMIN_PASSWORD;
    protected String MAGENTO_STOREFRONT_URL;

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    public MagentoBaseTest() {
        initializeVariables();
    }

    /**
     * this method initializes credentials and url O-Series for login page
     */
    private void initializeVariables() {
        try {
            File testcredential = new File(TEST_CREDENTIALS_FILE_PATH);
            if (testcredential != null && testcredential.exists()) {
                readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
            } else {
                VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
                        EpiBaseTest.class);
            }

            File testPropFilePath = new File(ENV_PROP_FILE_PATH);
            if (testPropFilePath != null && testPropFilePath.exists()) {
                readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
            } else {
                VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
                        EpiBaseTest.class);
            }

            MAGENTO_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.M2.ADMIN.USERNAME");
            MAGENTO_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.M2.ADMIN.PASSWORD");
            MAGENTO_ADMIN_URL = readEnvUrls.getProperty("TEST.ENV.M2.ADMIN.URL");
            MAGENTO_STOREFRONT_URL = readEnvUrls.getProperty("TEST.ENV.M2.STOREFRONT.URL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the ChromeDriver which interacts with the browser
     */
    protected void createChromeDriver() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("window-size=1920,1080");
        options.addArguments("--disable-infobars");
        if (isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new ChromeDriver(options);
    }

    /**
     * used to get service url
     *
     * @return service url
     */
    private URL getDriverServiceUrl() {
        try {
            String hostname = "localhost";
            if (isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}