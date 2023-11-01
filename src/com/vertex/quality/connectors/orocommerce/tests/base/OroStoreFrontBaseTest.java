package com.vertex.quality.connectors.orocommerce.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontLoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * contains all the common functions to run the UI Tests
 *
 * @author alewis
 */
public class OroStoreFrontBaseTest extends VertexUIBaseTest<OroStoreFrontLoginPage> {
    protected String OroURL;
    protected EnvironmentInformation OroCommerceEnvironment;
    protected EnvironmentCredentials OroCommerceCredentials;
    protected String username;
    protected String password;

    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * Used to be able to set environment descriptor from the child base test
     *
     * @return the environment descriptor based on the base test
     */
    protected DBEnvironmentDescriptors getPortalEnvironmentDescriptor() {
        return DBEnvironmentDescriptors.ORO_STOREFRONT;
    }

    /**
     * Gets credentials for the connector from the database
     */
    @Override
    public OroStoreFrontLoginPage loadInitialTestPage() {
        try {
            OroCommerceEnvironment = SQLConnection.getEnvironmentInformation(DBConnectorNames.ORO_COMMERCE,
                    DBEnvironmentNames.QA, getPortalEnvironmentDescriptor());
            OroCommerceCredentials = getEnvironmentCredentials(OroCommerceEnvironment);
//            OroURL = OroCommerceEnvironment.getEnvironmentUrl();
//            username = OroCommerceCredentials.getUsername();
//            password = OroCommerceCredentials.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }

        OroStoreFrontLoginPage signOnPage = new OroStoreFrontLoginPage(driver);
        return signOnPage;
    }

    /**
     * @return a representation of this site's login screen
     */
    protected OroStoreFrontLoginPage loadSignOnPage() {
        OroStoreFrontLoginPage signOnPage = null;

//        String url = this.OroURL;
        String url = OroTestData.STOREFRONT_URL.data;
        driver.get(url);

        signOnPage = new OroStoreFrontLoginPage(driver);

        return signOnPage;
    }

    /**
     * Load storefront's sign on page
     * Used to load sign on page for version based oro
     *
     * @param url pass the version based url on which test to be executed
     * @return storefront's sign on page
     */
    protected OroStoreFrontLoginPage getStoreFrontSignOnPage(String url) {
        driver.get(url);
        return new OroStoreFrontLoginPage(driver);
    }

    /**
     * does the sign in process in the login page
     *
     * @return new object of the oro store front home page
     */
    protected OroStoreFrontHomePage signInToStoreFront(final OroStoreFrontLoginPage signOnPage) {
        return signOnPage.loginAsUser(this.username, this.password);
    }

    protected OroStoreFrontHomePage signInToStorefrontGovernment(final OroStoreFrontLoginPage signOnPage) {
        return signOnPage.loginAsUser("viraj.karekar@vertexinc.com", "Viraj@123");
    }

    /**
     * initializes the ChromeDriver in 1920*1080 resolution which interacts with the browser
     */
    @Override
    protected void createChromeDriver() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("window-size=1920,1080");
        options.addArguments("--disable-infobars");
        if (isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new ChromeDriver(options);
    }

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