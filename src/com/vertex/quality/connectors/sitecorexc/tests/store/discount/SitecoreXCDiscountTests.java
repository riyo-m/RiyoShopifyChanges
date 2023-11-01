package com.vertex.quality.connectors.sitecorexc.tests.store.discount;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import com.vertex.quality.connectors.sitecorexc.pages.*;
import com.vertex.quality.connectors.sitecorexc.tests.base.SitecoreXCBasePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * SitecoreXC Discount related order tests - Order placed from Storefront
 *
 * @author Shivam.Soni
 */
public class SitecoreXCDiscountTests extends SitecoreXCBasePage {

    SitecoreXCCommerceLoginPage commerceLoginPage;
    SitecoreXCCommerceAppLauncherPage appLauncherPage;
    SitecoreXCContentEditorHomePage editorHomePage;
    SitecoreXCStoreLoginPage storeLoginPage;
    SitecoreXCStoreBannerPage storeBannerPage;
    SitecoreXCStoreSearchResultPage resultPage;
    SitecoreXCCartPage cartPage;
    SitecoreXCStoreCheckoutPage checkoutPage;

    /**
     * CSCXC-365 SitecoreXC - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Order Amount
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void amountDiscountedSalesOrderPAToCATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.FIVE_DOLLAR_ORDER.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-388 SitecoreXC - Test Case -Create Sales Order with multiple Discounts - Discount Shipping Amount, Discount Order Percent, Discount Line Amount
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void percentDiscountedSalesOrderPAToCATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);

        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);
        resultPage.addProductToTheCart(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.TEN_PERCENT_ORDER.data);
        cartPage.addDiscountCode(SiteCoreXCData.TEN_DOLLAR_ITEM.data);
        cartPage.addDiscountCode(SiteCoreXCData.FREE_SHIPPING.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-387 SitecoreXC - Test Case -Create Sales Order with Discounts and Invoice
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void percentDiscountedSalesOrderPAToWATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.TEN_PERCENT_ORDER.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Washington.city, Address.Washington.country.fullName,
                Address.Washington.state.abbreviation, Address.Washington.addressLine1, Address.Washington.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(10.1), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-386 SitecoreXC - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void shippingDiscountedSalesOrderPAToCATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.FREE_SHIPPING.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-385 SitecoreXC - Test Case -Create Sales Order with Discount - Multi line order with Discount line Amount
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void amountDiscountedItemPAToCATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.TEN_DOLLAR_ITEM.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-384 SitecoreXC - Test Case -Create Sales Order with Discount - Multi line order with Discount Line Percentage
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void percentDiscountedItemPAToCATest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.abbreviation,
                Address.Gettysburg.zip5, Address.Gettysburg.country.fullName, Address.Gettysburg.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.FIVE_PERCENT_ITEM.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-383 SitecoreXC - Test Case -Create Sales Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void multiplePercentDiscountedSalesOrderTest() {
        // Login to SitecoreXC commerce site
        commerceLoginPage = new SitecoreXCCommerceLoginPage(driver);
        commerceLoginPage.loadCommerceLoginPage();
        commerceLoginPage.logInToCommerceWithAdminUser();

        // Select Content Editor app
        appLauncherPage = new SitecoreXCCommerceAppLauncherPage(driver);
        appLauncherPage.selectAppFromAppLauncher(SiteCoreXCData.COMMERCE_APP_CONTENT_EDITOR.data);

        // Expand node tree to set warehouse address
        editorHomePage = new SitecoreXCContentEditorHomePage(driver);
        editorHomePage.selectHeaderNavigationOption(SiteCoreXCData.CONTENT_EDITOR_HOME.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_sitecore.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_CONTENT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SITECORE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_STOREFRONT.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_SETTINGS.data);
        editorHomePage.expandNodeFromLeftPanel(false, SiteCoreXCData.LEFT_PANEL_COMMERCE.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_VERTEX.data);
        editorHomePage.expandNodeFromLeftPanel(SiteCoreXCData.LEFT_PANEL_WAREHOUSE_CONFIG.data);

        // Set warehouse address, save changes & publish the changes
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_US.data);
        editorHomePage.setWarehouseAddress(Address.Colorado.addressLine1, Address.Colorado.city, Address.Colorado.state.abbreviation,
                Address.Colorado.zip5, Address.Colorado.country.fullName, Address.Colorado.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        // View cart, add discount code & checkout
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.TEN_PERCENT_ORDER.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(9.45), checkoutPage.getTaxFromUI());

        // Again goto cart, remove previous discount & add new discount code & checkout
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.clickViewCart();
        cartPage.removeAllDiscountCodes();
        cartPage.addDiscountCode(SiteCoreXCData.FIVE_PERCENT_ORDER.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(9.45), checkoutPage.getTaxFromUI());
    }
}
