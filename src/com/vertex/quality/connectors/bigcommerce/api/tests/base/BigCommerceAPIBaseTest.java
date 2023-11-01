package com.vertex.quality.connectors.bigcommerce.api.tests.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.common.utils.BigCommerceAPITestUtilities;
import com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries.OSeriesBaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * base test class for big commerce api testing
 * contains methods that're used in many different types of test cases
 *
 * @author osabha ssalisbury
 */

public abstract class BigCommerceAPIBaseTest extends OSeriesBaseTest {
    protected BigCommerceAPITestUtilities apiUtil;

    //don't modify this dynamically- its value is based on the value in the api test utility object
    protected String baseUrl;

    protected final double standardLowPriceAmount = BigCommerceAPITestUtilities.STANDARD_LOW_PRICE_AMOUNT;
    protected final double standardMediumPriceAmount = BigCommerceAPITestUtilities.STANDARD_MEDIUM_PRICE_AMOUNT;
    protected final double standardHighPriceAmount = BigCommerceAPITestUtilities.STANDARD_HIGH_PRICE_AMOUNT;
    protected final int defaultQuantity = BigCommerceAPITestUtilities.DEFAULT_QUANTITY;
    protected final int quantityTen = BigCommerceAPITestUtilities.QUANTITY_TEN;
    protected final int quantityFive = BigCommerceAPITestUtilities.QUANTITY_FIVE;
    protected final int quantityThree = BigCommerceAPITestUtilities.QUANTITY_THREE;
    protected final int defaultQuantityRefund = BigCommerceAPITestUtilities.DEFAULT_ORDER_QUANTITY;
    protected final int defaultOrderQuantity = BigCommerceAPITestUtilities.DEFAULT_ORDER_QUANTITY;
    protected final int refundQuantity = BigCommerceAPITestUtilities.REFUND_QUANTITY;

    protected final String item1FedTaxRatePath = BigCommerceAPITestUtilities.ITEM_1_FED_TAX_RATE_PATH;
    protected final String item1LocalTaxRatePath = BigCommerceAPITestUtilities.ITEM_1_LOCAL_TAX_RATE_PATH;
    protected final String item1FedTaxAmountPath = BigCommerceAPITestUtilities.ITEM_1_FED_TAX_AMOUNT_PATH;
    protected final String item1LocalTaxAmountPath = BigCommerceAPITestUtilities.ITEM_1_LOCAL_TAX_AMOUNT_PATH;

    protected final String quote1Id = BigCommerceAPITestUtilities.QUOTE_1_ID;
    protected final String quote2Id = BigCommerceAPITestUtilities.QUOTE_2_ID;
    protected final String quote3Id = BigCommerceAPITestUtilities.QUOTE_3_ID;
    protected final String quote4Id = BigCommerceAPITestUtilities.QUOTE_4_ID;
    protected final String quote5Id = BigCommerceAPITestUtilities.QUOTE_5_ID;
    protected final String quote6Id = BigCommerceAPITestUtilities.QUOTE_6_ID;
    protected final String document1Id = BigCommerceAPITestUtilities.DOCUMENT_1_ID;
    protected final String document2Id = BigCommerceAPITestUtilities.DOCUMENT_2_ID;
    protected final String document3Id = BigCommerceAPITestUtilities.DOCUMENT_3_ID;
    protected final String document4Id = BigCommerceAPITestUtilities.DOCUMENT_4_ID;
    protected final String document5Id = BigCommerceAPITestUtilities.DOCUMENT_5_ID;

    protected final String customer1Id = BigCommerceAPITestUtilities.CUSTOMER_1_ID;
    protected final String customer2Id = BigCommerceAPITestUtilities.CUSTOMER_2_ID;
    protected final String customer3Id = BigCommerceAPITestUtilities.CUSTOMER_3_ID;
    protected final String customer4Id = BigCommerceAPITestUtilities.CUSTOMER_4_ID;
    protected final String customer5Id = BigCommerceAPITestUtilities.CUSTOMER_5_ID;

    protected final String shipping1Id = BigCommerceAPITestUtilities.SHIPPING_1_ID;
    protected final String shipping2Id = BigCommerceAPITestUtilities.SHIPPING_2_ID;
    protected final String shipping3Id = BigCommerceAPITestUtilities.SHIPPING_3_ID;
    protected final String shipping4Id = BigCommerceAPITestUtilities.SHIPPING_4_ID;
    protected final String shipping5Id = BigCommerceAPITestUtilities.SHIPPING_5_ID;

    protected final String handling1Id = BigCommerceAPITestUtilities.HANDLING_1_ID;
    protected final String handling2Id = BigCommerceAPITestUtilities.HANDLING_2_ID;
    protected final String handling3Id = BigCommerceAPITestUtilities.HANDLING_3_ID;
    protected final String handling4Id = BigCommerceAPITestUtilities.HANDLING_4_ID;
    protected final String handling5Id = BigCommerceAPITestUtilities.HANDLING_5_ID;

    protected final String item1Id = BigCommerceAPITestUtilities.ITEM_1_ID;
    protected final String item2Id = BigCommerceAPITestUtilities.ITEM_2_ID;
    protected final String item3Id = BigCommerceAPITestUtilities.ITEM_3_ID;
    protected final String item4Id = BigCommerceAPITestUtilities.ITEM_4_ID;
    protected final String item5Id = BigCommerceAPITestUtilities.ITEM_5_ID;
    protected final String item6Id = BigCommerceAPITestUtilities.ITEM_6_ID;
    protected final String item7Id = BigCommerceAPITestUtilities.ITEM_7_ID;
    protected final String item8Id = BigCommerceAPITestUtilities.ITEM_8_ID;
    protected final String item9Id = BigCommerceAPITestUtilities.ITEM_9_ID;
    protected final String item10Id = BigCommerceAPITestUtilities.ITEM_10_ID;

    protected final String wrapping1Id = BigCommerceAPITestUtilities.WRAPPING_1_ID;
    protected final String wrapping2Id = BigCommerceAPITestUtilities.WRAPPING_2_ID;
    protected final String wrapping3Id = BigCommerceAPITestUtilities.WRAPPING_3_ID;
    protected final String wrapping4Id = BigCommerceAPITestUtilities.WRAPPING_4_ID;
    protected final String wrapping5Id = BigCommerceAPITestUtilities.WRAPPING_5_ID;

    protected WebDriver driver;

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    /**
     * performs the setup which is necessary for big commerce api tests
     *
     * @author ssalisbury
     */
    @Override
    protected void setupTestCase() {
        this.apiUtil = new BigCommerceAPITestUtilities();
        this.baseUrl = apiUtil.getBaseUrl();
    }

    /**
     * initializes the ChromeDriver which interacts with the browser
     *
     * @author rohit-mogane
     */
    protected void createDriver() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");

        if (isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new ChromeDriver(options);
    }

    /**
     * quits current ChromeDriver session
     *
     * @author rohit-mogane
     */

    protected void quitDriver() {
        VertexLogger.log("Quitting ChromeDriver...");
        if (driver != null)
            driver.quit();
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

}
