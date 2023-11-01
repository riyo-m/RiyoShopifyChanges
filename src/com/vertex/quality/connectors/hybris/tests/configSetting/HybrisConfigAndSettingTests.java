package com.vertex.quality.connectors.hybris.tests.configSetting;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.*;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.pages.backoffice.*;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class that validates sales order by doing configuration level settings on / off
 *
 * @author Shivam.Soni
 */
public class HybrisConfigAndSettingTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBODefaultDeliveryOriginPage deliveryOriginPage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisBOCronJobsPage cronJobsPage;
    HybrisEStorePage storeFront;
    HybrisEStoreCartPage cartPage;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    HybrisEStoreOrderConfirmationPage orderConfirmPage;
    String orderNumber;

    /**
     * CHYB-271 - HYB -Create Sales Order with Invoice OFF
     */
    @Test(groups = {"hybris_smoke", "hybris_regression"})
    public void salesOrderWithInvoiceTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Disabling invoice
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ENABLE_TAX_INVOICING.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // navigate Default Delivery Origin page and Set new Origin Address
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
            deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
            deliveryOriginPage.doubleClickOnOriginAddress();
            deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.GETTYSBURG.stName, HybrisBOOriginAddress.GETTYSBURG.stNumber, HybrisBOOriginAddress.GETTYSBURG.postalCode,
                    HybrisBOOriginAddress.GETTYSBURG.town, HybrisBOOriginAddress.GETTYSBURG.country);

            // save Vertex Configuration & Logout from Back office
            vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
            vertexConfigPage.saveVertexConfiguration();
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Get Cart Quantity and Remove All Items from Cart
            if (storeFront.getCartQuantity() > 0) {
                cartPage = storeFront.navigateToCart();
                cartPage.removeItemsFromCart();
            }

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.LosAngeles.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
            checkoutPage.clickDeliveryAddressNext();
            checkoutPage.clickDeliveryMethodNext();

            // Fill Payment card details
            checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

            // check use delivery address checkbox and proceed to checkout
            checkoutPage.enableUseDeliveryAddress();
            checkoutPage.clickpaymentBillingAddressNext();

            // Enable Terms And Conditions and Place Order
            checkoutPage.enableTermsConditions();
            orderConfirmPage = checkoutPage.placeOrder();

            // Assertion for percent based tax amount
            assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmPage.getTax());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Enabling invoice
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ENABLE_TAX_INVOICING.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();
        }
    }

    /**
     * CHYB-272 - HYB - Test Case - Validate the log level - OFF
     */
    @Test(groups = {"hybris_config"})
    public void salesOrderWithLoggingEnableDisableTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to vertex configuration
            vertexConfigPage = boHomePage.navigateToConfigurationPage(HybrisBONavTreeOptions.VERTEX.getMenuName(), HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName());

            // Disable Messaging log in Vertex Administration Configuration
            vertexConfigPage.selectVertexConfigurationRow(0);
            vertexConfigPage.navigateToAdministrationTab();
            vertexConfigPage.setMessagingLog(HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());

            // save Vertex Configuration
            vertexConfigPage.saveVertexConfiguration();

            // navigate to CronJobs Page
            cronJobsPage = boHomePage.navigateToCronJobsPage(HybrisBONavTreeOptions.SYSTEM.getMenuName(), HybrisBONavTreeOptions.BACKGROUND_PROCESSES.getMenuName(), HybrisBONavTreeOptions.CRON_JOBS.getMenuName());

            // search for Vertex Cronjob code
            cronJobsPage.searchVertexCronJob(HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode());
            cronJobsPage.selectVertexCronJob();

            // run CronJob
            cronJobsPage.runVertexCronJob();
            cronJobsPage.refreshCronJob();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Product - PowerShot to Cart
            storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.LosAngeles.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
            checkoutPage.clickDeliveryAddressNext();
            checkoutPage.clickDeliveryMethodNext();

            // Fill Payment card details
            checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

            // check use delivery address checkbox and proceed to checkout
            checkoutPage.enableUseDeliveryAddress();
            checkoutPage.clickpaymentBillingAddressNext();

            // Enable Terms And Conditions and Place Order
            checkoutPage.enableTermsConditions();
            orderConfirmPage = checkoutPage.placeOrder();

            // Get order Number & verify it should not be blank.
            orderNumber = orderConfirmPage.getOrderNumber();
            assertNotNull(orderNumber, "Order Number not available '/' Order Number is Blank");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to vertex configuration
            vertexConfigPage = boHomePage.navigateToConfigurationPage(HybrisBONavTreeOptions.VERTEX.getMenuName(), HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName());

            // Disable Messaging log in Vertex Administration Configuration
            vertexConfigPage.selectVertexConfigurationRow(0);
            vertexConfigPage.navigateToAdministrationTab();
            vertexConfigPage.setMessagingLog(HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());

            // save Vertex Configuration
            vertexConfigPage.saveVertexConfiguration();

            // navigate to CronJobs Page
            cronJobsPage = boHomePage.navigateToCronJobsPage(HybrisBONavTreeOptions.SYSTEM.getMenuName(), HybrisBONavTreeOptions.BACKGROUND_PROCESSES.getMenuName(), HybrisBONavTreeOptions.CRON_JOBS.getMenuName());

            // search for Vertex Cronjob code
            cronJobsPage.searchVertexCronJob(HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode());
            cronJobsPage.selectVertexCronJob();

            // run CronJob
            cronJobsPage.runVertexCronJob();
            cronJobsPage.refreshCronJob();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }
}
