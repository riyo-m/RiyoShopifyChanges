package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the Sales Tax Configuration Page
 *
 * @author alewis
 */
public class M2AdminSalesTaxConfigPage extends MagentoAdminPage {
    By statusID = By.id("grid-severity-minor");
    By valueClass = By.className("value");
    By statusClass = By.className("grid-severity-notice");
    By vertexSettingsID = By.id("tax_vertex_integration_invoice_order_status");
    By productDefaultTaxClassID = By.id("tax_classes_default_product_tax_class");
    By saveID = By.id("save");
    By defaultTaxClassCustomerId = By.id("tax_classes_default_customer_tax_class");
    By useVertexTaxLinks = By.xpath("//select[@id='tax_vertex_connection_enable_vertex']");
    By vertexTaxCalculation = By.id("tax_vertex_integration_use_for_calculation");
    By multipleApiStatus = By.xpath("//tr[@id='row_tax_vertex_connection_vertex_status']//td[@class='value']//span[@class]");
    By trustedIdErrorField = By.id("tax_vertex_connection_trusted_id-error");
    By trustedIdRequiredField = By.id("tax_vertex_connection_trusted_id-error");
    By useVertexForOrdersShippingToField = By.id("tax_vertex_integration_allowed_countries");
    By whenSendInvoiceToVertex = By.id("tax_vertex_integration_invoice_order");
    By invoiceWhenOrderStatusField = By.id("row_tax_vertex_integration_invoice_order_status");
    By whenOrderStatusDropDown = By.id("tax_vertex_integration_invoice_order_status");
    By vertexCompanyInfoTab = By.id("tax_vertex_seller_info-head");
    By vertexDeliveryTermsTab = By.id("tax_vertex_delivery_terms-head");
    By vertexShippingProductCodesTab = By.id("tax_vertex_advanced-head");
    By vertexLoggingTab = By.id("tax_vertex_logging-head");
    By rotationActionFieldTab = By.id("row_tax_vertex_logging_rotation_action");
    By logEntryLifetimeFieldTab = By.id("row_tax_vertex_logging_entry_lifetime");
    By rotationFrequencyFieldTab = By.id("row_tax_vertex_logging_rotation_frequency");
    By rotationTimeFieldTab = By.id("row_tax_vertex_logging_rotation_runtime");
    By requiredFieldCalUrlApi = By.id("tax_vertex_connection_api_url-error");
    By requiredFieldAddressUrlApi = By.id("tax_vertex_connection_address_api_url-error");
    By requiredFieldInvoiceOrderStatus = By.id("tax_vertex_integration_invoice_order_status-error");
    By requiredFieldCompanyState = By.id("tax_vertex_seller_info_region_id-error");
    By companyCodeErrorField = By.id("tax_vertex_seller_info_company-error");
    By locationCodeErrorField = By.id("tax_vertex_seller_info_location_code-error");
    By companyStreetAddressErrorField = By.id("tax_vertex_seller_info_street_address_1-error");
    By companyStreetAddressTwoErrorField = By.id("tax_vertex_seller_info_street_address_2-error");
    By companyCityErrorField = By.id("tax_vertex_seller_info_city-error");
    By companyPostalCodeErrorField = By.id("tax_vertex_seller_info_postal_code-error");
    By saveConfiguration = By.id("messages");
    By addButtonDeliveryTerm = By.xpath("//td[@class='col-actions-add']//button[@title='Add']");
    By countryDeliveryTermErrorField = By.xpath("//label[@class='mage-error']");
    By deliveryItemTermOverride = By.xpath("//table[@id='tax_vertex_delivery_terms_override']//tbody");
    By deleteDeliveryTermItems = By.xpath("//td[@class='col-actions']//button");
    By checkDisplayProductPricesInCatalogField = By.id("tax_display_type_inherit");
    By displayProductPricesInCatalogField = By.id("tax_display_type");
    By apiStatusErrorMessageField = By.className("vertex__automatically-disabled-message");
    By vertexRequestLoggingField = By.id("tax_vertex_logging_enable_logging");
    By logRotationField = By.id("tax_vertex_logging_enable_rotation");
    By rotationActionField = By.id("tax_vertex_logging_rotation_action");
    By rotationFrequencyField = By.id("tax_vertex_logging_rotation_frequency");
    By rotationTimeFieldHour = By.xpath("//select[@data-ui-id='time-groups-vertex-groups-logging-fields-rotation-runtime-value-hour']");
    By rotationTimeFieldMinute = By.xpath("//select[@data-ui-id='time-groups-vertex-groups-logging-fields-rotation-runtime-value-minute']");
    By rotationTimeFieldSecond = By.xpath("//select[@data-ui-id='time-groups-vertex-groups-logging-fields-rotation-runtime-value-second']");
    By freeShippingField = By.xpath("//tbody/tr[@id='row_tax_vertex_advanced_shipping_codes']//td[@class='value'][contains(text(), ' freeshipping_freeshipping')]");
    By connectionSettings = By.id("tax_vertex_connection-head");
    By expandedTaxamoSection = By.xpath(".//a[text()='Taxamo']/parent::div/parent::div[@class='section-config active']");
    By taxamoSection = By.id("tax_taxamo-head");
    By integrationSettings = By.id("tax_vertex_integration-head");
    By developerSupportInformation = By.id("tax_vertex_advanced-head");
    public By calculationApiUrl = By.id("tax_vertex_connection_api_url");
    public By addressApiUrl = By.id("tax_vertex_connection_address_api_url");
    public By trustedId = By.id("tax_vertex_connection_trusted_id");
    public By companyCodeField = By.id("tax_vertex_seller_info_company");
    public By locationCodeField = By.id("tax_vertex_seller_info_location_code");
    public By companyStressAddressField = By.id("tax_vertex_seller_info_street_address_1");
    public By companyStressAddressTwoField = By.id("tax_vertex_seller_info_street_address_2");
    public By companyCityField = By.id("tax_vertex_seller_info_city");
    public By companyCountryField = By.id("tax_vertex_seller_info_country_id");
    public By companyStateFieldText = By.xpath("//input[@id='tax_vertex_seller_info_region_id']");
    public By companyStateFieldSelect = By.xpath("//select[@id='tax_vertex_seller_info_region_id']");
    public By companyPostalCodeField = By.id("tax_vertex_seller_info_postal_code");
    public By expandedGlobalDeliverySection = By.xpath(".//tr[@id='row_tax_vertex_delivery_terms']//div[@class='section-config active']");
    public By collapsedGlobalDeliverySection = By.xpath(".//tr[@id='row_tax_vertex_delivery_terms']//div[@class='section-config']");
    public By globalDeliveryTermField = By.id("tax_vertex_delivery_terms_default_term");
    public By globalDeliveryTermCheckbox = By.id("tax_vertex_delivery_terms_default_term_inherit");
    public By logEntryLifeTimeField = By.id("tax_vertex_logging_entry_lifetime");
    public By saveAndTestCredentialsButton = By.xpath(".//button[@id='tax_taxamo_test_credentials']");

