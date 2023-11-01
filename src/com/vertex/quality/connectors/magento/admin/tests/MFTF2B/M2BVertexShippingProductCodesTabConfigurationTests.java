package com.vertex.quality.connectors.magento.admin.tests.MFTF2B;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for verifying various fields in magento admin Vertex Shipping Product Codes tab
 * configuration settings page
 *
 * @author alewis
 */

public class M2BVertexShippingProductCodesTabConfigurationTests extends MagentoAdminBaseTest {
    protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
    protected String username = MagentoData.ADMIN_USERNAME.data;
    protected String password = MagentoData.ADMIN_PASSWORD.data;
    protected String loggedInHeaderText = "Dashboard";
    protected String vertexApiStatus = "VALID";
    protected String validCalculationApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    protected String validAddressApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
    protected String validTrustedId = "1405705348746247";
    protected String validCompanyCode = "Store4GA";
    protected String validLocationCode = "";
    protected String validCompanyStreetAddressValue = "2301 Renaissance Blvd";
    protected String validCompanyStreetAddressTwoValue = "";
    protected String validCompanyCityValue = "King of Prussia";
    protected String validCompanyCountryValue = "United States";
    protected String validCompanyStateValue = "Pennsylvania";
    protected String validCompanyPostalCode = "19406";
    protected String validGlobalDeliveryTermText = "SUP - Supplier Ships";

    /**
     * tests change the value of Free Shipping Enabled Field in Shipping Methods Configuration Page
     * and verifies the free shipping field in Shipping Products Codes Tab in Tax Configuration Page
     * CMAGM2-173
     *
     * @author alewis
     */
    @Test()
    public void vertexProductShippingCodeTest() {
        M2AdminConfigPage configPage = new M2AdminConfigPage(driver);
        M2AdminSalesTaxConfigPage configVertexSetting = new M2AdminSalesTaxConfigPage(driver);

        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesShippingMethodsConfigPage ShippingMethodSetting = navigateToSalesShippingMethodsConfig();

        //Changing the value of free Shipping Enabled Field
        ShippingMethodSetting.changeFreeShippingEnabledField("No");

        //Click on save configuration
        ShippingMethodSetting.saveConfig();

        //Verifying free shipping field in Shipping Products Codes Tab in Tax Configuration Page
        configPage.clickTaxTab();
        assertFalse(configVertexSetting.verifyFreeShippingField());

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        configPage.clickTaxTab();
        validatingConfigFieldsAfterReset();
    }

    /**
     * validating the vertex fields in the configuration settings page
     *
     * @author alewis
     */
    protected void validatingConfigFieldsAfterReset() {
        M2AdminSalesTaxConfigPage configVertexSetting = new M2AdminSalesTaxConfigPage(driver);
        configVertexSetting.clickConnectionSettingsTab();
        configVertexSetting.clickIntegrationSettingsTab();
        configVertexSetting.clickDeveloperSupportInformationTab();
        assertEquals(configVertexSetting.getSelectedTextForVertexTaxLink(), "Enable");
        assertEquals(configVertexSetting.getSelectedTextForVertexTaxCalculation(), "Enable");
        assertEquals(configVertexSetting.getVertexAPIStatus(), vertexApiStatus);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.calculationApiUrl), validCalculationApiUrl);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.addressApiUrl), validAddressApiUrl);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.trustedId), validTrustedId);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyCodeField), validCompanyCode);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.locationCodeField), validLocationCode);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyStressAddressField), validCompanyStreetAddressValue);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyStressAddressTwoField), validCompanyStreetAddressTwoValue);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyCityField), validCompanyCityValue);
        assertEquals(configVertexSetting.getSelectedTextForCompanyCountry(), validCompanyCountryValue);
        assertEquals(configVertexSetting.getSelectedTextForCompanyState(), validCompanyStateValue);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyPostalCodeField), validCompanyPostalCode);
        assertEquals(configVertexSetting.getSelectedTextForGlobalDeliveryTerm(), validGlobalDeliveryTermText);
        assertEquals(configVertexSetting.getSelectedTextVertexRequestLoggingField(), "Enable");
        assertEquals(configVertexSetting.getSelectedTextRotationActionField(), "Delete");
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.logEntryLifeTimeField), "7");
        assertEquals(configVertexSetting.getSelectedTextRotationFrequencyField(), "Weekly");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeHourField(), "00");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeMinuteField(), "00");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeSecondField(), "00");
        assertTrue(configVertexSetting.verifyFreeShippingField());
        assertTrue(configVertexSetting.verifyRotationActionField());
        assertTrue(configVertexSetting.verifyLogEntryLifeTimeField());
        assertTrue(configVertexSetting.verifyRotationFrequencyField());
        assertTrue(configVertexSetting.verifyRotationTimeField());
        assertTrue(configVertexSetting.verifyVertexCompyInfoTab());
        assertTrue(configVertexSetting.verifyVertexDeliveryTermsTab());
        assertTrue(configVertexSetting.verifyVertexShippingProductCodesTab());
        assertTrue(configVertexSetting.verifyVertexLoggingTab());
        assertTrue(configVertexSetting.verifyCheckBoxDisplayProduct());
        assertFalse(configVertexSetting.verifyWhenOrderStatusField());
        assertFalse(configVertexSetting.verifyDeliveryTermItems());
    }

    /**
     * tests whether navigation can reach the M2AdminSalesShippingMethodsConfigPage
     *
     * @return Shipping Methods Settings Page
     */
    protected M2AdminSalesShippingMethodsConfigPage navigateToSalesShippingMethodsConfig() {
        M2AdminConfigPage configPage = navigateToConfig();

        configPage.clickSalesTab();

        M2AdminSalesShippingMethodsConfigPage ShippingMethodSettingsPage = configPage.clickShippingMethodTab();

        return ShippingMethodSettingsPage;
    }

    /**
     * tests whether navigation can reach the configPage
     *
     * @return the configuration page
     */
    protected M2AdminConfigPage navigateToConfig() {
        M2AdminHomepage homePage = signInToAdminHomepage();

        assertTrue(homePage.navPanel.isStoresButtonVisible());

        homePage.navPanel.clickStoresButton();

        M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

        String configPageTitle = configPage.getPageTitle();

        assertEquals(configTitleText, configPageTitle);

        return configPage;
    }

    /**
     * loads and signs into this configuration site
     *
     * @return a representation of the page that loads immediately after
     * successfully signing into this configuration site
     */
    protected M2AdminHomepage signInToAdminHomepage() {
        driver.get(url);

        M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

        boolean isUsernameFieldPresent = signOnPage.isUsernameFieldPresent();
        assertTrue(isUsernameFieldPresent);

        signOnPage.enterUsername(username);

        boolean isPasswordFieldPresent = signOnPage.isPasswordFieldPresent();
        assertTrue(isPasswordFieldPresent);

        signOnPage.enterPassword(password);

        boolean isLoginButtonPresent = signOnPage.isLoginButtonPresent();
        assertTrue(isLoginButtonPresent);

        M2AdminHomepage homepage = signOnPage.login();

        String homePageBannerText = homepage.checkJustLoggedIn();

        assertEquals(loggedInHeaderText, homePageBannerText);

        return homepage;
    }
}