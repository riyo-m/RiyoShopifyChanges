package com.vertex.quality.connectors.magentoTap.admin.tests.base;

import com.vertex.quality.connectors.magentoTap.admin.pages.*;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.tests.base.M2StorefrontBaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * currently just stores constants & utility functions that are used by many or
 * all tests
 *
 * @author alewis
 */
@Test()
public abstract class MagentoAdminBaseTest extends M2StorefrontBaseTest {
    protected String url = MagentoData.MAGENTO_QA_2_0_ADMIN.data;
    protected String magento244AdminURL = MagentoData.MAGENTO_TAP_QA_244_ADMIN_URL.data;
    protected String username = MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_USER.data;
    protected String password = MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_PASSWORD.data;
    protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
    protected String SKU = "MH12-XS-Blue";
    protected String skuOne = "VTXPCode";
    protected String skuTwo = "WSH12-32-Red";
    protected String skuThree = "ct123-1";
    protected String skuFour = "1234";
    protected String customerID = "Veronica Costello";
    protected String addressCleanseCustomerID = "MK Mayur K";
    protected String addressCleanseSKU = "24-MB02";
    protected By skuField = By.xpath("//*[contains(text(),'SKU number')]//following::input[1]");
    protected By skuField2 = By.xpath("//*[contains(text(),'SKU number')]//following::input[3]");
    protected By skuField3 = By.xpath("//*[contains(text(),'SKU number')]//following::input[5]");
    protected By skuField4 = By.xpath("//*[contains(text(),'SKU number')]//following::input[7]");
    protected By qtyField = By.xpath("//*[contains(text(),'SKU number')]//following::input[2]");
    protected By qtyField2 = By.xpath("//*[contains(text(),'SKU number')]//following::input[4]");
    protected By qtyField3 = By.xpath("//*[contains(text(),'SKU number')]//following::input[6]");
    protected By qtyField4 = By.xpath("//*[contains(text(),'SKU number')]//following::input[8]");
    M2AdminCreateNewOrderPage newOrderPage;

    /**
     * loads and signs into this configuration site
     *
     * @return a representation of the page that loads immediately after
     * successfully signing into this configuration site
     */
    protected M2AdminHomepage signInToAdminHomepage() {
        driver.get(magento244AdminURL);

        M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

        signOnPage.enterUsername(MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_USER.data);

        signOnPage.enterPassword(MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_PASSWORD.data);

        M2AdminHomepage homepage = signOnPage.login();

        return homepage;
    }

    /**
     * loads magento environment specific URL and sign-in to the site
     *
     * @return Login page
     */
    protected M2AdminHomepage signInToMagento244AdminHomepage() {
        driver.get(magento244AdminURL);

        M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

        signOnPage.enterUsername(MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_USER.data);

        signOnPage.enterPassword(MagentoData.MAGENTO_2_0_AUTOMATION_ADMIN_PASSWORD.data);

        return signOnPage.login();
    }

    /**
     * tests whether navigation can reach the configPage
     *
     * @return the configuration page
     */
    protected M2AdminConfigPage navigateToConfig() {
        M2AdminHomepage homePage = signInToAdminHomepage();

        homePage.closeMessagePopupIfPresent();

        assertTrue(homePage.navPanel.isStoresButtonVisible());

        homePage.navPanel.clickStoresButton();

        M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

        String configPageTitle = configPage.getPageTitle();

        assertTrue(configTitleText.equals(configPageTitle));

        return configPage;
    }

    /**
     * tests whether navigation can reach the configPage
     *
     * @return Magento config page
     */
    protected M2AdminConfigPage navigateToMagento244Config() {
        M2AdminHomepage homePage = signInToMagento244AdminHomepage();

        homePage.closeMessagePopupIfPresent();

        assertTrue(homePage.navPanel.isStoresButtonVisible());

        homePage.navPanel.clickStoresButton();

        M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

        String configPageTitle = configPage.getPageTitle();

        assertEquals(configPageTitle, configTitleText);

        return configPage;
    }

    /**
     * tests whether navigation can reach the OrdersPage
     *
     * @return the Orders Page
     */
    protected M2AdminOrdersPage navigateToOrders() {
        M2AdminHomepage homePage = signInToAdminHomepage();

        homePage.closeMessagePopupIfPresent();

        homePage.navPanel.clickSalesButton();

        M2AdminOrdersPage m2AdminOrdersPage = homePage.navPanel.clickOrdersButton();

        return m2AdminOrdersPage;
    }

