package com.vertex.quality.connectors.dynamics365.commerce.tests;

import com.vertex.quality.connectors.dynamics365.commerce.enums.CommerceDeliveryOptions;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCartPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCheckoutPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceHomePage;
import com.vertex.quality.connectors.dynamics365.commerce.tests.base.DCommerceBaseTest;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailOrderFulfillmentPage;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Contains general test cases for e-commerce site
 */
@Listeners(TestRerunListener.class)
public class DCommerceGeneralTests extends DCommerceBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.removeAllProducts();
        cartPage.navigateToHomePage();
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest() {
        System.out.println("Teardown");
    }

    /**
     * @TestCase CD365-2838
     * @Description Log in to e-commerce
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void loginTest() {
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        String accountName = homePage.getAccountName();

        assertEquals(accountName, "Test", "Failed to sign in to test account");
    }


}
