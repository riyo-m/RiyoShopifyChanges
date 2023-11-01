package com.vertex.quality.connectors.woocommerce.storefront.tests.orderInvoice;

import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminLoginPage;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * @author Shivam.Soni
 * CCMMER-464 WOO - tech debt reduction
 */
public class OrderInvoiceTests extends WooCommerceBaseTest {

    WooCommerceAdminLoginPage adminLoginPage;
    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;

    /**
     * CCWOO-337
     * Test case to -Create Invoice Request with New Jersey shipping address.
     */
    @Test(groups = "woo_regression")
    public void WooCommerceCreateOrderTest() {
        try {
            // Setting up physical origin or Ship From address
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_CA_LA.data[0] + " — " + WooData.US_CA_LA.data[3], WooData.US_CA_LA.data[1], WooData.US_CA_LA.data[2], WooData.US_CA_LA.data[4]);

            quitDriver();
            createChromeDriver();

            // Processing order
            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.US_NJ_ED.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(WooData.TAX_TC_WOO_337.value, checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // setting up default physical origin or Ship From
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-338
     * Test case to -Create Invoice Request from US To CAN.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooUSCNInvoiceTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.CAN_BC_VICTORIA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentageBasedTax(7), checkoutPage.getTaxFromUIAfterOrderPlace());
        assertEquals(WooData.TAX_TC_WOO_338.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-335
     * Test case to -Create Invoice Request with different shipping and billing Address.
     */
    @Test(groups = "woo_regression")
    public void wooDifferentShipBillTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_DE_WI.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(WooData.TAX_TC_WOO_US_US_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-346
     * Test case to -Create Quotation Request.
     */
    @Test(groups = "woo_regression")
    public void wooCreateSalesQuoteRequestTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(WooData.TAX_TC_WOO_US_US_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-332
     * Test case to -Create Invoice Request.
     */
    @Test(groups = "woo_regression")
    public void wooCreateInvoiceRequestTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(WooData.TAX_TC_WOO_US_US_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-330
     * WOO - Test Case - Create Sales Invoice change location
     */
    @Test(groups = "woo_regression")
    public void wooCreateSalesInvoiceChangeLocationTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        assertEquals(checkoutPage.calculatePercentBasedTaxBeforeOrderPlace(9.5), checkoutPage.getTaxFromUIBeforeOrder());

        // changing line items as per test document & processing further order
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        assertEquals(checkoutPage.calculatePercentBasedTaxBeforeOrderPlace(9.5), checkoutPage.getTaxFromUIBeforeOrder());
    }

    /**
     * CWOO-339
     * Test case to -Create Invoice Request CAN to US location.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCNUSInvoiceTest() {
        try {
            // Setting up physical origin or Ship From address
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.CAN_QB_QC.data[0] + " — " + WooData.CAN_QB_QC.data[3], WooData.CAN_QB_QC.data[1], WooData.CAN_QB_QC.data[2], WooData.CAN_QB_QC.data[4]);

            quitDriver();
            createChromeDriver();

            // Processing order
            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.US_PA_GTY.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(checkoutPage.calculatePercentageBasedTax(6), checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // setting up default physical origin or Ship From
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-341
     * Test case to -Create Invoice Request CANQC to CANBC location.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCNQCToCNBCInvoiceTest() {
        try {
            // Setting up physical origin or Ship From address
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.CAN_QB_QC.data[0] + " — " + WooData.CAN_QB_QC.data[3], WooData.CAN_QB_QC.data[1], WooData.CAN_QB_QC.data[2], WooData.CAN_QB_QC.data[4]);

            quitDriver();
            createChromeDriver();

            // Processing order
            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.CAN_BC_VICTORIA.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(WooData.TAX_TC_WOO_341.value, checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // setting up default physical origin or Ship From
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-340
     * Test case to -Create Invoice Request CANNB to CANNB location.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCNNBToCNNBInvoiceTest() {
        try {
            // Setting up physical origin or Ship From address
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.CAN_NB_GM.data[0] + " — " + WooData.CAN_NB_GM.data[3], WooData.CAN_NB_GM.data[1], WooData.CAN_NB_GM.data[2], WooData.CAN_NB_GM.data[4]);

            quitDriver();
            createChromeDriver();

            // Processing order
            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.CAN_NB_QU.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(WooData.TAX_TC_WOO_CAN_CAN_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // setting up default physical origin or Ship From
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-344
     * Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCNBCToCNQCInvoiceTest() {
        try {
            // Setting up physical origin or Ship From address
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.CAN_BC_VICTORIA.data[0] + " — " + WooData.CAN_BC_VICTORIA.data[3], WooData.CAN_BC_VICTORIA.data[1], WooData.CAN_BC_VICTORIA.data[2], WooData.CAN_BC_VICTORIA.data[4]);

            quitDriver();
            createChromeDriver();

            // Processing order
            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.CAN_QB_QC.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(WooData.TAX_TC_WOO_CAN_CAN_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // setting up default physical origin or Ship From
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-343
     * Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCNBCToCNONInvoiceTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.CAN_BC_VICTORIA.data[0] + " — " + WooData.CAN_BC_VICTORIA.data[3], WooData.CAN_BC_VICTORIA.data[1], WooData.CAN_BC_VICTORIA.data[2], WooData.CAN_BC_VICTORIA.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.CAN_ON_OT.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(WooData.TAX_TC_WOO_343.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-332
     * WOO - Test Case - End to End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = "woo_regression")
    public void wooEndToEndCreateSalesInvoiceChangeLocationDeleteItemTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        assertEquals(WooData.TAX_TC_WOO_US_US_COMMON.value, checkoutPage.getTaxFromUIBeforeOrderPlace());

        // changing line items & ship to address to re-calculate tax based on test-doc
        loadStoreFrontPage();
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_TEN_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        cartPage.goToCheckout();
        checkoutPage.addBillingsAddress(WooData.US_LA_NO.data);
        assertEquals(WooData.TAX_TC_WOO_331_1.value, checkoutPage.getTaxFromUIBeforeOrderPlace());

        // changing line items & ship to address to re-calculate tax based on test-doc
        loadStoreFrontPage();
        productsPage.addItemToCartViewCart(WooData.PRODUCTS_BELT_TWO_HOODIES.data[0]);
        cartPage.deleteSingleLineItem(WooData.PRODUCT_BEANIE.value);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_THREE_THREE.data, WooData.PRODUCTS_BELT_TWO_HOODIES.data);
        cartPage.goToCheckout();
        checkoutPage.addBillingsAddress(WooData.US_CO_CS.data);
        assertEquals(WooData.TAX_TC_WOO_331_2.value, checkoutPage.getTaxFromUIBeforeOrderPlace());
    }

    /**
     * CWOO-329
     * WOO - Test Case - Create Sale Order where the Customer is registered in O Series and the Financial System
     */
    @Test(groups = "woo_regression")
    public void wooCreateSalesOrderCustomerNotRegisteredTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_BER.data[0] + " — " + WooData.US_PA_BER.data[3], WooData.US_PA_BER.data[1], WooData.US_PA_BER.data[2], WooData.US_PA_BER.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_UT_SLC.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentageBasedTax(7.45), checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-333
     * WOO - Test Case - Create Invoice with quantity 10
     */
    @Test(groups = "woo_regression")
    public void wooCreateSalesOrderTenQuantitiesTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_TEN_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_WA_SM.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentageBasedTax(10.1), checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-334
     * WOO - Test Case -Create Sales Order with Invoice
     */
    @Test(groups = "woo_regression")
    public void wooCreateSalesOrderWithInvoiceTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUIAfterOrderPlace());
    }
}