    /**
     * tests whether navigation can reach the OrdersPage when user already signed in
     *
     * @return the Orders Page
     */
    protected M2AdminOrdersPage navigateToOrdersWithoutSignIn() {
        M2AdminHomepage homePage = new M2AdminHomepage(driver);

        homePage.navPanel.clickSalesButton();
        M2AdminOrdersPage m2AdminOrdersPage = homePage.navPanel.clickOrdersButton();
        m2AdminOrdersPage.waitForSpinnerToBeDisappeared();
        return m2AdminOrdersPage;
    }

    /**
     * tests whether navigation can reach the M2AdminSalesTaxConfigPage
     *
     * @return Tax Settings Page
     */
    protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfig() {
        M2AdminConfigPage configPage = navigateToConfig();

        configPage.clickSalesTab();
        M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

        return taxSettingsPage;
    }

    /**
     * tests whether navigation can reach the M2AdminCustomersPage
     *
     * @return the Customers Page
     */
    protected M2AdminCustomersPage navigateToAllCustomers() {
        M2AdminHomepage homePage = signInToAdminHomepage();

        homePage.navPanel.clickCustomersButton();

        M2AdminCustomersPage customersPage = homePage.navPanel.clickAllCustomersButton();

        return customersPage;
    }

    /**
     * tests whether navigation can reach the AdminCacheManagementPage
     *
     * @return the Cache Management Page
     */
    protected M2AdminCacheMgmt navigateToCacheManagement() {
        M2AdminHomepage homepage = signInToAdminHomepage();

        homepage.navPanel.clickSystemButton();

        M2AdminCacheMgmt cacheManagementPage = homepage.navPanel.clickCacheManagementButton();

        return cacheManagementPage;
    }

    /**
     * tests whether navigation can reach the M2AdminProductsPage
     *
     * @return the Products Page
     */
    protected M2AdminProductsPage navigateToProducts() {
        M2AdminHomepage homepage = signInToAdminHomepage();

        homepage.navPanel.clickCatalogButton();

        M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

        return productsPage;
    }

    /**
     * tests whether navigation can reach the M2AdminOrderCustomerPage
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage navigateToOrderCustomerPage() {
        M2AdminOrdersPage ordersPage = navigateToOrders();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        return newOrderPage;
    }

    /**
     * tests whether navigation can reach the M2AdminOrderCustomerPage
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addProductToSalesOrders() {
        M2AdminCreateNewOrderPage newOrderPage = navigateToOrderCustomerPage();
        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterQty();
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * Navigation to Order Info after order is placed
     *
     * @return Order View Info Page
     */
    protected M2AdminOrderViewInfoPage navigateOrderViewInfoPage() {
        M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();
        newOrderPage.selectCheckAsPaymentMethod();
        newOrderPage.addShippingMethod(2);
        M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButton();

        infoPage.clickTaxBlind();

        return infoPage;
    }

    /**
     * Navigation to New Invoice page after placing an order
     *
     * @return Order View Info page
     */
    protected M2AdminNewInvoicePage navigateToNewInvoicePage() {
        M2AdminOrderViewInfoPage newOrderPage = navigateOrderViewInfoPage();
        M2AdminNewInvoicePage invoicePage = newOrderPage.clickInvoiceButton();

        invoicePage.openTaxBlind();

        return invoicePage;
    }

    /**
     * Navigation to Credit Memo page after inputting an invoice
     *
     * @return Credit Order page
     */
    protected M2AdminCreditMemoPage navigateToNewMemoPage() {
        M2AdminNewInvoicePage newInvoicePage = navigateToNewInvoicePage();
        newInvoicePage.clickSubmitInvoiceButton();
        M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();
        creditMemoPage.clicksTaxBlind();

        return creditMemoPage;
    }

    /**
     * submits a credit memo from the new credit memo page
     */
    protected M2AdminOrderViewInfoPage submitCreditMemo() {
        M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();
        M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();

        return orderViewInfoPage;
    }

    /**
     * Navigates to Marketing page
     */
    protected void navigateToMarketingPage() {
        M2AdminHomepage homepage = signInToAdminHomepage();
        homepage.navPanel.clickMarketingButton();
    }

