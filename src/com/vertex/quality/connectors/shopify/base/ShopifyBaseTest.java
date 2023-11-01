package com.vertex.quality.connectors.shopify.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Base page for Shopify UI tests, handles initializing the browsers, variables etc...
 *
 * @author Shivam.Soni
 */
public class ShopifyBaseTest extends VertexUIBaseTest {

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * initializes the ChromeDriver which interacts with the browser
     */
    protected void createChromeDriver() {
		System.setProperty("webdriver.chrome.driver","C:\\Software\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
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
        options.addArguments("--incognito");
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
