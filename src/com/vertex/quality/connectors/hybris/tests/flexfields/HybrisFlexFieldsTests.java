package com.vertex.quality.connectors.hybris.tests.flexfields;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.*;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.pages.backoffice.*;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class that validates Flex-Fields functionality
 *
 * @author Shivam.Soni
 */
public class HybrisFlexFieldsTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBOVertexCustomizationPage vertexCustomization;
    HybrisBODefaultDeliveryOriginPage deliveryOriginPage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisEStorePage storeFront;
    HybrisEStoreCartPage cartPage;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    HybrisEStoreOrderConfirmationPage orderConfirmPage;

    /**
     * CHYB-276 HYB - Test Case - create Sales order with 1 Flex field and invoice
     * Validates sales Order by setting-up 1 Flexible Field.
     */
    @Test(groups = {"hybris_flexField"})
    public void createSalesOrderWithOneFlexFieldTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting Flex Code field
            vertexCustomization.setFlexCodeField(HybrisFlexFieldsValues.FIELD_VALUE_ID_1.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData(),
                    HybrisFlexFieldsValues.FIELD_VALUE_ORDER.getData(), HybrisFlexFieldsValues.FIELD_VALUE_CODE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Setting Flex Date field
            vertexCustomization.setFlexDateField(HybrisFlexFieldsValues.FIELD_VALUE_ID_1.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData(),
                    HybrisFlexFieldsValues.FIELD_VALUE_ORDER.getData(), HybrisFlexFieldsValues.FIELD_VALUE_MODIFIED_TIME.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Setting Flex Numeric field
            vertexCustomization.setFlexNumericField(HybrisFlexFieldsValues.FIELD_VALUE_ID_1.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData(),
                    HybrisFlexFieldsValues.FIELD_VALUE_ORDER.getData(), HybrisFlexFieldsValues.FIELD_VALUE_DOUBLE.getData(), HybrisFlexFieldsValues.FIELD_VALUE_SUB_TOTAL.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Setting Ship-From address
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
            VertexLogger.log("Test failed due to exception, Kindly check logs for exception / error detail.");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Remove all selected Flex field(s)
            vertexCustomization.removeAllSelectedFlexFields();
            electronicsStorePage.saveElectronicsStoreConfiguration();
        }
    }
}