    /**
     * Navigates to Partners page
     *
     * @return the Partners Page
     */
    protected M2AdminPartnersPage navigateToPartnersPage() {
        M2AdminHomepage homepage = signInToAdminHomepage();
        M2AdminPartnersPage partnersPage = homepage.navPanel.clickPartnersButton();

        return partnersPage;
    }

    /**
     * adds product details and discount while creating sales order for customers.
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addProductDetails(String coupon) {
        M2AdminOrdersPage ordersPage = navigateToOrders();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterSpecifiedQty("5");
        newOrderPage.clickAddToOrderButton();
        newOrderPage.enterCouponCode(coupon);
        newOrderPage.clickApplyDiscountButton();
        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumberOne(skuOne);
        newOrderPage.enterSpecifiedQty("1");
        newOrderPage.clickAddToOrderButton();
        return newOrderPage;
    }

    /**
     * adds product details and discount while creating sales order for customers.
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addMultipleLineProductDetails(String coupon) {
        M2AdminOrdersPage ordersPage = navigateToOrdersWithoutSignIn();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.selectDefaultStore();
        newOrderPage.clickAddProducts();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterNewQty(SKU, "5");
        newOrderPage.clickAddToOrderButton();
        newOrderPage.enterCouponCode(coupon);
        newOrderPage.clickApplyDiscountButton();
        newOrderPage.clickAddProducts();
        newOrderPage.enterSKUNumber(skuTwo);
        newOrderPage.enterNewQty(skuTwo, "1");
        newOrderPage.clickAddToOrderButton();
        return newOrderPage;
    }

    /**
     * adds product details while creating sales order for customers.
     *
     * @param qty1
     * @param qty2
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addMultipleLineProductsDetails(String qty1, String qty2) {
        M2AdminOrdersPage ordersPage = navigateToOrders();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUDetails(SKU, skuField);
        newOrderPage.enterMultiQty(qty1, qtyField);
        newOrderPage.clickAddAnotherButton();
        newOrderPage.enterSKUDetails(skuFour, skuField2);
        newOrderPage.enterMultiQty(qty2, qtyField2);
        newOrderPage.clickAddToOrderButton();
        return newOrderPage;
    }

    /**
     * tests whether customer can add product and add address
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addProductAddressDetails(String qty) {
        M2AdminOrdersPage ordersPage = navigateToOrdersWithoutSignIn();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.selectDefaultStore();
        newOrderPage.clickAddProducts();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterNewQty(SKU, qty);
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * tests whether customer can add product and add address without navigate to orders without signIn.
     *
     * @param qty1
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addProductsAddressDetails(String qty1) {
        M2AdminOrdersPage ordersPage = navigateToOrdersWithoutSignIn();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.selectDefaultStore();
        newOrderPage.clickAddProducts();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterNewQty(SKU, qty1);
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * Sets shipping origin
     *
     * @param address address of ship from
     * @return M2AdminConfigPage
     */
    protected M2AdminConfigPage setShippingOrigin(String... address) {
        M2AdminConfigPage configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        M2AdminShippingSettingsPage shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(address);
        configPage.saveConfigurations();
        return new M2AdminConfigPage(driver);
    }

    /**
     * tests whether customer can add product and add address
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addMultipleProductDetails(String qty1, String qty2,
                                                                  String qty3, String qty4, String loginStatus) {
        M2AdminOrdersPage ordersPage;
        if (loginStatus.equals("loggedOut")) {
            ordersPage = navigateToOrders();
        } else {
            ordersPage = navigateToOrdersWithoutSignIn();
        }
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        if (!qty1.equals("0")) {
            newOrderPage.clickAddSKUButton();
            newOrderPage.enterSKUDetails(SKU, skuField);
            newOrderPage.enterMultiQty(qty1, qtyField);
        }
        if (!qty2.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(skuOne, skuField2);
            newOrderPage.enterMultiQty(qty2, qtyField2);
        }
        if (!qty3.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(skuTwo, skuField3);
            newOrderPage.enterMultiQty(qty3, qtyField3);
        }
        if (!qty4.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(skuThree, skuField4);
            newOrderPage.enterMultiQty(qty4, qtyField4);
        }
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * tests whether customer can add product and add address without sign in and products.
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addMultipleProductDetail(String qty1, String qty2,
                                                                 String qty3, String qty4, String sku1,
                                                                 String sku2, String sku3, String sku4) {
        M2AdminOrdersPage ordersPage = navigateToOrdersWithoutSignIn();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        if (!qty1.equals("0")) {
            newOrderPage.clickAddSKUButton();
            newOrderPage.enterSKUDetails(sku1, skuField);
            newOrderPage.enterMultiQty(qty1, qtyField);
        }
        if (!qty2.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(sku2, skuField2);
            newOrderPage.enterMultiQty(qty2, qtyField2);
        }
        if (!qty3.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(sku3, skuField3);
            newOrderPage.enterMultiQty(qty3, qtyField3);
        }
        if (!qty4.equals("0")) {
            newOrderPage.clickAddAnotherButton();
            newOrderPage.enterSKUDetails(sku4, skuField4);
            newOrderPage.enterMultiQty(qty4, qtyField4);
        }
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * Tests whether customer can add billing address and shipping address
     *
     * @return Order View Info Page
     */
    protected M2AdminCreateNewOrderPage addBillingShippingAddressWithShipping(Map<String, String> billingAddress,
                                                                              Map<String, String> shippingAddress) {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        newOrderPage.addBillingAddress(billingAddress);
        newOrderPage.addShippingAddress(shippingAddress);
        newOrderPage.selectPaymentMethod();
        newOrderPage.selectShippingMethod(1);
        return newOrderPage;
    }

