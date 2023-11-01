package com.vertex.quality.connectors.bigcommerce.ui.tests;

import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.enums.BigCommerceAdminData;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminHomePage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreCheckoutPage;
import com.vertex.quality.connectors.bigcommerce.ui.pojos.BigCommerceUiAddressPojo;
import com.vertex.quality.connectors.bigcommerce.ui.tests.base.BigCommerceUiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CBC-760
 * CBC- Create UI automation for Canada.
 *
 * @author vivek.kumar
 */
public class BigCommerceUiCanadaEstimateTests extends BigCommerceUiBaseTest {
    /**
     * this checks out a single item and verifies that the correct tax is estimated for the order before the order
     * would be submitted
     */
    @Test(groups = {"bigCommerce_ui"})
    public void canadaEstimateTest() {

        BigCommerceUiAddressPojo shipAddress = new BigCommerceUiAddressPojo(BigCommerceAdminData.SHIPPING_FIRST_NAME.data, BigCommerceAdminData.SHIPPING_LAST_NAME.data,
                BigCommerceTestDataAddress.US_AZ_ADDRESS_2);
        BigCommerceUiAddressPojo billAddress = new BigCommerceUiAddressPojo(BigCommerceAdminData.BILLING_FIRST_NAME.data, BigCommerceAdminData.BILLING_LAST_NAME.data,
                BigCommerceTestDataAddress.US_CA_ADDRESS_2);

        BigCommerceAdminHomePage adminHomePage = signInToHomePage(testStartPage);


        BigCommerceStoreCheckoutPage checkoutPage = checkoutSingleItem(adminHomePage, BigCommerceAdminData.DEFAULT_PRODUCT_NAME.data);

        fillCheckoutPage(checkoutPage, BigCommerceAdminData.CUSTOMER_EMAIL.data, shipAddress, billAddress);
        String taxEstimate = checkoutPage.retrieveOrderTax();
        assertEquals(taxEstimate, BigCommerceAdminData.EXPECTED_TAX_ESTIMATE.data);
    }
}