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
 * Test cases for verifying various fields in magento admin company information tab
 * configuration settings page
 *
 * @author alewis
 */

public class M2BVertexCompanyInformationTabConfigurationTests extends MagentoAdminBaseTest {
    protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
    protected String username = MagentoData.ADMIN_USERNAME.data;
    protected String password = MagentoData.ADMIN_PASSWORD.data;
    protected String loggedInHeaderText = "Dashboard";
    protected String companyCodeText = "Store5NYTheraininSpainfallsmostlyontheplainsThe raininSpainfallsmostly";
    protected String expectedCompanyCodeErrorText = "Please enter less or equal than 40 symbols.";
    protected String expectedLocationCodeErrorText = "Please enter less or equal than 40 symbols.";
    protected String expectedCompanyStreetAddressErrorText = "Please enter less or equal than 100 symbols.";
    protected String expectedCompanyCityErrorText = "Please enter less or equal than 60 symbols.";
    protected String expectedCompanyPostalCodeErrorText = "Please enter less or equal than 15 symbols.";
    protected String locationCodeText = "KOPTheraininSpainfallsmostlyon theplainsTheraininSpainfallsmostly";
    protected String expectedVertexApiStatus5 = "ADDRESS INCOMPLETE, MISSING: COMPANY STREET";
    protected String expectedVertexApiStatus6 = "ADDRESS INCOMPLETE, MISSING: ONE OF COMPANY CITY OR POSTCODE";
    protected String expectedVertexApiStatus7 = "ADDRESS INCOMPLETE, MISSING: COMPANY COUNTRY";
    protected String expectedVertexApiStatus8 = "ADDRESS INCOMPLETE, MISSING: ONE OF COMPANY CITY OR POSTCODE";
    protected String vertexApiStatus = "VALID";
    protected String validTrustedId = "1405705348746247";
    protected String validCalculationApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    protected String validAddressApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
    protected String validCompanyCode = "Store4GA";
    protected String validLocationCode = "";
    protected String validCompanyStreetAddressValue = "2301 Renaissance Blvd";
    protected String validCompanyStreetAddressTwoValue = "";
    protected String validCompanyCityValue = "King of Prussia";
    protected String validcompanyCountryValue = "United States";
    protected String companyCountryValue = "--Please Select--";
    protected String companyStateValue = "Pennsylvania";
    protected String validCompanyPostalCode = "19406";
    protected String companyStateValue2 = "Please select a region, state or province.";
    protected String companyStateRequiredFieldText = "This is a required field.";
    protected String invalidCompanyPostalCode = "KOPTheraininSpainfallsmostlyontheplainsTherainin Spainfallsmostly";
    protected String invalidCompanyCityValue = "Philadelphia and add KOPTheraininSpainfallsmostlyontheplainsTherainin SpainfallsmostlyKOPTheraininSpainfallsmostlyontheplainsTheraininSpainfallsmostly";
    protected String invalidCompanyStreetAddressValue = "KOPTheraininSpainfallsmostlyontheplainsTherainin SpainfallsmostlyKOPTheraininSpainfallsmostlyontheplainsTheraininSpainfallsmostly";
    protected String invalidCompanyStreetAddressTwoValue = "KOPTheraininSpainfallsmostlyontheplainsTheraininSpain fallsmostlyKOPTheraininSpainfallsmostlyontheplainsTheraininSpainfallsmostly";