    /**
     * tests whether customer can add commodity product
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addCommodityProductDetails(String qty) {
        M2AdminOrdersPage ordersPage = navigateToOrdersWithoutSignIn();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        newOrderPage.selectDefaultStore();
        newOrderPage.clickAddProducts();
        newOrderPage.enterSKUNumber(SKU);
        newOrderPage.enterNewQty(SKU, qty);
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * Submit the order after products and address added
     *
     * @return Order View Info Page
     */
    protected M2AdminOrderViewInfoPage submitOrderForVAT() {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButton();
        return infoPage;
    }

    /**
     * Submit the order after products and address added
     *
     * @return Order View Info Page
     */
    protected M2AdminOrderViewInfoPage submitOrder() {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.clickSubmitOrderButton();
        newOrderPage.waitForSpinnerToBeDisappeared();
        return new M2AdminOrderViewInfoPage(driver);
    }

    /**
     * fetch discount percentage amount applied for the order.
     *
     * @return discountPercent
     */
    public double getMagentoDiscountPercent(double percent) {
        newOrderPage = new M2AdminCreateNewOrderPage(driver);
        String subTotal = newOrderPage.getMagentoSubTotalValue();
        double subTotalValue = Double.parseDouble(subTotal);
        double discountPercent = subTotalValue * percent;
        return discountPercent;
    }

    /**
     * Tests whether customer can add billing address and shipping address
     *
     * @return Order View Info Page
     */
    protected M2AdminCreateNewOrderPage addBillingShippingAddress(Map<String, String> billingAddress,
                                                                  Map<String, String> shippingAddress) {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.addBillingAddress(billingAddress);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.selectDeselectSameAsAddress(false);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.addShippingAddress(true, shippingAddress);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.selectDeselectSameAsAddress(true);
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.selectPaymentMethod();
        newOrderPage.waitForSpinnerToBeDisappeared();
        newOrderPage.selectShippingMethod(0);
        newOrderPage.waitForSpinnerToBeDisappeared();
        return newOrderPage;
    }

    /**
     * Tests whether customer can add billing address and shipping address with Shipping
     * and billing address as Input text.
     *
     * @return Order View Info Page
     */
    protected M2AdminCreateNewOrderPage addBillingShippingAddresses(Map<String, String> billingAddress,
                                                                    Map<String, String> shippingAddress) {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        newOrderPage.addBillingAddresses(billingAddress);
        newOrderPage.addShippingAddresses(shippingAddress);
        newOrderPage.selectPaymentMethod();
        newOrderPage.selectShippingMethod(1);
        return newOrderPage;
    }

    /**
     * fetch discount percentage amount applied for the order.
     *
     * @return discountPercent
     */
    public double getMagentoDiscountPerItemPercent() {
        newOrderPage = new M2AdminCreateNewOrderPage(driver);
        String subTotal = newOrderPage.getMagentoSubTotalPerItemValue();
        double subTotalValue = Double.parseDouble(subTotal);
        double discountPercent = subTotalValue * 0.05;
        return discountPercent;
    }

