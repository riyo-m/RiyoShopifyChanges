package com.vertex.quality.connectors.magento.admin.tests.MFTF2B;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSignOnPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for verifying various fields in magento admin vertex delivery terms tab
 * configuration settings page
 *
 * @author alewis
 */

public class M2BVertexDeliveryTermsTabConfigurationTests extends MagentoAdminBaseTest {
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
    protected String globalDeliveryTermText = "FOB - Free Onboard Vessel";
    protected String countryDeliveryTerm = "--Please Select--";
    protected String subDeliveryTermValue = "FOB - Free Onboard Vessel";
    protected String expectedCountryDeliveryTermErrorMessage = "This is a required field.";

    /**
     * tests change the value of global delivery term
     * CMAGM2-167
     *
     * @author alewis
     */
    @Test()
    public void globalDeliveryTermTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field
        configVertexSetting.changeGlobalDeliveryTermField(globalDeliveryTermText);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration message and selected dropdown value appears in global delivery term field.
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextForGlobalDeliveryTerm(), globalDeliveryTermText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of delivery term override
     * CMAGM2-168
     *
     * @author alewis
     */
    @Test()
    public void deliveryTermOverrideTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field
        configVertexSetting.changeDeliveryTermOverrideField("--Please Select--", "FOB - Free Onboard Vessel", 1);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying country delivery term subfield in delivery term override field
        String actualErrorMessage = configVertexSetting.verifyCountryDeliveryTermErrorMessage();
        assertEquals(expectedCountryDeliveryTermErrorMessage, actualErrorMessage);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of delivery term override to italy,france and spain
     * CMAGM2-169
     *
     * @author alewis
     */
    @Test()
    public void deliveryTermOverrideITFranceSpainTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field
        configVertexSetting.changeDeliveryTermOverrideField("Italy", "FOB - Free Onboard Vessel", 1);
        configVertexSetting.changeDeliveryTermOverrideField("France", "CUS - Customer Ships", 2);
        configVertexSetting.changeDeliveryTermOverrideField("Spain", "DDP - Delivery Duty Paid", 3);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying country delivery term subfield in delivery term override field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(1), "Italy");
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(2), "France");
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(3), "Spain");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(1), "FOB - Free Onboard Vessel");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(2), "CUS - Customer Ships");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(3), "DDP - Delivery Duty Paid");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of delivery term override to Afghanistan
     * CMAGM2-170
     *
     * @author alewis
     */
    @Test()
    public void deliveryTermOverrideAfghanistanTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field
        configVertexSetting.changeDeliveryTermOverrideField("Afghanistan", "FOB - Free Onboard Vessel", 1);
        configVertexSetting.changeDeliveryTermOverrideField("Afghanistan", "CUS - Customer Ships", 2);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying country delivery term subfield in delivery term override field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(1), "Afghanistan");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(1), "CUS - Customer Ships");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of delivery term override to Malta
     * CMAGM2-171
     *
     * @author alewis
     */
    @Test()
    public void deliveryTermOverrideMaltaTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field
        configVertexSetting.changeDeliveryTermOverrideField("Malta", "SUP - Supplier Ships", 1);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying country delivery term subfield in delivery term override field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(1), "Malta");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(1), "SUP - Supplier Ships");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of global delivery term and delivery term override
     * CMAGM2-172
     *
     * @author alewis
     */
    @Test()
    public void globalDeliveryTermOverrideTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of global delivery term field and delivery term override field
        configVertexSetting.changeGlobalDeliveryTermField("DDP - Delivery Duty Paid");
        configVertexSetting.changeDeliveryTermOverrideField("Canada", "SUP - Supplier Ships", 1);
        configVertexSetting.changeDeliveryTermOverrideField("Italy", "CUS - Customer Ships", 2);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying global delivery term and country delivery term subfield in delivery term override field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextForGlobalDeliveryTerm(), "DDP - Delivery Duty Paid");
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(1), "Canada");
        assertEquals(configVertexSetting.getSelectedTextCountryDeliveryTerm(2), "Italy");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(1), "SUP - Supplier Ships");
        assertEquals(configVertexSetting.getSelectedTextDeliveryTerm(2), "CUS - Customer Ships");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
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
        assertTrue(configVertexSetting.verifyVertexCompyInfoTab());
        assertTrue(configVertexSetting.verifyVertexDeliveryTermsTab());
        assertTrue(configVertexSetting.verifyVertexShippingProductCodesTab());
        assertTrue(configVertexSetting.verifyVertexLoggingTab());
        assertFalse(configVertexSetting.verifyWhenOrderStatusField());
        assertFalse(configVertexSetting.verifyDeliveryTermItems());
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

        taxSettingsPage.clickConnectionSettingsTab();

        taxSettingsPage.clickIntegrationSettingsTab();

        return taxSettingsPage;
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