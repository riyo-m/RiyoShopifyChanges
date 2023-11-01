package com.vertex.quality.connectors.sitecorexc.tests.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.sitecorexc.pages.SitecoreXCPage;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * SitecoreXC base page that contains to initialize Chrome browser
 *
 * @author Shivam.Soni
 */
public class SitecoreXCBasePage extends VertexUIBaseTest<SitecoreXCPage> {

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * initializes the ChromeDriver which interacts with the browser
     */
    @Override
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
        driver = new RemoteWebDriver(getDriverServiceUrl(), options);
    }

    /**
     * used to get service url
     *
     * @return service url
     */
    private URL getDriverServiceUrl() {
        try {
            // SitecoreXC don't have public-facing URL for now & it is only accessible through Remote machine through Vertex VPN.
            // As of now, only local execution is possible for SitecoreXC
            // so that, we have Intentionally removed other options & directly passing the accessible ip of that remote machine.
            String sc1010IP = "18.189.219.215";
            String sc1030IP = "3.14.204.145";
            String ec2InstanceIP = "172.28.11.20";
            return new URL("http://" + ec2InstanceIP + ":4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
