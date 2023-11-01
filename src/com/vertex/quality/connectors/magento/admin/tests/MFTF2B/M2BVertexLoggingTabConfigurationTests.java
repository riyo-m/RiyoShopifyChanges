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
 * Test cases for verifying various fields in magento admin vertex Vertex Logging tab
 * configuration settings page
 *
 * @author alewis
 */

public class M2BVertexLoggingTabConfigurationTests extends MagentoAdminBaseTest {
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
     * tests change the value of Vertex Request Logging field
     * CMAGM2-176
     *
     * @author alewis
     */
    @Test()
    public void vertexRequestLoggingTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Vertex Request Logging field
        configVertexSetting.changeVertexRequestLoggingField("Disable");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and value in Vertex Request Logging field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextVertexRequestLoggingField(), "Disable");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Log Rotation field
     * CMAGM2-177
     *
     * @author alewis
     */
    @Test()
    public void logRotationTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Log Rotation field
        configVertexSetting.changelogRotationField("Disable");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and fields
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextLogRotationField(), "Disable");
        assertFalse(configVertexSetting.verifyRotationActionField());
        assertFalse(configVertexSetting.verifyLogEntryLifeTimeField());
        assertFalse(configVertexSetting.verifyRotationFrequencyField());
        assertFalse(configVertexSetting.verifyRotationTimeField());

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Rotation Action field
     * CMAGM2-178
     *
     * @author alewis
     */
    @Test()
    public void rotationActionTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Rotation Action field
        configVertexSetting.changeRotationActionField("Export to file and delete");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and value in Rotation Action field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextRotationActionField(), "Export to file and delete");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Log Entry Lifetime field
     * CMAGM2-179
     *
     * @author alewis
     */
    @Test()
    public void logEntryLifeTimeTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Log Entry Life time field
        configVertexSetting.changeLogEntryLifetimeField("0");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and value in Log Entry Life time field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.logEntryLifeTimeField), "0");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Rotation Frequency field
     * CMAGM2-180
     *
     * @author alewis
     */
    @Test()
    public void rotationFrequencyTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Rotation Frequency field
        configVertexSetting.changeRotationFrequencyField("Monthly");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and value in Rotation Frequency field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextRotationFrequencyField(), "Monthly");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of Rotation Time field
     * CMAGM2-181
     *
     * @author alewis
     */
    @Test()
    public void rotationTimeTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value Rotation Time field
        configVertexSetting.changeRotationTimeField("23", "22", "21");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying save configuration text and value in Rotation Time field
        assertTrue(configVertexSetting.verifySaveConfiguration());
        assertEquals(configVertexSetting.getSelectedTextRotationTimeHourField(), "23");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeMinuteField(), "22");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeSecondField(), "21");

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
        assertEquals(configVertexSetting.getSelectedTextVertexRequestLoggingField(), "Enable");
        assertEquals(configVertexSetting.getSelectedTextRotationActionField(), "Delete");
        assertEquals(configVertexSetting.getFieldText(configVertexSetting.logEntryLifeTimeField), "7");
        assertEquals(configVertexSetting.getSelectedTextRotationFrequencyField(), "Weekly");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeHourField(), "00");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeMinuteField(), "00");
        assertEquals(configVertexSetting.getSelectedTextRotationTimeSecondField(), "00");
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
