package com.vertex.quality.connectors.sitecorexc.tests.store.basicOrder;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import com.vertex.quality.connectors.sitecorexc.pages.*;
import com.vertex.quality.connectors.sitecorexc.tests.base.SitecoreXCBasePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * SitecoreXC Basic sales order Invoice related order tests - Order placed from Storefront
 *
 * @author Shivam.Soni
 */
public class SitecoreXCBasicInvoiceTests extends SitecoreXCBasePage {

    SitecoreXCCommerceLoginPage commerceLoginPage;
    SitecoreXCCommerceAppLauncherPage appLauncherPage;
    SitecoreXCContentEditorHomePage editorHomePage;
    SitecoreXCStoreLoginPage storeLoginPage;
    SitecoreXCStoreBannerPage storeBannerPage;
    SitecoreXCStoreSearchResultPage resultPage;
    SitecoreXCStoreCheckoutPage checkoutPage;

    /**
     * CSCXC-376 SitecoreXC - Test Case -Create Sales Order with no State Tax
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderCOToDETest() {
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
        assertEquals(checkoutPage.calculatePercentBasedTax(0), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-375 SitecoreXC - Test Case -Create Sales Order with no State Tax, locally administered
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void salesOrderPAToAKTest() {
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
        checkoutPage.enterShippingAddress(Address.Juneau.city, Address.Juneau.country.fullName,
                Address.Juneau.state.abbreviation, Address.Juneau.addressLine1, Address.Juneau.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.Juneau.tax), checkoutPage.getTaxFromUI());
    }

    /**
     * CSCXC-372 SitecoreXC - Test Case -Create Sales Order with Modified Origin State
     */
    @Test(groups = {"sitecoreXC_smoke", "sitecoreXC_regression"})
    public void salesOrderCAToCATest() {
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
        checkoutPage.enterShippingAddress(Address.UniversalCity.city, Address.UniversalCity.country.fullName,
                Address.UniversalCity.state.abbreviation, Address.UniversalCity.addressLine1, Address.UniversalCity.zip5);
        checkoutPage.selectShippingOption(SiteCoreXCData.GROUND_SHIP_OPTION.data);

        // Continue to billing & validate taxes
        checkoutPage.clickOnContinueToBilling();
        assertEquals(checkoutPage.calculatePercentBasedTax(TaxRate.UniversalCity.tax), checkoutPage.getTaxFromUI());
    }
}