    protected By apiTokenInputBox = By.id("tax_taxamo_api_token");
    protected By calculationApiUrlInput = By.xpath("//*[contains(text(),'Calculation API URL')]//following::input[1]");
    protected By addressValApiUrlInput = By.xpath("//*[contains(text(),'Address Validation API URL')]//following::input[1]");
    protected By apiStatusClass = By.xpath("//*[contains(text(),'API Status')]//following::span[2]");
    protected By healthCheckTestMSG = By.xpath(".//p[@id='test_result']");

    protected String allTaxEnabledEUCountries = ".//select[@id='tax_taxamo_enabled_countries']//option";

    protected String vertexApiStatus = "VALID";
    protected String validTrustedId = "1405705348746247";
    protected String whenInvoiceCreated = "Invoice Created";
    protected String companyCode = "Store4GA";
    protected String validCompanyStreetAddressValue = "2301 Renaissance Blvd";
    protected String validCompanyCityValue = "King of Prussia";
    protected String validCompanyCountryValue = "United States";
    protected String validCompanyStateValue = "Pennsylvania";
    protected String validCompanyPostalCode = "19406";
    protected String globalDeliveryTermText = "FOB - Free Onboard Vessel";
    protected String validGlobalDeliveryTermText = "SUP - Supplier Ships";
    protected String validCalculationApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    protected String validAddressApiUrl = "https://mgcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminSalesTaxConfigPage(WebDriver driver) {
        super(driver);
    }

    /**
     * gets the header of the Sales Tax Configuration Page
     *
     * @return the header of Sales TAx Configuration Page
     */
    public String getPageHeader() {
        By header = By.id("tax_vertex-head");
        WebElement headerClass = wait.waitForElementPresent(header);
        String headerText = headerClass.getText();

        return headerText;
    }

    /**
     * gets the status of the Vertex API Status tab
     *
     * @return returns valid or invalid
     */
    public String getVertexAPIStatus() {
        WebElement rowElement = wait.waitForElementPresent(statusID);
        WebElement valueElement = rowElement.findElement(valueClass);
        WebElement noticeElement = valueElement.findElement(statusClass);
        String status = noticeElement.getText();

        return status;
    }

    /**
     * gets the status of the connection settings API Status tab
     *
     * @return returns valid or invalid
     */
    public String getConnectionAPIStatus() {
        WebElement apiStatusElement = wait.waitForElementDisplayed(apiStatusClass);
        String status = apiStatusElement.getText();

        return status;
    }

    /**
     * gets the status of the Vertex API Status tab
     *
     * @param statusString status value
     */
    public void changeWhenToSendInvoice(String statusString) {
        WebElement vertexTaxSetting = wait.waitForElementDisplayed(vertexSettingsID);
        dropdown.selectDropdownByValue(vertexTaxSetting, statusString);
        click.clickElement(saveID);
    }

    /**
     * change the default Tax Class in configuration settings
     *
     * @param taxClass value of tax class
     */
    public void changeDefaultTaxClassForProduct(String taxClass) {
        WebElement productDefaultTaxClass = wait.waitForElementDisplayed(productDefaultTaxClassID);
        dropdown.selectDropdownByDisplayName(productDefaultTaxClass, taxClass);
        click.clickElement(saveID);
        waitForPageLoad();
    }

    /**
     * selects the default tax class for customer groups
     * by the displayed name
     *
     * @param statusString display name of tax class to select
     */
    public void changeDefaultCustomerTaxClass(String statusString) {
        WebElement vertexTaxSetting = wait.waitForElementDisplayed(defaultTaxClassCustomerId);
        dropdown.selectDropdownByDisplayName(vertexTaxSetting, statusString);
        click.clickElement(saveID);
    }

    /**
     * verify Connection Settings Tab is Displayed or not
     *
     * @return getConnectionSettingsTab True or False
     */
    public boolean verifyConnectionSettingsTab() {
        WebElement connectionSettingsTab = wait.waitForElementPresent(connectionSettings);
        boolean getConnectionSettingsTab = connectionSettingsTab.isDisplayed();
        return getConnectionSettingsTab;
    }

    /**
     * verify Integration Settings Tab is Displayed or not
     *
     * @return getIntegrationSettingsTab True or False
     */
    public boolean verifyIntegrationSettingsTab() {
        WebElement integrationSettingsTab = wait.waitForElementPresent(integrationSettings);
        boolean getIntegrationSettingsTab = integrationSettingsTab.isDisplayed();
        return getIntegrationSettingsTab;
    }

    /**
     * verify Developer & Support Information Tab is Displayed or not
     *
     * @return getIntegrationSettingsTab True or False
     */
    public boolean verifyDeveloperSupportInformationTab() {
        WebElement integrationSettingsTab = wait.waitForElementPresent(developerSupportInformation);
        boolean getIntegrationSettingsTab = integrationSettingsTab.isDisplayed();
        return getIntegrationSettingsTab;
    }

    /**
     * click on Connection Settings Tab
     */
    public void clickConnectionSettingsTab() {
        if (verifyConnectionSettingsTab()) {
            waitForPageLoad();
            WebElement connectionSettingsTab = wait.waitForElementPresent(connectionSettings, 5);
            click.clickElement(connectionSettingsTab);
        }
    }

    /**
     * click on Taxamo Tab
     */
    public void clickOnTaxamoTab() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        WebElement taxamo = wait.waitForElementPresent(taxamoSection);
        if (!element.isElementDisplayed(expandedTaxamoSection)) {
            click.moveToElementAndClick(taxamo);
        }
    }

    /**
     * Enter calculation api url
     *
     * @param url which is string url of Calculation API endpoints of O-Series
     */
    public void enterCalculationAPIUrl(String url) {
        if (verifyConnectionSettingsTab()) {
            wait.waitForElementPresent(calculationApiUrlInput, 5);
            text.clearText(calculationApiUrlInput);
            text.enterText(calculationApiUrlInput, url);
        }
    }

    /**
     * Enters API Token
     *
     * @param token API Token value
     */
    public void enterAPIToken(String token) {
        if (token.isEmpty() | token.isBlank()) {
            token = MagentoData.VALID_API_TOKEN.data;
        }
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        text.enterText(wait.waitForElementPresent(apiTokenInputBox), token);
    }

    /**
     * Click on Save and Test Credentials button
     */
    public void clickSaveAndTestCredentialsButton() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(saveAndTestCredentialsButton));
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Get health-check pass or fail message from the UI
     *
     * @return health-check message
     */
    public String getHealthCheckMSGFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        return text.getElementText(wait.waitForElementPresent(healthCheckTestMSG));
    }

    /**
     * Enter address validation api url
     *
     * @param url which is string url of Address validation API endpoints of O-Series
     */
    public void enterAddressValidAPIUrl(String url) {
        if (verifyConnectionSettingsTab()) {
            wait.waitForElementPresent(addressValApiUrlInput, 5);
            text.clearText(addressApiUrl);
            text.enterText(addressValApiUrlInput, url);
        }
    }

    /**
     * click on Integration Settings Tab
     */
    public void clickIntegrationSettingsTab() {
        if (verifyIntegrationSettingsTab()) {
            waitForPageLoad();
            WebElement integrationSettingsTab = wait.waitForElementPresent(integrationSettings, 5);
            click.clickElement(integrationSettingsTab);
        }
    }

    /**
     * click on Developed And Support Information Tab
     */
    public void clickDeveloperSupportInformationTab() {
        if (verifyDeveloperSupportInformationTab()) {
            WebElement developerSupportInformationTab = wait.waitForElementPresent(developerSupportInformation, 5);
            click.clickElement(developerSupportInformationTab);
        }
    }

    /**
     * changes the value of user vertex tax link field
     * (enable/disable) in vertex settings tab
     *
     * @param useTaxLinkValue set value to enable or disable
     */
    public void changeUseVertexTaxLinksField(String useTaxLinkValue) {
        WebElement useVertexTaxLinksSettings = wait.waitForElementPresent(useVertexTaxLinks);
        dropdown.selectDropdownByDisplayName(useVertexTaxLinksSettings, useTaxLinkValue);
    }

    /**
     * changes the value of vertex tax calculation field
     * (enable/disable) in vertex settings tab
     *
     * @param taxCalculationValue set value to enable or disable
     */
    public void changeVertexTaxCalculationField(String taxCalculationValue) {
        WebElement vertexTaxCalculationSettings = wait.waitForElementPresent(vertexTaxCalculation);
        dropdown.selectDropdownByDisplayName(vertexTaxCalculationSettings, taxCalculationValue);
    }

    /**
     * changes the value of vertex calculation api url field
     * in vertex settings tab
     *
     * @param calculationUrlValue set value for vertex calculation api url field
     */
    public void changeCalculationApiUrl(String calculationUrlValue) {
        wait.waitForElementPresent(calculationApiUrl);
        text.clearText(calculationApiUrl);
        text.enterText(calculationApiUrl, calculationUrlValue);
    }

    /**
     * changes the value of vertex Address api url field
     * in vertex settings tab
     *
     * @param addressUrlValue set value for vertex Address api url field
     */
    public void changeAddressApiUrl(String addressUrlValue) {
        wait.waitForElementPresent(addressApiUrl);
        text.clearText(addressApiUrl);
        text.enterText(addressApiUrl, addressUrlValue);
    }

    /**
     * changes the value of vertex trusted id field
     * in vertex settings tab
     *
     * @param trustedIdValue set value for vertex trusted id field
     */
    public void changeTrustedIdField(String trustedIdValue) {
        wait.waitForElementPresent(trustedId);
        text.clearText(trustedId);
        text.enterText(trustedId, trustedIdValue);
    }

    /**
     * To select Country from the dropdown 'Use Vertex for orders shipping to field'
     *
     * @param shippingTo set value for Use Vertex for orders shipping to field
     */
    public void changeUseVertexForOrdersShippingToField(String shippingTo) {
        WebElement useVertexForOrdersShippingTo = wait.waitForElementPresent(useVertexForOrdersShippingToField);
        dropdown.selectDropdownByDisplayName(useVertexForOrdersShippingTo, shippingTo);
    }

    /**
     * changes the value of When to send invoice to Vertex field
     *
     * @param sendInvoice set value for vertex trusted id field
     */
    public void changeSendInvoiceToVertexField(String sendInvoice) {
        WebElement sendInvoiceVertexField = wait.waitForElementPresent(whenSendInvoiceToVertex);
        dropdown.selectDropdownByDisplayName(sendInvoiceVertexField, sendInvoice);
    }

    /**
     * changes the value of When to send invoice to Vertex field
     *
     * @param orderStatusValue set value for When to send invoice to Vertex field
     */
    public void changeInvoiceWhenOrderStatusField(String orderStatusValue) {
        WebElement invoiceWhenOrderStatusField = wait.waitForElementPresent(whenOrderStatusDropDown);
        dropdown.selectDropdownByDisplayName(invoiceWhenOrderStatusField, orderStatusValue);
    }

    /**
     * changes the value of company code field
     *
     * @param companyCodeValue set value for company code field
     */
    public void changeCompanyCodeField(String companyCodeValue) {
        wait.waitForElementEnabled(companyCodeField);
        text.clearText(companyCodeField);
        text.enterText(companyCodeField, companyCodeValue);
    }

    /**
     * changes the value of location code field
     *
     * @param locationCodeValue set value for location code field
     */
    public void changeLocationCodeField(String locationCodeValue) {
        wait.waitForElementEnabled(locationCodeField);
        text.clearText(locationCodeField);
        text.enterText(locationCodeField, locationCodeValue);
    }

    /**
     * changes the value of company street address field
     *
     * @param companyStreetAddressOneValue set value for company street address1 field
     */
    public void changeCompanyStreetAddressOneField(String companyStreetAddressOneValue) {
        wait.waitForElementEnabled(companyStressAddressField);
        text.clearText(companyStressAddressField);
        text.enterText(companyStressAddressField, companyStreetAddressOneValue);
    }

    /**
     * changes the value of company street address two field
     *
     * @param companyStreetAddressTwoValue set value for company street address two field
     */
    public void changeCompanyStreetAddressTwoField(String companyStreetAddressTwoValue) {
        wait.waitForElementEnabled(companyStressAddressTwoField);
        text.clearText(companyStressAddressTwoField);
        text.enterText(companyStressAddressTwoField, companyStreetAddressTwoValue);
    }

    /**
     * changes the value of company city field
     *
     * @param companyCityValue set value for company city field
     */
    public void changeCompanyCityField(String companyCityValue) {
        wait.waitForElementEnabled(companyCityField);
        text.clearText(companyCityField);
        text.enterText(companyCityField, companyCityValue);
    }

    /**
     * changes the value of company country field
     *
     * @param companyCountryValue set value for company country field
     */
    public void changeCompanyCountryField(String companyCountryValue) {
        WebElement companyCountry = wait.waitForElementPresent(companyCountryField);
        dropdown.selectDropdownByDisplayName(companyCountry, companyCountryValue);
    }

    /**
     * changes the value of company state field
     *
     * @param companyStateTextValue set value for company state field
     */
    public void changeCompanyStateTextField(String companyStateTextValue) {
        wait.waitForElementEnabled(companyStateFieldText);
        text.clearText(companyStateFieldText);
        text.enterText(companyStateFieldText, companyStateTextValue);
    }

    /**
     * changes the value of company state field
     *
     * @param companyStateSelectValue set value for company state field
     */
    public void changeCompanyStateSelectField(String companyStateSelectValue) {
        WebElement companyState = wait.waitForElementPresent(companyStateFieldSelect);
        dropdown.selectDropdownByDisplayName(companyState, companyStateSelectValue);
    }

    /**
     * changes the value of company postal code field
     *
     * @param companyPostalCodeValue set value for company postal code field
     */
    public void changeCompanyPostalCodeField(String companyPostalCodeValue) {
        wait.waitForElementEnabled(companyPostalCodeField);
        text.clearText(companyPostalCodeField);
        text.enterText(companyPostalCodeField, companyPostalCodeValue);
    }

    /**
     * Expands the global delivery term section
     */
    public void expandGlobalDeliveryTerm() {
        waitForSpinnerToBeDisappeared();
        if (!element.isElementPresent(expandedGlobalDeliverySection)) {
            click.moveToElementAndClick(collapsedGlobalDeliverySection);
            wait.waitForElementPresent(expandedGlobalDeliverySection);
        }
    }

    /**
     * changes the value of global delivery term field
     *
     * @param globalDeliveryTermValue set value for global delivery term field
     */
    public void changeGlobalDeliveryTermField(String globalDeliveryTermValue) {
        expandGlobalDeliveryTerm();
        selectDeselectGlobalDeliveryTerm(false);
        WebElement globalDeliveryTerm = wait.waitForElementPresent(globalDeliveryTermField);
        dropdown.selectDropdownByDisplayName(globalDeliveryTerm, globalDeliveryTermValue);
    }

    /**
     * Selects Use System Default checkbox for Global Delivery Term
     *
     * @param select true to select & false to deselect
     */
    public void selectDeselectGlobalDeliveryTerm(boolean select) {
        waitForSpinnerToBeDisappeared();
        WebElement deliveryTerm = wait.waitForElementPresent(globalDeliveryTermCheckbox);
        if (select && !checkbox.isCheckboxChecked(deliveryTerm)) {
            click.moveToElementAndClick(deliveryTerm);
        } else if (!select && checkbox.isCheckboxChecked(deliveryTerm)) {
            click.moveToElementAndClick(deliveryTerm);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * click add button in delivery term override field
     */
    public void clickAddButtonDeliveryOverride() {
        WebElement addButton = wait.waitForElementPresent(addButtonDeliveryTerm);
        click.clickElementCarefully(addButton);
    }

    /**
     * changes the value of global delivery term field
     *
     * @param countryValue      Country
     * @param deliveryTermValue Delivery Term
     * @param rowNumber         Row No
     */
    public void changeDeliveryTermOverrideField(String countryValue, String deliveryTermValue, int rowNumber) {
        clickAddButtonDeliveryOverride();
        String country = String.format("//tbody/tr[" + rowNumber + "]/td/select[contains(@name, '[country_id]')]");
        By countryDeliveryTermField = By.xpath(country);
        WebElement countryDeliveryTerm = wait.waitForElementDisplayed(countryDeliveryTermField);
        dropdown.selectDropdownByDisplayName(countryDeliveryTerm, countryValue);
        String delivery = String.format("//tbody/tr[" + rowNumber + "]/td/select[contains(@name, '[delivery_term]')]");
        By deliveryTermField = By.xpath(delivery);
        WebElement deliveryTerm = wait.waitForElementDisplayed(deliveryTermField);
        dropdown.selectDropdownByDisplayName(deliveryTerm, deliveryTermValue);
    }

    /**
     * delete delivery term override items
     */
    public void deleteDeliveryTermOverrideItems() {
        List<WebElement> elements = wait.waitForAllElementsDisplayed(deleteDeliveryTermItems);
        for (int i = 1; i <= elements.size(); i++) {
            WebElement deleteDelivery = driver.findElement(By.xpath("(//td[@class='col-actions']//button)['" + i + "']"));
            wait.waitForElementDisplayed(deleteDelivery);
            click.clickElementCarefully(deleteDelivery);
        }
    }

    /**
     * check the checkbox to true or false for Display Product Prices In Catalog
     *
     * @param checkUseSystemValue set True or False for Checkbox in display product
     */
    public void checkCheckBoxInDisplayProductField(boolean checkUseSystemValue) {
        wait.waitForElementDisplayed(checkDisplayProductPricesInCatalogField);
        checkbox.setCheckbox(checkDisplayProductPricesInCatalogField, checkUseSystemValue);
    }

    /**
     * verify the checkbox to true or false for Display Product Prices In Catalog
     *
     * @return checkUseSystemValue true or false for checkbox use system value
     */
    public boolean verifyCheckBoxDisplayProduct() {
        wait.waitForElementDisplayed(checkDisplayProductPricesInCatalogField);
        boolean checkUseSystemValue = checkbox.isCheckboxChecked(checkDisplayProductPricesInCatalogField);
        return checkUseSystemValue;
    }

    /**
     * change the dropdown value of display product prices in catalog field
     *
     * @param displayProductPriceCatalogValue set value for the dropdown
     */
    public void changeDisplayProductPricesInCatalogField(String displayProductPriceCatalogValue) {
        WebElement globalDeliveryTerm = wait.waitForElementPresent(displayProductPricesInCatalogField);
        dropdown.selectDropdownByDisplayName(globalDeliveryTerm, displayProductPriceCatalogValue);
    }

    /**
     * change the dropdown value of Vertex Request Logging field
     *
     * @param vertexRequestLoggingValue set value for the dropdown
     */
    public void changeVertexRequestLoggingField(String vertexRequestLoggingValue) {
        WebElement vertexRequestLogging = wait.waitForElementPresent(vertexRequestLoggingField);
        dropdown.selectDropdownByDisplayName(vertexRequestLogging, vertexRequestLoggingValue);
    }

    /**
     * change the dropdown value of log Rotation field
     *
     * @param logRotationValue set value for the dropdown
     */
    public void changelogRotationField(String logRotationValue) {
        WebElement logRotation = wait.waitForElementPresent(logRotationField);
        dropdown.selectDropdownByDisplayName(logRotation, logRotationValue);
    }

    /**
     * change the dropdown value of Rotation Action field
     *
     * @param rotationActionValue set value for the dropdown
     */
    public void changeRotationActionField(String rotationActionValue) {
        WebElement rotationAction = wait.waitForElementPresent(rotationActionField);
        dropdown.selectDropdownByDisplayName(rotationAction, rotationActionValue);
    }

    /**
     * changes the value of Log Entry Life-time field
     *
     * @param logEntryLifeTimeValue set value for company postal code field
     */
    public void changeLogEntryLifetimeField(String logEntryLifeTimeValue) {
        wait.waitForElementEnabled(logEntryLifeTimeField);
        text.clearText(logEntryLifeTimeField);
        text.enterText(logEntryLifeTimeField, logEntryLifeTimeValue);
    }

    /**
     * change the dropdown value of Rotation Frequency field
     *
     * @param rotationFrequencyValue set value for the dropdown
     */
    public void changeRotationFrequencyField(String rotationFrequencyValue) {
        WebElement rotationFrequency = wait.waitForElementPresent(rotationFrequencyField);
        dropdown.selectDropdownByDisplayName(rotationFrequency, rotationFrequencyValue);
    }

    /**
     * change the dropdown value of Rotation Time field
     *
     * @param rotationTimeHourValue   set value for the dropdown
     * @param rotationTimeMinuteValue set value for the dropdown
     * @param rotationTimeSecondValue set value for the dropdown
     */
    public void changeRotationTimeField(String rotationTimeHourValue, String rotationTimeMinuteValue, String rotationTimeSecondValue) {
        WebElement rotationTimeHour = wait.waitForElementPresent(rotationTimeFieldHour);
        dropdown.selectDropdownByDisplayName(rotationTimeHour, rotationTimeHourValue);
        WebElement rotationTimeMinute = wait.waitForElementPresent(rotationTimeFieldMinute);
        dropdown.selectDropdownByDisplayName(rotationTimeMinute, rotationTimeMinuteValue);
        WebElement rotationTimeSecond = wait.waitForElementPresent(rotationTimeFieldSecond);
        dropdown.selectDropdownByDisplayName(rotationTimeSecond, rotationTimeSecondValue);
    }

    /**
     * verify the error message appear below api status field
     *
     * @return apiStatusErrorText apiStatus text
     */
    public String verifyApiStatusErrorMessageBelow() {
        WebElement apiStatusErrorMessage = wait.waitForElementPresent(apiStatusErrorMessageField);
        String apiStatusErrorText = text.getElementText(apiStatusErrorMessage);
        return apiStatusErrorText;
    }

    /**
     * verifying save configuration is displaying or not
     *
     * @return getSaveConfigurationText returns true or false
     */
    public boolean verifySaveConfiguration() {
        WebElement saveConfigurationText = wait.waitForElementPresent(saveConfiguration);
        boolean getSaveConfigurationText = saveConfigurationText.isDisplayed();
        return getSaveConfigurationText;
    }

    /**
     * verifying Invoice When Order Status Field
     * is displaying or not
     *
     * @return getWhenOrderStatus returns true or false
     */
    public boolean verifyWhenOrderStatusField() {
        WebElement whenOrderStatus = wait.waitForElementPresent(invoiceWhenOrderStatusField);
        boolean getWhenOrderStatus = whenOrderStatus.isDisplayed();
        return getWhenOrderStatus;
    }

    /**
     * verifying trusted id error message
     * is displaying or not
     *
     * @return trustedIdErrorText returns error message
     */
    public String verifyTrustedIdErrorMessage() {
        WebElement trustedIdErrorMessage = wait.waitForElementPresent(trustedIdErrorField);
        String trustedIdErrorText = text.getElementText(trustedIdErrorMessage);
        return trustedIdErrorText;
    }

    /**
     * verifying required field of vertex trusted id field
     * is displaying or not
     *
     * @return trustedIdRequiredText returns required message
     */
    public String verifyTrustedIdRequiredField() {
        WebElement TrustedIdRequiredField = wait.waitForElementPresent(trustedIdRequiredField);
        String trustedIdRequiredText = text.getElementText(TrustedIdRequiredField);
        return trustedIdRequiredText;
    }

    /**
     * verifying company code error message
     * is displaying or not
     *
     * @return companyCodeErrorText returns error message
     */
    public String verifyCompanyCodeErrorMessage() {
        WebElement companyCodeErrorMessage = wait.waitForElementPresent(companyCodeErrorField);
        String companyCodeErrorText = text.getElementText(companyCodeErrorMessage);
        return companyCodeErrorText;
    }

    /**
     * verifying location code error message
     * is displaying or not
     *
     * @return locationCodeErrorText returns error message
     */
    public String verifyLocationCodeErrorMessage() {
        WebElement locationCodeErrorMessage = wait.waitForElementPresent(locationCodeErrorField);
        String locationCodeErrorText = text.getElementText(locationCodeErrorMessage);
        return locationCodeErrorText;
    }

    /**
     * verifying company street address error message
     * is displaying or not
     *
     * @return companyStreetAddressErrorText returns error message
     */
    public String verifyCompanyStreetAddressErrorMessage() {
        WebElement companyStreetAddressErrorMessage = wait.waitForElementPresent(companyStreetAddressErrorField);
        String companyStreetAddressErrorText = text.getElementText(companyStreetAddressErrorMessage);
        return companyStreetAddressErrorText;
    }

    /**
     * verifying company street address Two error message
     * is displaying or not
     *
     * @return companyStreetAddressTwoErrorText returns error message
     */
    public String verifyCompanyStreetAddressTwoErrorMessage() {
        WebElement companyStreetAddressTwoErrorMessage = wait.waitForElementPresent(companyStreetAddressTwoErrorField);
        String companyStreetAddressTwoErrorText = text.getElementText(companyStreetAddressTwoErrorMessage);
        return companyStreetAddressTwoErrorText;
    }

    /**
     * verifying company city error message
     * is displaying or not
     *
     * @return companyCityErrorText returns error message
     */
    public String verifyCompanyCityErrorMessage() {
        WebElement companyCityErrorMessage = wait.waitForElementPresent(companyCityErrorField);
        String companyCityErrorText = text.getElementText(companyCityErrorMessage);
        return companyCityErrorText;
    }

    /**
     * verifying company postal code error message
     * is displaying or not
     *
     * @return companyPostalCodeErrorText returns error message
     */
    public String verifyCompanyPostalCodeErrorMessage() {
        WebElement companyPostalCodeErrorMessage = wait.waitForElementPresent(companyPostalCodeErrorField);
        String companyPostalCodeErrorText = text.getElementText(companyPostalCodeErrorMessage);
        return companyPostalCodeErrorText;
    }

    /**
     * verifying country delivery term error message
     * is displaying or not
     *
     * @return countryDeliveryTermErrorText returns error message
     */
    public String verifyCountryDeliveryTermErrorMessage() {
        WebElement countryDeliveryTermErrorMessage = wait.waitForElementPresent(countryDeliveryTermErrorField);
        String countryDeliveryTermErrorText = text.getElementText(countryDeliveryTermErrorMessage);
        return countryDeliveryTermErrorText;
    }

    /**
     * verifying delivery term override items
     *
     * @return getDeliveryItem returns true or false
     */
    public boolean verifyDeliveryTermItems() {
        WebElement deliveryTermOverrideItems = wait.waitForElementPresent(deliveryItemTermOverride);
        boolean getDeliveryItem = deliveryTermOverrideItems.isDisplayed();
        return getDeliveryItem;
    }

    /**
     * verifying vertex company information tab
     * is displaying or not
     *
     * @return getCompanyInfoTab returns true or false
     */
    public boolean verifyVertexCompyInfoTab() {
        WebElement companyInfoTab = wait.waitForElementPresent(vertexCompanyInfoTab);
        boolean getCompanyInfoTab = companyInfoTab.isDisplayed();
        return getCompanyInfoTab;
    }

    /**
     * verifying vertex delivery terms tab
     * is displaying or not
     *
     * @return getDeliveryTermsTab returns true or false
     */
    public boolean verifyVertexDeliveryTermsTab() {
        WebElement deliveryTermsTab = wait.waitForElementPresent(vertexDeliveryTermsTab);
        boolean getDeliveryTermsTab = deliveryTermsTab.isDisplayed();
        return getDeliveryTermsTab;
    }

    /**
     * verifying vertex shipping Product Codes tab
     * is displaying or not
     *
     * @return getShippingProductCodesTab returns true or false
     */
    public boolean verifyVertexShippingProductCodesTab() {
        WebElement shippingProductCodesTab = wait.waitForElementPresent(vertexShippingProductCodesTab);
        boolean getShippingProductCodesTab = shippingProductCodesTab.isDisplayed();
        return getShippingProductCodesTab;
    }

    /**
     * verifying vertex Logging tab
     * is displaying or not
     *
     * @return getLoggingTab returns true or false
     */
    public boolean verifyVertexLoggingTab() {
        WebElement loggingTab = wait.waitForElementPresent(vertexLoggingTab);
        boolean getLoggingTab = loggingTab.isDisplayed();
        return getLoggingTab;
    }

    /**
     * verifying Rotation Action Field
     * is displaying or not
     *
     * @return getRotationAction returns true or false
     */
    public boolean verifyRotationActionField() {
        WebElement rotationAction = wait.waitForElementPresent(rotationActionFieldTab);
        boolean getRotationAction = rotationAction.isDisplayed();
        return getRotationAction;
    }

    /**
     * verifying Log Entry Life Time field
     * is displaying or not
     *
     * @return getLogEntryLifeTime returns true or false
     */
    public boolean verifyLogEntryLifeTimeField() {
        WebElement logEntryLifeTime = wait.waitForElementPresent(logEntryLifetimeFieldTab);
        boolean getLogEntryLifeTime = logEntryLifeTime.isDisplayed();
        return getLogEntryLifeTime;
    }

    /**
     * verifying Rotation Frequency field
     * is displaying or not
     *
     * @return getRotationFrequency returns true or false
     */
    public boolean verifyRotationFrequencyField() {
        WebElement rotationFrequency = wait.waitForElementPresent(rotationFrequencyFieldTab);
        boolean getRotationFrequency = rotationFrequency.isDisplayed();
        return getRotationFrequency;
    }

    /**
     * verifying Rotation Time field
     * is displaying or not
     *
     * @return getRotationTime returns true or false
     */
    public boolean verifyRotationTimeField() {
        WebElement rotationTime = wait.waitForElementPresent(rotationTimeFieldTab);
        boolean getRotationTime = rotationTime.isDisplayed();
        return getRotationTime;
    }

    /**
     * verifying Free Shipping field
     * is displaying or not
     *
     * @return getFreeShipping returns true or false
     */
    public boolean verifyFreeShippingField() {
        clickDeveloperSupportInformationTab();
        boolean getFreeShipping = element.isElementPresent(freeShippingField);
        return getFreeShipping;
    }

    /**
     * gets the different status of the Vertex API Status tab
     *
     * @return apiStatusText returns multiple api status text
     */
    public String getMultipleApiStatus() {
        clickConnectionSettingsTab();
        clickIntegrationSettingsTab();
        WebElement apiStatusField = wait.waitForElementPresent(multipleApiStatus);
        String apiStatusText = text.getElementText(apiStatusField);
        return apiStatusText;
    }

    /**
     * gets the selected dropdown field
     * in use vertex tax links field
     *
     * @return taxLinkDropDownText returns selected text in dropdown
     */
    public String getSelectedTextForVertexTaxLink() {
        WebElement selectedTaxLinkOption = wait.waitForElementPresent(useVertexTaxLinks);
        WebElement taxLinkDropDown = dropdown.getDropdownSelectedOption(selectedTaxLinkOption);
        String taxLinkDropDownText = text.getElementText(taxLinkDropDown);
        return taxLinkDropDownText;
    }

    /**
     * gets the selected dropdown field
     * in vertex tax calculation field
     *
     * @return taxCalculationDropDownText returns selected text in dropdown
     */
    public String getSelectedTextForVertexTaxCalculation() {
        WebElement selectedTaxCalculationOption = wait.waitForElementDisplayed(vertexTaxCalculation);
        WebElement taxLinkDropDown = dropdown.getDropdownSelectedOption(selectedTaxCalculationOption);
        String taxCalculationDropDownText = text.getElementText(taxLinkDropDown);
        return taxCalculationDropDownText;
    }

    /**
     * gets the selected dropdown Value of Order Status field
     *
     * @return selectInputOrderStatusText returns selected text in dropdown
     */
    public String getSelectedTextOrderStatus() {
        WebElement selectedOrderStatusOption = wait.waitForElementDisplayed(whenOrderStatusDropDown);
        WebElement OrderStatusDropDown = dropdown.getDropdownSelectedOption(selectedOrderStatusOption);
        String selectInputOrderStatusText = text.getElementText(OrderStatusDropDown);
        return selectInputOrderStatusText;
    }

    /**
     * gets the selected dropdown field of company country field
     *
     * @return companyCountryDropDownText returns selected text in dropdown
     */
    public String getSelectedTextForCompanyCountry() {
        WebElement selectedCompanyCountryOption = wait.waitForElementDisplayed(companyCountryField);
        WebElement companyCountryDropDown = dropdown.getDropdownSelectedOption(selectedCompanyCountryOption);
        String companyCountryDropDownText = text.getElementText(companyCountryDropDown);
        return companyCountryDropDownText;
    }

    /**
     * gets the selected dropdown field of company State field
     *
     * @return companyStateDropDownText returns selected text in dropdown
     */
    public String getSelectedTextForCompanyState() {
        WebElement selectedCompanyStateOption = wait.waitForElementDisplayed(companyStateFieldSelect);
        WebElement companyStateDropDown = dropdown.getDropdownSelectedOption(selectedCompanyStateOption);
        String companyStateDropDownText = text.getElementText(companyStateDropDown);
        return companyStateDropDownText;
    }

    /**
     * gets the selected dropdown field of global delivery term field
     *
     * @return globalDeliveryTermText returns selected text in dropdown
     */
    public String getSelectedTextForGlobalDeliveryTerm() {
        WebElement selectedGlobalDeliveryTermOption = wait.waitForElementDisplayed(globalDeliveryTermField);
        WebElement globalDeliveryTermDropDown = dropdown.getDropdownSelectedOption(selectedGlobalDeliveryTermOption);
        String globalDeliveryTermText = text.getElementText(globalDeliveryTermDropDown);
        return globalDeliveryTermText;
    }

    /**
     * gets the selected dropdown field of country delivery term field
     *
     * @return countryDeliveryTermText returns selected text in dropdown
     */
    public String getSelectedTextCountryDeliveryTerm(int rowNumber) {
        String country = String.format("//tbody/tr[" + rowNumber + "]/td/select[contains(@name, '[country_id]')]");
        By selectedCountryDeliveryTermField = By.xpath(country);
        WebElement selectedCountryDeliveryTermOption = wait.waitForElementDisplayed(selectedCountryDeliveryTermField);
        WebElement countryDeliveryTermDropDown = dropdown.getDropdownSelectedOption(selectedCountryDeliveryTermOption);
        String countryDeliveryTermText = text.getElementText(countryDeliveryTermDropDown);
        return countryDeliveryTermText;
    }

    /**
     * gets the selected dropdown field of delivery term field
     *
     * @return deliveryTermText returns selected text in dropdown
     */
    public String getSelectedTextDeliveryTerm(int rowNumber) {
        String delivery = String.format("//tbody/tr[" + rowNumber + "]/td/select[contains(@name, '[delivery_term]')]");
        By selectedDeliveryTermField = By.xpath(delivery);
        WebElement selectedDeliveryTermOption = wait.waitForElementDisplayed(selectedDeliveryTermField);
        WebElement deliveryTermDropDown = dropdown.getDropdownSelectedOption(selectedDeliveryTermOption);
        String deliveryTermText = text.getElementText(deliveryTermDropDown);
        return deliveryTermText;
    }

    /**
     * gets the selected dropdown field of Vertex Request Logging field
     *
     * @return vertexRequestLoggingText returns selected text in dropdown
     */
    public String getSelectedTextVertexRequestLoggingField() {
        WebElement selectedVertexRequestLoggingOption = wait.waitForElementDisplayed(vertexRequestLoggingField);
        WebElement vertexRequestLoggingDropDown = dropdown.getDropdownSelectedOption(selectedVertexRequestLoggingOption);
        String vertexRequestLoggingText = text.getElementText(vertexRequestLoggingDropDown);
        return vertexRequestLoggingText;
    }

    /**
     * gets the selected dropdown field of log Rotation field
     *
     * @return logRotationText returns selected text in dropdown
     */
    public String getSelectedTextLogRotationField() {
        WebElement selectedLogRotationOption = wait.waitForElementDisplayed(logRotationField);
        WebElement logRotationDropDown = dropdown.getDropdownSelectedOption(selectedLogRotationOption);
        String logRotationText = text.getElementText(logRotationDropDown);
        return logRotationText;
    }

    /**
     * gets the selected dropdown field of Rotation Action field
     *
     * @return rotationActionText returns selected text in dropdown
     */
    public String getSelectedTextRotationActionField() {
        WebElement selectedRotationActionOption = wait.waitForElementDisplayed(rotationActionField);
        WebElement rotationActionDropDown = dropdown.getDropdownSelectedOption(selectedRotationActionOption);
        String rotationActionText = text.getElementText(rotationActionDropDown);
        return rotationActionText;
    }

    /**
     * gets the selected dropdown field of Rotation Frequency field
     *
     * @return rotationFrequencyText returns selected text in dropdown
     */
    public String getSelectedTextRotationFrequencyField() {
        WebElement selectedRotationFrequencyOption = wait.waitForElementDisplayed(rotationFrequencyField);
        WebElement rotationFrequencyDropDown = dropdown.getDropdownSelectedOption(selectedRotationFrequencyOption);
        String rotationFrequencyText = text.getElementText(rotationFrequencyDropDown);
        return rotationFrequencyText;
    }

    /**
     * gets the selected dropdown field of Rotation Time Hour field
     *
     * @return rotationTimeHourText returns selected text in dropdown
     */
    public String getSelectedTextRotationTimeHourField() {
        WebElement selectedRotationTimeHourOption = wait.waitForElementPresent(rotationTimeFieldHour);
        WebElement rotationTimeHourDropDown = dropdown.getDropdownSelectedOption(selectedRotationTimeHourOption);
        String rotationTimeHourText = text.getElementText(rotationTimeHourDropDown);
        return rotationTimeHourText;
    }

    /**
     * gets the selected dropdown field of Rotation Time Minute field
     *
     * @return rotationTimeMinuteText returns selected text in dropdown
     */
    public String getSelectedTextRotationTimeMinuteField() {
        WebElement selectedRotationTimeMinuteOption = wait.waitForElementDisplayed(rotationTimeFieldMinute);
        WebElement rotationTimeMinuteDropDown = dropdown.getDropdownSelectedOption(selectedRotationTimeMinuteOption);
        String rotationTimeMinuteText = text.getElementText(rotationTimeMinuteDropDown);
        return rotationTimeMinuteText;
    }

    /**
     * gets the selected dropdown field of Rotation Time Second  field
     *
     * @return rotationTimeSecondText returns selected text in dropdown
     */
    public String getSelectedTextRotationTimeSecondField() {
        WebElement selectedRotationTimeSecondOption = wait.waitForElementDisplayed(rotationTimeFieldSecond);
        WebElement rotationTimeSecondDropDown = dropdown.getDropdownSelectedOption(selectedRotationTimeSecondOption);
        String rotationTimeSecondText = text.getElementText(rotationTimeSecondDropDown);
        return rotationTimeSecondText;
    }


    /**
     * verifying required field of calculation url api
     * is displaying or not
     *
     * @return calculationApiRequiredText returns required field text
     */
    public String verifyCalculationUrlRequiredField() {
        WebElement calculationApiUrlRequiredField = wait.waitForElementPresent(requiredFieldCalUrlApi);
        String calculationApiRequiredText = text.getElementText(calculationApiUrlRequiredField);
        return calculationApiRequiredText;
    }

    /**
     * verifying required field of address url api
     * is displaying or not
     *
     * @return addressApiRequiredText returns required field text
     */
    public String verifyAddressUrlRequiredField() {
        WebElement addressApiUrlRequiredField = wait.waitForElementPresent(requiredFieldAddressUrlApi);
        String addressApiRequiredText = text.getElementText(addressApiUrlRequiredField);
        return addressApiRequiredText;
    }

    /**
     * verifying required field of invoice when order status
     * is displaying or not
     *
     * @return orderStatusRequiredText returns required field text
     */
    public String verifyRequiredFieldOrderStatus() {
        WebElement invoiceOrderStatusRequiredField = wait.waitForElementPresent(requiredFieldInvoiceOrderStatus);
        String orderStatusRequiredText = text.getElementText(invoiceOrderStatusRequiredField);
        return orderStatusRequiredText;
    }

    /**
     * verifying required field of company state
     * is displaying or not
     *
     * @return companyStateRequiredText returns required field text
     */
    public String verifyCompanyStateRequiredField() {
        WebElement companyStateRequiredField = wait.waitForElementPresent(requiredFieldCompanyState);
        String companyStateRequiredText = text.getElementText(companyStateRequiredField);
        return companyStateRequiredText;
    }

    /**
     * clicks on save configuration button
     */
    public void saveConfig() {
        wait.waitForElementPresent(saveID);
        click.clickElement(saveID);
        jsWaiter.waitForLoadAll();
    }

    /**
     * getting the value of a text field
     *
     * @param locator Locator value
     * @return actualFieldTextValue returns text or value in the text field
     */
    public String getFieldText(By locator) {
        String actualFieldTextValue = text.retrieveTextFieldContents(locator);
        return actualFieldTextValue;
    }

    /**
     * reset the configuration settings into default template
     */
    public void resetConfigSettingToDefault() {
        M2AdminConfigPage configPage = new M2AdminConfigPage(driver);
        M2AdminSalesShippingMethodsConfigPage ShippingMethods = new M2AdminSalesShippingMethodsConfigPage(driver);
        refreshPage();
        clickConnectionSettingsTab();
        clickIntegrationSettingsTab();
        wait.waitForElementPresent(useVertexTaxLinks);
        if (getSelectedTextForVertexTaxLink().equalsIgnoreCase("Disable")) {
            changeUseVertexTaxLinksField("Enable");
            clickIntegrationSettingsTab();
        }
        if (getSelectedTextForVertexTaxCalculation().equalsIgnoreCase("Disable")) {
            changeVertexTaxCalculationField("Enable");
        }
        if (verifyIntegrationSettingsTab() && verifyConnectionSettingsTab()) {
            clickConnectionSettingsTab();
            clickIntegrationSettingsTab();
            if (!(getMultipleApiStatus().equalsIgnoreCase("VALID"))) {
                changeCalculationApiUrl(validCalculationApiUrl);
                changeAddressApiUrl(validAddressApiUrl);
                changeTrustedIdField(validTrustedId);
                changeCompanyCodeField(companyCode);
                changeCompanyStreetAddressOneField(validCompanyStreetAddressValue);
                changeCompanyCityField(validCompanyCityValue);
                changeCompanyCountryField(validCompanyCountryValue);
                changeCompanyStateSelectField(validCompanyStateValue);
                changeCompanyPostalCodeField(validCompanyPostalCode);
                changeDisplayProductPricesInCatalogField("Excluding Tax");
                checkCheckBoxInDisplayProductField(true);
            }
        }
        if (verifyWhenOrderStatusField()) {
            changeSendInvoiceToVertexField(whenInvoiceCreated);
        }
        if (!(getSelectedTextForGlobalDeliveryTerm().equalsIgnoreCase(validGlobalDeliveryTermText))) {
            changeGlobalDeliveryTermField(validGlobalDeliveryTermText);
        }
        if (verifyDeliveryTermItems()) {
            deleteDeliveryTermOverrideItems();
        }
        if (getSelectedTextVertexRequestLoggingField().equalsIgnoreCase("Disable")) {
            changeVertexRequestLoggingField("Enable");
        }
        if (getSelectedTextLogRotationField().equalsIgnoreCase("Disable")) {
            changelogRotationField("Enable");
        }
        if (getSelectedTextRotationActionField().equalsIgnoreCase("Export to file and delete")) {
            changeRotationActionField("Delete");
        }
        if (getFieldText(logEntryLifeTimeField).equalsIgnoreCase("0")) {
            changeLogEntryLifetimeField("7");
        }
        if (getSelectedTextRotationFrequencyField().equalsIgnoreCase("Monthly")) {
            changeRotationFrequencyField("Weekly");
        }
        if (getSelectedTextRotationTimeHourField().equalsIgnoreCase("23")) {
            changeRotationTimeField("00", "00", "00");
        }
        saveConfig();
        if (!(verifyFreeShippingField())) {
            configPage.clickShippingMethodTab();
            ShippingMethods.changeFreeShippingEnabledField("Yes");
            ShippingMethods.saveConfig();
        }
    }

    /**
     * Get all Tax enabled European countries names in the form of List from the UI.
     *
     * @return all EU Tax Enabled countries names
     */
    public List<String> getAllEUCountriesName() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForAllElementsPresent(By.xpath(allTaxEnabledEUCountries));
        List<String> countries = new ArrayList<>();
        int size = element.getWebElements(By.xpath(allTaxEnabledEUCountries)).size();
        for (int i = 1; i <= size; i++) {
            countries.add(text.getElementText(By.xpath(allTaxEnabledEUCountries + "[" + i + "]")));
        }
        return countries;
    }
}