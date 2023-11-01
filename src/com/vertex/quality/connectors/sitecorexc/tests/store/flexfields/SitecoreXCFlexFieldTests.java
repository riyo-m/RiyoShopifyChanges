package com.vertex.quality.connectors.sitecorexc.tests.store.flexfields;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import com.vertex.quality.connectors.sitecorexc.pages.*;
import com.vertex.quality.connectors.sitecorexc.tests.base.SitecoreXCBasePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * SitecoreXC flex-field tests
 *
 * @author Shivam.Soni
 */
public class SitecoreXCFlexFieldTests extends SitecoreXCBasePage {

    SitecoreXCCommerceLoginPage commerceLoginPage;
    SitecoreXCCommerceAppLauncherPage appLauncherPage;
    SitecoreXCContentEditorHomePage editorHomePage;
    SitecoreXCStoreLoginPage storeLoginPage;
    SitecoreXCStoreBannerPage storeBannerPage;
    SitecoreXCStoreSearchResultPage resultPage;
    SitecoreXCStoreCheckoutPage checkoutPage;

    /**
     * CSCXC-389 SitecoreXC - create Sales order with 1 Flex field and invoice
     */
    @Test(groups = {"sitecoreXC_regression"})
    public void salesOrderFlexFieldTest() {
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
        storeBannerPage.searchFromEntireStoreSearch(SiteCoreXCData.FLEX_FIELD_PRODUCT.data);
        resultPage = new SitecoreXCStoreSearchResultPage(driver);
        resultPage.addProductToTheCartByProductID(SiteCoreXCData.FLEX_FIELD_PRODUCT.data);
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
    }
}