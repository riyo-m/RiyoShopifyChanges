package com.vertex.quality.connectors.magento.admin.tests.MFTF2B;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for verifying various fields in magento admin configuration settings page
 *
 * @author alewis
 */

public class M2BVertexSettingsTabConfigurationTests extends MagentoAdminBaseTest {

    protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
    protected String username = MagentoData.ADMIN_USERNAME.data;
    protected String password = MagentoData.ADMIN_PASSWORD.data;
    protected String loggedInHeaderText = "Dashboard";
    protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
    protected String expectedVertexApiStatus = "VALID";
    protected String expectedVertexApiStatus1 = "DISABLED";
    protected String expectedVertexApiStatus2 = "UNABLE TO CONNECT TO CALCULATION API";
    protected String expectedVertexApiStatus3 = "UNABLE TO CONNECT TO ADDRESS VALIDATION API";
    protected String expectedVertexApiStatus4 = "UNABLE TO VALIDATE ADDRESS AGAINST API";
    protected String invalidTrustedId = "1405705348746247FF";
    protected String invalidTrustedId2 = "15705348746247FF";
    protected String invalidTrustedId3 = "140";
    protected String expectedRequiredFieldErrorText = "This is a required field.";
    protected String expectedTrustedIdErrorText = "Please enter less or equal than 16 symbols.";
    protected String expectedTrustedIdErrorText2 = "Please enter more or equal than 6 symbols.";
    protected String sendInvoiceOrderStatus = "Order Status Is Changed";
    protected String orderStatus = "On Hold";
    protected String selectOrderStatus = "-- Please Select --";
    protected String invalidCalculationApiUrlText = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/CalculateTaxFF";
    protected String invalidAddressApiUrlText = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreasFF";
    protected String vertexApiStatus = "VALID";
    protected String validTrustedId = "1405705348746247";
    protected String validCalculationApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    protected String validAddressApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";

