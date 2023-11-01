package com.vertex.quality.connectors.sitecorexc.tests.store.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import com.vertex.quality.connectors.sitecorexc.pages.*;
import com.vertex.quality.connectors.sitecorexc.tests.base.SitecoreXCBasePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * SitecoreXC Invoice related order tests - Order placed from Storefront
 *
 * @author Shivam.Soni
 */
public class SitecoreXCInvoiceTests extends SitecoreXCBasePage {

    SitecoreXCCommerceLoginPage commerceLoginPage;
    SitecoreXCCommerceAppLauncherPage appLauncherPage;
    SitecoreXCContentEditorHomePage editorHomePage;
    SitecoreXCStoreLoginPage storeLoginPage;
    SitecoreXCStoreBannerPage storeBannerPage;
    SitecoreXCStoreSearchResultPage resultPage;
    SitecoreXCCartPage cartPage;
    SitecoreXCStoreCheckoutPage checkoutPage;

    /**
     * CSCXC-382 SitecoreXC - Create Sale Order where the Customer is registered in O Series and the Financial System
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void salesOrderPAToUTTest() {
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
        editorHomePage.setWarehouseAddress(Address.Berwyn.addressLine1, Address.Berwyn.city, Address.Berwyn.state.abbreviation,
                Address.Berwyn.zip5, Address.Berwyn.country.fullName, Address.Berwyn.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.SaltLakeCity.city, Address.SaltLakeCity.country.fullName,
                Address.SaltLakeCity.state.abbreviation, Address.SaltLakeCity.addressLine1, Address.SaltLakeCity.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(7.45), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-381 SitecoreXC - Test Case - Create Sales Invoice only
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void salesOrderPAToCATest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

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
     * CSCXC-380 SitecoreXC -Create Sales Order with Invoice
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderUSPAToUSCATest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

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
     * CSCXC-379 SitecoreXC -Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderCANNBToCANNBTest() {
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
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_CA.data);
        editorHomePage.setWarehouseAddress(Address.GrandManan.addressLine1, Address.GrandManan.city, Address.GrandManan.province.abbreviation,
                Address.GrandManan.zip5, Address.GrandManan.country.fullName, Address.GrandManan.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Quispamsis.city, Address.Quispamsis.country.fullName,
                Address.Quispamsis.province.abbreviation, Address.Quispamsis.addressLine1, Address.Quispamsis.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(false, false, 15), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-369 SitecoreXC - Test Case -Create Sales Order with Invoice CANQC to CANBC different Province (GST/PST)
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void salesOrderCANQCToCANBCTest() {
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
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_CA.data);
        editorHomePage.setWarehouseAddress(Address.QuebecCity.addressLine1, Address.QuebecCity.city, Address.QuebecCity.province.abbreviation,
                Address.QuebecCity.zip5, Address.QuebecCity.country.fullName, Address.QuebecCity.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Victoria.city, Address.Victoria.country.fullName,
                Address.Victoria.province.abbreviation, Address.Victoria.addressLine1, Address.Victoria.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(false, false, 12), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-378 SitecoreXC - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderCANBCToCANONTest() {
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
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_CA.data);
        editorHomePage.setWarehouseAddress(Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.abbreviation,
                Address.Victoria.zip5, Address.Victoria.country.fullName, Address.Victoria.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Ottawa.city, Address.Ottawa.country.fullName,
                Address.Ottawa.province.abbreviation, Address.Ottawa.addressLine1, Address.Ottawa.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(false, false, 13), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-377 SitecoreXC - Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderCANBCToCANQCTest() {
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
        editorHomePage.selectLeftPanelOption(SiteCoreXCData.WAREHOUSE_NAME_CA.data);
        editorHomePage.setWarehouseAddress(Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.abbreviation,
                Address.Victoria.zip5, Address.Victoria.country.fullName, Address.Victoria.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.QuebecCity.city, Address.QuebecCity.country.fullName,
                Address.QuebecCity.province.abbreviation, Address.QuebecCity.addressLine1, Address.QuebecCity.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(false, false, 14.975), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-395 SitecoreXC - Test Case -Create Sales Order with NJ no tax on clothing, no shipping tax
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderLAToNJTest() {
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
        editorHomePage.setWarehouseAddress(Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.abbreviation,
                Address.LosAngeles.zip5, Address.LosAngeles.country.fullName, Address.LosAngeles.country.iso2code);
        editorHomePage.clickSave();
        editorHomePage.publishTheItemWithDefaultOptions(SiteCoreXCData.WAREHOUSE_NAME_US.data, SiteCoreXCData.CONTENT_EDITOR_PUBLISH_ITEM.data);

        // Login to Store website
        storeLoginPage = new SitecoreXCStoreLoginPage(driver);
        storeLoginPage.loadStoreLoginPage();
        storeLoginPage.signInToStoreWithDefaultUser();

        // Clear the cart
        storeBannerPage = new SitecoreXCStoreBannerPage(driver);
        storeBannerPage.clearTheCart();

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Edison.city, Address.Edison.country.fullName,
                Address.Edison.state.abbreviation, Address.Edison.addressLine1, Address.Edison.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(TaxRate.Edison.tax));
    }

    /**
     * CSCXC-394 SitecoreXC - Test Case -Create Sales Order different ship and bill
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderPAToDETest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Delaware.city, Address.Delaware.country.fullName,
                Address.Delaware.state.abbreviation, Address.Delaware.addressLine1, Address.Delaware.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(0));
    }

    /**
     * CSCXC-393 SitecoreXC - Test Case - Create Invoice with quantity 10
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderBulkQuantitiesTest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);

        // Go to cart & update quantities
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.updateProductQuantity(SiteCoreXCData.SMART_WIFI_BUNDLE.data, SiteCoreXCData.QUANTITY_10.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Washington.city, Address.Washington.country.fullName,
                Address.Washington.state.abbreviation, Address.Washington.addressLine1, Address.Washington.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(10.1));
    }

    /**
     * CSCXC-392 SitecoreXC - Test Case - End-to-End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void endToEndSalesOrderTest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(9.45));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data);
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);
        resultPage.addProductToTheCart(SiteCoreXCData.REFRIGERATOR_FLIP_PHONE_BUNDLE.data);

        // Go to cart & update qty
        storeBannerPage.clickViewCart();
        cartPage = new SitecoreXCCartPage(driver);
        cartPage.updateProductQuantity(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data, SiteCoreXCData.QUANTITY_10.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.ColoradoSprings.city, Address.ColoradoSprings.country.fullName,
                Address.ColoradoSprings.state.abbreviation, Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(8.2));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();

        // Go to cart, update qty & delete line item
        storeBannerPage.clickViewCart();
        cartPage.updateProductQuantity(SiteCoreXCData.SMART_WIFI_DYNAMIC_BUNDLE.data, SiteCoreXCData.QUANTITY_5.data);
        cartPage.deleteLineItem(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        cartPage.clickCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(9.45));
    }

    /**
     * CSCXC-391 SitecoreXC - Test Case - Create Sales Invoice change location
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderChangeLocationTest() {
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

        // Search for the product, add product to cart & proceed to checkout
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCart(SiteCoreXCData.SMART_WIFI_BUNDLE.data);
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage = new SitecoreXCStoreCheckoutPage(driver);
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.LosAngeles.city, Address.LosAngeles.country.fullName,
                Address.LosAngeles.state.abbreviation, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(TaxRate.LosAngeles.tax));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(9.45));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Colorado.city, Address.Colorado.country.fullName,
                Address.Colorado.state.abbreviation, Address.Colorado.addressLine1, Address.Colorado.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(5));

        // Go to store home page
        storeBannerPage.clickOnStoreLogo();
        storeBannerPage.proceedToCheckout();

        // Select delivery preference, Set the warehouse address & Select delivery method
        checkoutPage.selectDeliveryPreference(SiteCoreXCData.DELIVERY_SHIP_ITEMS.data);
        checkoutPage.enterShippingAddress(Address.Louisiana.city, Address.Louisiana.country.fullName,
                Address.Louisiana.state.abbreviation, Address.Louisiana.addressLine1, Address.Louisiana.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.getTaxFromUI(), checkoutPage.calculatePercentBasedTax(9.45));
    }
}
