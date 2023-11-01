package com.vertex.quality.connectors.woocommerceTap.admin.tests.base;


import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.woocommerceTap.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerceTap.admin.pages.WooCommerceAdminLoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * Class for common methods for woocommerce Admin UI
 *
 * @author Vivek.Kumar
 */
public class WooCommerceAdminBaseTest extends VertexUIBaseTest<WooCommerceAdminLoginPage> {

    protected String signInUsername;
    protected String signInPassword;
    protected String wooCommerceUrl;
    protected String environmentURL;
    protected EnvironmentInformation wooCommerceEnvironment;
    protected EnvironmentCredentials wooCommerceCredentials;

    private DBEnvironmentDescriptors getEnvironmentDescriptor() {
        return DBEnvironmentDescriptors.WOOCOMMERCE_ADMIN;
    }

    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * gets sign on information such as username, password, and url from SQL server
     */
    @Override
    public WooCommerceAdminLoginPage loadInitialTestPage() {
        try {

            wooCommerceEnvironment = SQLConnection.getEnvironmentInformation(DBConnectorNames.WOOCOMMERCE, DBEnvironmentNames.QA,
                    getEnvironmentDescriptor());
            wooCommerceCredentials = getEnvironmentCredentials(wooCommerceEnvironment);
            wooCommerceUrl = wooCommerceEnvironment.getEnvironmentUrl();
            signInUsername = wooCommerceCredentials.getUsername();
            signInPassword = wooCommerceCredentials.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }


        WooCommerceAdminLoginPage signInPage = loadSignOnPage();
        return signInPage;
    }

    /**
     * method for getting url and loading signOn Page.
     *
     * @return signOnPage after getting connector url.
     */
    protected WooCommerceAdminLoginPage loadSignOnPage() {
        WooCommerceAdminLoginPage signOnPage;

        String url = this.wooCommerceUrl;

        driver.get(url);

        signOnPage = new WooCommerceAdminLoginPage(driver);

        return signOnPage;
    }

    /**
     * method for signing into connector url.
     *
     * @param signOnPage for WooCommerceAdminLoginPage
     * @return signOnPage WooCommerceAdminHomePage after logging into connector url.
     */
    protected WooCommerceAdminHomePage signInToAdmin(final WooCommerceAdminLoginPage signOnPage) {
        return signOnPage.loginAsUser(signInUsername, signInPassword);
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