    /**
     * tests use vertex tax link field in vertex settings tab
     * CMAGM2-142
     *
     * @author alewis
     */
    @Test()
    public void useVertexTaxLinkFieldTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex tax links field
        configVertexSetting.changeUseVertexTaxLinksField("Disable");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying below Tabs for not displayed when use vertex tax links field is Disable
        assertFalse(configVertexSetting.verifyVertexCompyInfoTab(), "Verifying vertex company information tab is not displayed");
        assertFalse(configVertexSetting.verifyVertexDeliveryTermsTab(), "Verifying vertex delivery terms tab is not displayed");
        assertFalse(configVertexSetting.verifyVertexShippingProductCodesTab(), "Verifying vertex product codes tab is not displayed");
        assertFalse(configVertexSetting.verifyVertexLoggingTab(), "Verifying vertex logging tab is not displayed");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();

    }

    /**
     * tests vertex tax calculation field in vertex settings tab
     * CMAGM2-143
     *
     * @author alewis
     */
    @Test()
    public void vertexTaxCalculationFieldTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex tax calculation field
        configVertexSetting.changeVertexTaxCalculationField("Disable");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying vertex api status tab is DISABLED
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus1, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests invalid vertex Calculation API url field
     * in vertex settings tab
     * CMAGM2-144
     *
     * @author alewis
     */
    @Test()
    public void invalidVertexCalculationApiUrlTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex calculate api url field
        configVertexSetting.changeCalculationApiUrl(invalidCalculationApiUrlText);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying vertex api status tab is Unable to connect to Calculation API
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus2, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests Clear vertex Calculation API Url field
     * in vertex settings tab and Verify error message
     * CMAGM2-145
     *
     * @author alewis
     */
    @Test()
    public void clearVertexCalculationApiUrlTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex calculate api url field to empty
        configVertexSetting.changeCalculationApiUrl("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying required field is displaying or not for cal url api field
        String actualRequiredFieldText = configVertexSetting.verifyCalculationUrlRequiredField();
        assertEquals(expectedRequiredFieldErrorText, actualRequiredFieldText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests invalid vertex Address API url field
     * in vertex settings tab
     * CMAGM2-146
     *
     * @author alewis
     */
    @Test()
    public void invalidVertexAddressValidationApiTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex Address api url field
        configVertexSetting.changeAddressApiUrl(invalidAddressApiUrlText);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying vertex api status tab is Unable to connect to ADDRESS API
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus3, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests Clear vertex Address API Url field
     * in vertex settings tab and Verify error message
     * CMAGM2-147
     *
     * @author alewis
     */
    @Test()
    public void clearVertexAddressApiUrlTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex address api url field to empty
        configVertexSetting.changeAddressApiUrl("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying required field is displaying or not for address url api field
        String actualRequiredFieldErrorText = configVertexSetting.verifyAddressUrlRequiredField();
        assertEquals(expectedRequiredFieldErrorText, actualRequiredFieldErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests invalid vertex Trusted ID field
     * in vertex settings tab
     * CMAGM2-148
     *
     * @author alewis
     */
    @Test()
    public void invalidVertexTrustedIdTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex trusted id field to invalid
        configVertexSetting.changeTrustedIdField(invalidTrustedId);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears directly below the modified field.
        String actualTrustedIdErrorText = configVertexSetting.verifyTrustedIdErrorMessage();
        assertEquals(expectedTrustedIdErrorText, actualTrustedIdErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests required field message for vertex Trusted ID field
     * in vertex settings tab
     * CMAGM2-149
     *
     * @author alewis
     */
    @Test()
    public void clearVertexTrustedIdTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex trusted id field to empty
        configVertexSetting.changeTrustedIdField("");

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears directly below the modified field.
        String actualRequiredFieldErrorText = configVertexSetting.verifyTrustedIdRequiredField();
        assertEquals(expectedRequiredFieldErrorText, actualRequiredFieldErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests check's api status with invalid trusted id
     * in vertex settings tab
     * CMAGM2-150
     *
     * @author alewis
     */
    @Test()
    public void checkApiStatusWithInvalidTrustedIdTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex trusted id field to invalid
        configVertexSetting.changeTrustedIdField(invalidTrustedId2);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying vertex api status tab is UNABLE TO VALIDATE ADDRESS AGAINST API
        String actualApiStatus = configVertexSetting.getMultipleApiStatus();
        assertEquals(expectedVertexApiStatus4, actualApiStatus);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests invalid vertex Trusted ID with value of 140 field
     * in vertex settings tab
     * CMAGM2-151
     *
     * @author alewis
     */
    @Test()
    public void invalidVertexTrustedIdLessThanCharLimitTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value of vertex trusted id field
        configVertexSetting.changeTrustedIdField(invalidTrustedId3);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verifying error message appears directly below the modified field.
        String actualTrustedIdErrorText = configVertexSetting.verifyTrustedIdErrorMessage();
        assertEquals(expectedTrustedIdErrorText2, actualTrustedIdErrorText);

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests verifies invoice when order status is displaying or not
     * in vertex settings tab
     * CMAGM2-152
     *
     * @author alewis
     */
    @Test()
    public void verifyInvoiceWhenOrderStatusFieldTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value When to Send Invoice To Vertex field
        configVertexSetting.changeSendInvoiceToVertexField(sendInvoiceOrderStatus);

        //Click on save configuration
        configVertexSetting.saveConfig();

        configVertexSetting.clickIntegrationSettingsTab();

        //Verifying Invoice When Order Status Field is Displaying or not
        assertTrue(configVertexSetting.verifyWhenOrderStatusField(), "Verifying Invoice When Order Status Field is displayed");

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests change the value of invoice when order status value TO Hold and verify
     * in vertex settings tab
     * CMAGM2-153
     *
     * @author alewis
     */
    @Test()
    public void verifyInvoiceWhenOrderStatusFieldSetToHoldTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value When to Send Invoice To Vertex field
        configVertexSetting.changeSendInvoiceToVertexField(sendInvoiceOrderStatus);

        //Change Invoice When Order Status Field Value To Hold
        configVertexSetting.changeInvoiceWhenOrderStatusField(orderStatus);

        //Click on save configuration
        configVertexSetting.saveConfig();

        configVertexSetting.clickIntegrationSettingsTab();

        //Verify Invoice When Order Status Field Value To Hold
        assertTrue(configVertexSetting.verifyWhenOrderStatusField(), "Verifying Invoice When Order Status Field is displayed");
        assertTrue(configVertexSetting.getSelectedTextOrderStatus().equalsIgnoreCase(orderStatus));

        //Resetting Configuration to Default
        configVertexSetting.resetConfigSettingToDefault();

        //Validating Configuration Setting fields after reset to default
        validatingConfigFieldsAfterReset();
    }

    /**
     * tests verifies the required field for invoice when order status field
     * in vertex settings tab
     * CMAGM2-154
     *
     * @author alewis
     */
    @Test()
    public void invoiceWhenOrderStatusRequiredFieldTest() {
        //Log In to Magento Application and Navigate to the Configuration Page
        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

        //Changing the value When to Send Invoice To Vertex field
        configVertexSetting.changeSendInvoiceToVertexField(sendInvoiceOrderStatus);

        //Change Invoice When Order Status Field Value Please Select
        configVertexSetting.changeInvoiceWhenOrderStatusField(selectOrderStatus);

        //Click on save configuration
        configVertexSetting.saveConfig();

        //Verify RequiredField for Invoice When Order Status Field
        assertTrue(configVertexSetting.verifyWhenOrderStatusField(), "Verifying Invoice When Order Status Field is displayed");
        assertTrue(configVertexSetting.verifyRequiredFieldOrderStatus().equalsIgnoreCase(expectedRequiredFieldErrorText));

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