    /**
     * tests change the value of company code field and verifies the error message
     * CMAGM2-156
     *
     * @author alewis
     */
    @Test()
    public void companyCodeErrorMessageTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company code field
        configVertexSetting.changeCompanyCodeField(companyCodeText);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for company code field.
        String actualCompanyCodeErrorText = configVertexSetting.verifyCompanyCodeErrorMessage();
        assertEquals(expectedCompanyCodeErrorText, actualCompanyCodeErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of location code field and verifies the error message
     * CMAGM2-157
     *
     * @author alewis
     */
    @Test()
    public void locationCodeErrorMessageTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of location code field
        configVertexSetting.changeLocationCodeField(locationCodeText);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for location code field.
        String actualLocationCodeErrorText = configVertexSetting.verifyLocationCodeErrorMessage();
        assertEquals(expectedLocationCodeErrorText, actualLocationCodeErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Street Address field and verifies the api status
     * CMAGM2-158
     *
     * @author alewis
     */
    @Test()
    public void companyStreetAddressErrorMessageTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of Company Street Address field
        configVertexSetting.changeCompanyStreetAddressOneField("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying api status for company street address
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus5, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Street Address field and verifies the error message
     * CMAGM2-159
     *
     * @author alewis
     */
    @Test()
    public void invalidCompanyStreetAddressTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of Company Street Address field
        configVertexSetting.changeCompanyStreetAddressOneField(invalidCompanyStreetAddressValue);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for company street address field.
        String actualCompanyStreetAddressErrorText = configVertexSetting.verifyCompanyStreetAddressErrorMessage();
        assertEquals(expectedCompanyStreetAddressErrorText, actualCompanyStreetAddressErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Street Address Two field and verifies the error message
     * CMAGM2-160
     *
     * @author alewis
     */
    @Test()
    public void invalidCompanyStreetAddressTwoTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of Company Street Address Two field
        configVertexSetting.changeCompanyStreetAddressTwoField(invalidCompanyStreetAddressTwoValue);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for company street address two field.
        String actualCompanyStreetAddressTwoErrorText = configVertexSetting.verifyCompanyStreetAddressTwoErrorMessage();
        assertEquals(expectedCompanyStreetAddressErrorText, actualCompanyStreetAddressTwoErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of company city field and verifies api status
     * CMAGM2-161
     *
     * @author alewis
     */
    @Test()
    public void companyCityTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company city field
        configVertexSetting.changeCompanyCityField("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying api status for company city field when value is empty
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus6, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of company city field and verifies the error message
     * CMAGM2-162
     *
     * @author alewis
     */
    @Test()
    public void companyCityErrorMessageTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company city field
        configVertexSetting.changeCompanyCityField(invalidCompanyCityValue);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for company city field
        String actualCompanyCityErrorText = configVertexSetting.verifyCompanyCityErrorMessage();
        assertEquals(expectedCompanyCityErrorText, actualCompanyCityErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Country field and verifies the error message
     * CMAGM2-163
     *
     * @author alewis
     */
    @Test()
    public void companyCountryTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company country field
        configVertexSetting.changeCompanyCountryField(companyCountryValue);
        configVertexSetting.changeCompanyStateTextField(companyStateValue);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying api status for company country field when value is empty
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus7, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company State field and verifies required field
     * CMAGM2-164
     *
     * @author alewis
     */
    @Test()
    public void companyStateTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company State field
        configVertexSetting.changeCompanyStateSelectField(companyStateValue2);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying required field message of company state field
        String actualCompanyStateErrorText = configVertexSetting.verifyCompanyStateRequiredField();
        assertEquals(companyStateRequiredFieldText, actualCompanyStateErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Postal Code field and verifies api Status
     * CMAGM2-165
     *
     * @author alewis
     */
    @Test()
    public void companyPostalCodeTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company postal code field
        configVertexSetting.changeCompanyPostalCodeField("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying api status for company postal code field when value is empty
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus8, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Company Postal Code field and verifies error message
     * CMAGM2-166
     *
     * @author alewis
     */
    @Test()
    public void invalidCompanyPostalCodeTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of company postal code field
        configVertexSetting.changeCompanyPostalCodeField(invalidCompanyPostalCode);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears for company postal code field
        String actualCompanyPostalCodeErrorText = configVertexSetting.verifyCompanyPostalCodeErrorMessage();
        assertEquals(expectedCompanyPostalCodeErrorText, actualCompanyPostalCodeErrorText);

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
        assertEquals(configVertexSetting.getSelectedTextForCompanyCountry(), validcompanyCountryValue);
        assertEquals(configVertexSetting.getSelectedTextForCompanyState(), companyStateValue);
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.companyPostalCodeField), validCompanyPostalCode);
        assertTrue(configVertexSetting.verifyVertexCompyInfoTab());
        assertTrue(configVertexSetting.verifyVertexDeliveryTermsTab());
        assertTrue(configVertexSetting.verifyVertexShippingProductCodesTab());
        assertTrue(configVertexSetting.verifyVertexLoggingTab());
        assertFalse(configVertexSetting.verifyWhenOrderStatusField());
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