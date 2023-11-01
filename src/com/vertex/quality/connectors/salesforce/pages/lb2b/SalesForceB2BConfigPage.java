package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMConfigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common functions for anything related to Salesforce Lightning Vertex Config Page.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BConfigPage extends SalesForceLB2BPostLogInPage {
    // Configuration Tab Locators
    protected final By pageBodyIframe = By.xpath(".//iframe");

    protected By TAXPAYER_CODE = By.xpath(".//*[@data-id='companyName']//label[contains(text(), 'Default Taxpayer Code')]/following-sibling::div/input");
    protected By SEVERITY_DROPDOWN = By.xpath(".//button[@name= 'Severity']");
    protected By MAX_LOG_ROWS_INPUT = By.xpath(".//*[@data-id='maxlogrows']//label[contains(text(), 'Max rows to log')]/following-sibling::div/input");
    protected By ALLOW_ADDRESS_VALIDATION_CHECKBOX = By.id("allowValidateAddr");
    protected By BASIC_AUTH_CHECKBOX = By.xpath(".//span[text()='Enable Basic Authentication']");

    protected By CONFIGURATION_MESSAGE = By.xpath(".//*[contains(@id, 'configurationMessageDiv')]");
    protected By AREA_LOOKUP_MESSAGE = By.xpath(".//*[contains(@id, 'areaLookupMessage')]");
    protected By TAX_CALC_MESSAGE = By.xpath(".//*[contains(@id, 'taxCalcMessage')]");

    protected By TAX_AREA_LOOKUP_ENDPOINT_URL = By.xpath(".//*[@id='taxAreaURL' or contains(@placeholder,'TaxAreas')]");
    protected By TAX_CALCULATION_LOOKUP_ENDPOINT_URL = By.xpath(
            ".//*[@id='taxCalcURL' or contains(@placeholder,'CalculateTax')]");

    protected By USERNAME_INPUT = By.xpath(".//*[@id='Username' or contains(@placeholder,'BobSmith')]");
    protected By PASSWORD_INPUT = By.xpath(".//*[@id='Password' or @type='password']");
    protected By TRUSTED_ID_INPUT = By.xpath(".//*[@id='TrustedId' or @id='input-27']");

    protected By SAVE_VALIDATE_BUTTON = By.xpath("//button[contains(text(),'Validate Credentials and Save')]");

    protected By CONFIG_MSG_CLOSE = By.cssSelector("#configurationMessageDiv button");

    protected By LOOKUP_MSG_CLOSE = By.cssSelector("#areaLookupMessage button");
    protected By TAX_MSG_CLOSE = By.cssSelector("#taxCalcMessage button");

    // Logs Tab Locators

    protected By LOG_FILE_OLD = By.xpath(".//*[@id='table-container-custom']/div/table/tbody/tr/th/div/a");
    protected By TRANSACTION_ID_LINK_OLD = By.xpath("//td[@data-label='Transaction ID']//a");
    protected By LOG_NAME_OLD = By.id("logNameModal");
    protected By END_POINT_OLD = By.id("endpointModal");
    protected By RESPONSE_STATUS_OLD = By.id("responseStatusModal");
    protected By RESPONSE_STATUS_CODE_OLD = By.id("responseStatusCodeModal");
    protected By LOG_DETAILS_OLD = By.id("detailsModal");
    protected By LOG_REQUEST_OLD = By.id("requestModal");
    protected By LOG_RESPONSE_OLD = By.id("responseModal");

    protected By LOG_REFRESH_BUTTON = By.xpath("//button[@title='Refresh']");
    protected By LOG_DELETE_OLD_LOGS_BUTTON = By.xpath("//button[@title='Delete Old Logs']");
    protected By LOG_DELETE_ALL_LOGS_BUTTON = By.xpath("//button[@title='Delete All Logs']");

    protected By LOG_FILE = By.xpath("//tr[@data-row-key-value='row-0']//lightning-formatted-url/a");
    protected By TRANSACTION_ID_LINK = By.xpath(
            "//tr[@data-row-key-value='row-0']/td[@data-label='Transaction Id']//lightning-formatted-url/a");
    protected By LOG_NAME = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Vertex Log Number']/../..//lightning-formatted-text");
    protected By END_POINT = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Endpoint']/../..//lightning-formatted-text");
    protected By RESPONSE_STATUS = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Response Status']/../..//lightning-formatted-text");
    protected By RESPONSE_STATUS_CODE = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Response Status Code']/../..//lightning-formatted-number");
    protected By LOG_DETAILS = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Details']/../..//lightning-formatted-text");
    protected By LOG_REQUEST = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Request']/../..//lightning-formatted-text");
    protected By LOG_RESPONSE = By.xpath(
            ".//span[contains(@class, 'field-label') and text()='Response']/../..//lightning-formatted-text");
    protected By CLEAR_TAX_DETAIL_BUTTON = By.xpath("//button[contains(text(),'Clear Tax Detail Cache')]");

    protected By LOG_CLOSE_BUTTON = By.xpath("//button[contains(text(),'Close')]");

    public SalesForceB2BConfigPage(WebDriver driver) {
        super(driver);
    }

    SalesForceLB2BPostLogInPage postLogInPage = new SalesForceLB2BPostLogInPage(driver);

    /**
     * To enter Tax Area Lookup Url
     *
     * @param taxarealookupurl
     */
    public void setTaxAreaLookUpUrl(String taxarealookupurl) {
        text.enterText(TAX_AREA_LOOKUP_ENDPOINT_URL, taxarealookupurl);
    }

    /**
     * To enter Tax calculation Url
     *
     * @param taxcalculationurl
     */
    public void setTaxCalculationUrl(String taxcalculationurl) {
        text.enterText(TAX_CALCULATION_LOOKUP_ENDPOINT_URL, taxcalculationurl);
    }

    /**
     * To enter Configuration Username
     *
     * @param username
     */
    public void setUsername(String username) {
        if(username.equals("") && BASIC_AUTH_ENABLED){
            username = "TST_user1";
        }
        text.enterText(USERNAME_INPUT, username);
    }

    /**
     * To enter Configuration Password
     *
     * @param password
     */
    public void setPassword(String password) {
        if(password.equals("") && BASIC_AUTH_ENABLED){
            password = "TSTuser1";
        }
        text.enterText(PASSWORD_INPUT, password);
    }

    /**
     * To enter TrustedId
     *
     * @param trustedid
     */
    public void setTrustedId(String trustedid) {
        if(!BASIC_AUTH_ENABLED)
        {
            text.enterText(TRUSTED_ID_INPUT, trustedid);
        }
    }

    /**
     * To set Allow Address Validation Check box
     */
    public void checkAllowAddressValidation(Boolean addressValidation) {
        waitForSalesForceLoaded(10000);
        if (addressValidation) {
            setConfigTabCheckbox("Allow Address Validation", Boolean.TRUE);
        } else {
            setConfigTabCheckbox("Allow Address Validation", Boolean.FALSE);
        }
    }

    /**
     * To enter TaxPayercode
     *
     * @param taxpayercode
     */
    public void setTaxPayerCode(String taxpayercode) {
        text.enterText(TAXPAYER_CODE, taxpayercode);
    }

    /**
     * To set Allow Process build request Check box
     */
    public void checkAllowRequests() {
        setConfigTabCheckbox("Allow Process Builder and Flow Requests", Boolean.TRUE);
    }

    /**
     * To select Severity VAlue
     *
     * @param severityValue
     */
    public void setSeverity(String severityValue) {
        try {
            click.clickElement(SEVERITY_DROPDOWN);
            String xpathSelector = String.format(".//*[@data-value='%s']", severityValue);
            click.clickElement(By.xpath(xpathSelector));
        } catch (Exception ex) {
            dropdown.selectDropdownByDisplayName(SEVERITY_DROPDOWN, severityValue);
        }
    }

    /**
     * To set Max Log Rows
     *
     * @param maxRows
     */
    public void setMaxRowsToLog(String maxRows) {
        text.enterText(MAX_LOG_ROWS_INPUT, maxRows);
    }

    /**
     * Set Enable Basic Authentication checkbox
     */
    public void setEnableBasicAuthCheckbox(boolean enableBasicAuth)
    {
        if(enableBasicAuth){
            setConfigTabCheckbox("Enable Basic Authentication", Boolean.TRUE);
        }
        else{
            setConfigTabCheckbox("Enable Basic Authentication", Boolean.FALSE);
        }
    }

    /**
     * To set Asynchronous Logging Check box
     */
    public void checkAsyncLogging() {
        setConfigTabCheckbox("Asynchronous Logging (@future)", Boolean.TRUE);
    }

    /**
     * Click on Validate Credentials and Save button
     */
    public void clickSaveAndValidateButton() {
        VertexLogger.log("Clicking SaveAndValidate button...", VertexLogLevel.TRACE, SalesForceB2BConfigPage.class);
        click.clickElement(SAVE_VALIDATE_BUTTON);
        wait.waitForElementDisplayed(AREA_LOOKUP_MESSAGE);
    }

    /**
     * To get Area Look up Success Message
     *
     * @return Area Look up Success Message
     */
    public String getAreaLookUpMessage() {
        wait.waitForElementDisplayed(AREA_LOOKUP_MESSAGE);
        String authMessage = text
                .getElementText(AREA_LOOKUP_MESSAGE)
                .replaceAll("\n", "");
        return authMessage;
    }

    /**
     * To get Tax Calculation Success Message
     *
     * @return Tax Calculation Success Message
     */
    public String getTaxCalcMessage() {
        wait.waitForElementDisplayed(TAX_CALC_MESSAGE);
        String authMessage = text
                .getElementText(TAX_CALC_MESSAGE)
                .replaceAll("\n", "");
        return authMessage;
    }

    /**
     * To get Configuration Success Message
     *
     * @return Configuration Success Message
     */
    public String getConfigurationMessage() {
        wait.waitForElementDisplayed(CONFIGURATION_MESSAGE);
        String configMessage = text
                .getElementText(CONFIGURATION_MESSAGE)
                .replaceAll("\n", "");
        return configMessage;
    }

    /**
     * This method is to Set Configuration settings and validate success messages
     *
     * @param taxAreaLookupUrl
     * @param taxCalculationUrl
     * @param configUsername
     * @param configPassword
     * @param trustedId
     * @param addressValidation
     * @param taxPayerCode
     * @param severityValue
     * @param maxRows
     */
    public void setAndValidateConfigSettings(String taxAreaLookupUrl, String taxCalculationUrl, String configUsername,
                                             String configPassword, String trustedId, Boolean addressValidation, String taxPayerCode, String severityValue,
                                             String maxRows) {
        setAndValidateConfigSettings(taxAreaLookupUrl, taxCalculationUrl, configUsername, configPassword,
                trustedId, addressValidation, BASIC_AUTH_ENABLED, taxPayerCode, severityValue, maxRows );
    }

    /**
     * This method is to Set Configuration settings and validate success messages
     *
     * @param taxAreaLookupUrl
     * @param taxCalculationUrl
     * @param configUsername
     * @param configPassword
     * @param trustedId
     * @param addressValidation
     * @param enableBasicAuth
     * @param taxPayerCode
     * @param severityValue
     * @param maxRows
     */
    public void setAndValidateConfigSettings(String taxAreaLookupUrl, String taxCalculationUrl, String configUsername,
                                             String configPassword, String trustedId, Boolean addressValidation, Boolean enableBasicAuth, String taxPayerCode, String severityValue,
                                             String maxRows) {
        postLogInPage.clickVertexPageTab(NavigateMenu.Vertex.CONFIGURATION_TAB.text);
        waitForSalesForceLoaded();
        waitForPageLoad();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.switchToFrame(pageBodyIframe);
        waitForPageLoad();

        setTaxAreaLookUpUrl(taxAreaLookupUrl);
        setTaxCalculationUrl(taxCalculationUrl);
        setUsername(configUsername);
        setPassword(configPassword);
        setTrustedId(trustedId);
        checkAllowAddressValidation(addressValidation);
        if (element.isElementPresent(BASIC_AUTH_CHECKBOX)){
            setEnableBasicAuthCheckbox(enableBasicAuth);
        }
        setTaxPayerCode(taxPayerCode);
        checkAllowRequests();
        setSeverity(severityValue);
        setMaxRowsToLog(maxRows);
        checkAsyncLogging();
        clickSaveAndValidateButton();
    }

    /**
     * Close Configuration Success Message section
     */
    public void closeConfigMsg() {
        click.clickElement(CONFIG_MSG_CLOSE);
    }

    /**
     * Close Area Lookup Success Message section
     */
    public void closeLookUpMsg() {
        click.clickElement(LOOKUP_MSG_CLOSE);
    }

    /**
     * Close Tax Calculation Success Message section
     */
    public void closeTaxCalcMsg() {
        click.clickElement(TAX_MSG_CLOSE);
    }

    // This is not regular check box, It is span control.
    // The DOM is not supporting the existing check box enable and disable
    // methods.So It is generalized in this way and it is limited to Configuration
    // tab check boxes
    private void setConfigTabCheckbox(String checkbox_name, boolean toEnable) {
        waitForSalesForceLoaded();
        window.switchToFrame(pageBodyIframe);
        String labelLocator = String.format("//*[contains(@class,'slds-checkbox')]/.//*[contains(text(),'%s')]",
                checkbox_name);
        By checkbox_span_tuple = By.xpath(labelLocator + "/../*[contains(@class,'faux')]");
        By checkbox_input_tuple = By.xpath(labelLocator + "/../..//*[@type='checkbox']");

        String is_checked = attribute.getElementAttribute(checkbox_input_tuple, "checked");

        if (toEnable) {
            if (is_checked == null) {
                click.clickElement(checkbox_span_tuple);
            } else {
                VertexLogger.log(checkbox_name + " check box is already checked", SalesForceCRMConfigPage.class);
            }
        } else if (!(is_checked == null)) {
            click.clickElement(checkbox_span_tuple);
        }
    }

    /**
     * get if log file exists
     */
    public Boolean logFileExists() {
        By logElement = LOG_FILE;
        return element.isElementPresent(logElement);
    }

    /**
     * Click on Logs Name link to validate the request and response
     */
    public void clickLogNameLink() {
        window.switchToDefaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath(".//iframe")));

        Boolean newWindow = true;
        By logElement = LOG_FILE;
        wait.waitForElementDisplayed(logElement);
        if (!element.isElementPresent(logElement)) {
            logElement = LOG_FILE_OLD;
            newWindow = false;
        }
        wait.waitForElementDisplayed(logElement);
        click.javascriptClick(logElement);

        if (newWindow) {
            window.switchToWindowTextInTitle("VL");
        }
    }

    /**
     * Click on Logs Name link to validate the request and response
     */
    public void clickTransactionIdLink() {
        By logElement = TRANSACTION_ID_LINK;
        if (!element.isElementPresent(logElement)) {
            logElement = TRANSACTION_ID_LINK_OLD;
        }
        wait.waitForElementDisplayed(logElement);
        click.javascriptClick(logElement);
        window.switchToWindowTextInTitle(TestInput.OPP_WINDOW_TITLE);
        waitForPageLoad();
    }

    /**
     * To get Logname
     *
     * @return Logname
     */
    public String getLogName() {
        By logDetail = LOG_NAME;
        wait.waitForElementDisplayed(logDetail);
        if (!element.isElementPresent(logDetail)) {
            logDetail = LOG_NAME_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String logName = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return logName;
    }

    /**
     * To get Endpoint Value
     *
     * @return Endpoint
     */
    /**
     * To get Endpoint Value
     *
     * @return Endpoint
     */
    public String getEndPoint() {
        By logDetail = END_POINT;
        if (!element.isElementPresent(logDetail)) {
            logDetail = END_POINT_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String endPoint = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return endPoint;
    }

    /**
     * To get ResponseStatus Value
     *
     * @return ResponseStatus
     */
    public String getResponseStatus() {
        By logDetail = RESPONSE_STATUS;
        if (!element.isElementPresent(logDetail)) {
            logDetail = RESPONSE_STATUS_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String responseStatus = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return responseStatus;
    }

    /**
     * To get ResponseStatusCode Value
     *
     * @return ResponseStatusCode
     */
    public String getResponseStatusCode() {
        By logDetail = RESPONSE_STATUS_CODE;
        if (!element.isElementPresent(logDetail)) {
            logDetail = RESPONSE_STATUS_CODE_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String responseStatusCode = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return responseStatusCode;
    }

    /**
     * To get Details section of the Log
     *
     * @return Details
     */
    public String getLogDetailsSection() {
        By logDetail = LOG_DETAILS;
        wait.waitForElementDisplayed(logDetail);
        if (!element.isElementPresent(logDetail)) {
            logDetail = LOG_DETAILS_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String details = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return details;
    }

    /**
     * To get Request value of Log
     *
     * @return
     */
    public String getLogRequestValue() {
        By logDetail = LOG_REQUEST;
        if (!element.isElementPresent(logDetail)) {
            logDetail = LOG_REQUEST_OLD;
        }
        wait.waitForElementDisplayed(logDetail);

        String requestDetails = text
                .getElementText(logDetail)
                .replaceAll("\n", "");
        return requestDetails;
    }

    /**
     * To get Response value of Log
     *
     * @return
     */
    public String getLogResponseValue() {
        Boolean newUI = true;
        By logDetail = LOG_RESPONSE;
        if (!element.isElementPresent(logDetail)) {
            logDetail = LOG_RESPONSE_OLD;
            newUI = false;
        }

        wait.waitForElementDisplayed(logDetail);

        String responseDetails = text.getElementText(logDetail);
        if (newUI) {
            responseDetails = responseDetails.replace("<br>", " ");
            responseDetails = responseDetails.replaceAll("\\n", " ");
            responseDetails = responseDetails.replaceAll("\\r", " ");
        }
        responseDetails = stripCDATA(responseDetails);
        return responseDetails;
    }

    /**
     * To Close Log pop up window
     */
    public void closeLogDialog() {
        if (element.isElementPresent(LOG_CLOSE_BUTTON)) {
            wait.waitForElementDisplayed(LOG_CLOSE_BUTTON, DEFAULT_TIMEOUT);
            click.javascriptClick(LOG_CLOSE_BUTTON);
            wait.waitForElementNotDisplayed(LOG_CLOSE_BUTTON, DEFAULT_TIMEOUT);
        } else {
            driver.close();
            window.switchToWindow();
        }
        waitForPageLoad();
    }

    /**
     * Click refresh button on Logs Tab
     */
    public void clickRefreshButton() {
        wait.waitForElementDisplayed(LOG_REFRESH_BUTTON);
        click.clickElement(LOG_REFRESH_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click Delete Old Logs button on Logs Tab
     */
    public void clickDeleteOldLogsButton() {
        wait.waitForElementDisplayed(LOG_DELETE_OLD_LOGS_BUTTON);
        click.clickElement(LOG_DELETE_OLD_LOGS_BUTTON);

        alert.acceptAlert(DEFAULT_TIMEOUT);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Click Delete All Logs button on Logs Tab
     */
    public void clickDeleteAllLogsButton() {
        wait.waitForElementDisplayed(LOG_DELETE_ALL_LOGS_BUTTON);
        click.clickElement(LOG_DELETE_ALL_LOGS_BUTTON);

        alert.acceptAlert(DEFAULT_TIMEOUT);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Click on Clear Tax Detail button
     */
    public void clickClearTaxDetailButton() {
        VertexLogger.log("Clicking ClearTaxDetail button...", VertexLogLevel.TRACE, SalesForceCRMConfigPage.class);
        click.clickElement(CLEAR_TAX_DETAIL_BUTTON);
        alert.acceptAlert(DEFAULT_TIMEOUT);
        waitForPageLoad();
    }

    public static String stripCDATA(String str) {
        Pattern p = Pattern.compile("<!\\[CDATA\\[(.*?)\\]\\]>");
        Matcher m = p.matcher(str);
        while(m.find()) {
            str = str.replace(m.group(), m.group(1));
        }
        return str;
    }
}