    /**
     * selects a shipping rate for the order
     *
     * @return tax rate
     */
    public double getMagentoTaxRate() {
        newOrderPage = new M2AdminCreateNewOrderPage(driver);
        String taxPrice = newOrderPage.getMagentoTaxValue();
        double priceDoubleTax = Double.parseDouble(taxPrice);
        double taxRate = priceDoubleTax / 100;
        return taxRate;
    }

    /**
     * fetch discount percentage amount applied for the order.
     *
     * @return discountPercent
     */
    public double getMagentoDiscountRate() {
        newOrderPage = new M2AdminCreateNewOrderPage(driver);
        String discountValue = newOrderPage.getMagentoDiscountValue();
        String subTotal = newOrderPage.getMagentoSubTotalValue();
        double subTotalValue = Double.parseDouble(subTotal);
        double discount = Double.parseDouble(discountValue);
        double discountPercent = subTotalValue;
        return discountPercent;
    }

    /**
     * Creates billing address list
     *
     * @return billing address
     */
    public Map<String, String> createBillingAddress(String firstName, String lastName, String street0, String country,
                                                    String state, String city, String zip, String phone) {
        Map<String, String> billingAddress = new HashMap<>();
        billingAddress.put("firstName", firstName);
        billingAddress.put("lastName", lastName);
        billingAddress.put("street0", street0);
        billingAddress.put("country", country);
        billingAddress.put("state", state);
        billingAddress.put("city", city);
        billingAddress.put("zip", zip);
        billingAddress.put("phone", phone);
        return billingAddress;
    }

    /**
     * Creates shipping address list
     *
     * @return shipping address
     */
    public Map<String, String> createShippingAddress(String firstName, String lastName, String street0, String country,
                                                     String state, String city, String zip, String phone) {
        Map<String, String> shippingAddress = new HashMap<>();
        shippingAddress.put("firstName", firstName);
        shippingAddress.put("lastName", lastName);
        shippingAddress.put("street0", street0);
        shippingAddress.put("country", country);
        shippingAddress.put("state", state);
        shippingAddress.put("city", city);
        shippingAddress.put("zip", zip);
        shippingAddress.put("phone", phone);
        return shippingAddress;
    }

    protected M2AdminCreateNewOrderPage addProductAddressDetailsForAddressCleansing() {
        M2AdminOrdersPage ordersPage = navigateToOrders();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(addressCleanseCustomerID);
        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumber(addressCleanseSKU);
        newOrderPage.enterQty();
        newOrderPage.clickAddToOrderButton();

        return newOrderPage;
    }

    /**
     * submits a credit memo with refund
     */
    protected M2AdminOrderViewInfoPage submitCreditMemoWithRefund(String qty1, String qty2, String qty3, String adjustFee) {
        M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.addAdjustmentRefund(qty1, qty2, qty3, adjustFee);
        M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();
        return orderViewInfoPage;
    }

    /**
     * Submit the order and submit invoice
     *
     * @return Order View Info Page
     */
    protected M2AdminOrderViewInfoPage submitOrderAndInvoice() {
        M2AdminCreateNewOrderPage newOrderPage = new M2AdminCreateNewOrderPage(driver);
        M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButton();
        M2AdminNewInvoicePage invoicePage = infoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        return infoPage;
    }


    /**
     * Navigation to Credit Memo page after inputting an invoice
     *
     * @return Credit Order page
     */
    protected M2AdminCreditMemoPage navigateToNewMemoPageWithoutInvoice() {
        M2AdminNewInvoicePage newInvoicePage = new M2AdminNewInvoicePage(driver);
        newInvoicePage.waitForPageLoad();
        newInvoicePage.waitForSpinnerToBeDisappeared();
        M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();
        return creditMemoPage;
    }

    /**
     * Navigation to Credit Memo page after inputting an invoice
     *
     * @return Credit Order page
     * @author rohit-mogane
     */
    protected M2AdminCustomerInformationPage setCustomerClass(String custCodeClass) {
        M2AdminCustomersPage customerPage = navigateToAllCustomers();
        M2AdminCustomerInformationPage customerInfo = customerPage.editCustomerByName(MagentoData.CUSTOMER_FIRST_NAME.data, MagentoData.CUSTOMER_LAST_NAME.data);
        customerInfo.clickAccountInformationTab();
        customerInfo.inputVertexCustomerCode(custCodeClass);
        customerInfo.clickSaveButton();
        return customerInfo;
    }

}