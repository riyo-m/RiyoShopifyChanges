package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesDocumentBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents Vertex Admin Page
 *
 * @author Shruti
 */
public class SalesVertexAdminPage extends SalesDocumentBasePage {

    protected By SETTINGS_BUTTON = By.cssSelector("[aria-label='Settings']");
    protected By ABOUT_BUTTON = By.cssSelector("[aria-label='About']");

    protected By SERVER_VERSION = By.xpath("//p[contains(text(), 'Server version')]");
    protected By CLIENT_VERSION = By.xpath("//p[contains(text(), 'Client version')]");

    protected By ADMIN_TAB = By.xpath("//li[@title='Vertex Admin']");
    protected By ADMIN_SETTINGS_LOC = By.xpath("//a[@aria-label='Vertex Admin']");
    protected By COMPANY_CODE_INPUT = By.xpath("//input[@aria-label='Company Code']");
    protected By TRUSTED_ID_INPUT = By.xpath("//input[@aria-label='Trusted ID']");
    protected By TAX_CALCULATION_URL_INPUT = By.xpath("//input[@aria-label='Tax Calculation Server Address']");
    protected By ADDRESS_VALIDATION_URL_INPUT = By.xpath("//input[@aria-label='Address Validation Server Address']");

    protected By XML_LOGS_TAB = By.xpath("//li[@title='Vertex XML Logs']");
    protected By XML_MESSAGE_TAB = By.xpath("//li[@title='XML Message']");
    protected By XML_MESSAGE = By.xpath("//textarea[@aria-label='XML Message']");

    protected By DIAGNOSTICS_TAB = By.xpath("//li[@title='Vertex Diagnostics']");

    protected By FLEX_FIELDS_TAB = By.xpath("//li[@title='Flex Fields']");
    protected By FIELD_SOURCE_DROPDOWN = By.cssSelector("[aria-label='Flex Field Source']");
    protected By FIELD_TYPE_DROPDOWN = By.cssSelector("[aria-label='Flex Field Type']");
    protected By FIELD_ID_INPUT = By.xpath("//input[@aria-label='Flex Field ID']");
    protected By FIELD_VALUE_INPUT = By.xpath("//input[@aria-label='Flex Field Value, Lookup']");

    public SalesVertexAdminPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to home page of Sales Hub from Vertex Admin page
     * @return SalesHomePage
     */
    public SalesHomePage navigateToSalesHomePage() {
        navigateToApp("Vertex", "Sales Hub");

        SalesHomePage salesHomePage = new SalesHomePage(driver);

        return salesHomePage;
    }

    /**
     * Go to section with XML logs
     */
    public void navigateToXMLLogs() {
        wait.waitForElementDisplayed(XML_LOGS_TAB);
        click.clickElementIgnoreExceptionAndRetry(XML_LOGS_TAB);

        waitForLoadingScreen();
    }

    /**
     * Go to section with Vertex settings
     */
    public void navigateToVertexSettings() {
        wait.waitForElementDisplayed(ADMIN_TAB);
        click.clickElementIgnoreExceptionAndRetry(ADMIN_TAB);

        wait.waitForElementDisplayed(ADMIN_SETTINGS_LOC);
        click.clickElementIgnoreExceptionAndRetry(ADMIN_SETTINGS_LOC);

        waitForPageLoad();
    }

    /**
     * Go to section with Flex Fields
     */
    public void navigateToFlexFields() {
        wait.waitForElementDisplayed(FLEX_FIELDS_TAB);
        click.clickElementIgnoreExceptionAndRetry(FLEX_FIELDS_TAB);

        waitForPageLoad();
    }

    /**
     * Set Trusted ID for Vertex OSeries
     * @param id
     */
    public void setTrustedID(String id) {
        wait.waitForElementDisplayed(TRUSTED_ID_INPUT);
        text.selectAllAndInputText(TRUSTED_ID_INPUT, id);
    }

    /**
     * Set Tax Calculation URL for Vertex OSeries
     * @param url
     */
    public void setTaxCalculationURL(String url) {
        wait.waitForElementDisplayed(TAX_CALCULATION_URL_INPUT);
        text.selectAllAndInputText(TAX_CALCULATION_URL_INPUT, url);
    }

    /**
     * Set Address Validation URL for Vertex OSeries
     * @param url
     */
    public void setAddressValidationURL(String url) {
        wait.waitForElementDisplayed(ADDRESS_VALIDATION_URL_INPUT);
        text.selectAllAndInputText(ADDRESS_VALIDATION_URL_INPUT, url);
    }


    /**
     * Click on dropdown of 'Name' and set filter to 'Contains' with specified search (docType)
     * @param docType
     */
    public void filterLogs(String docType) {
        waitForLoadingScreen();

        wait.waitForElementDisplayed(NAME_DROPDOWN);
        click.clickElementCarefully(NAME_DROPDOWN);

        wait.waitForElementDisplayed(FILTER_BUTTON);
        click.clickElementCarefully(FILTER_BUTTON);

        wait.waitForElementDisplayed(FILTER_OPERATOR);
        click.clickElementCarefully(FILTER_OPERATOR);

        By containsOptionLoc = By.xpath("//span[contains(@class, 'Dropdown-optionText') and text()='Contains']");
        click.clickElementCarefully(containsOptionLoc);

        click.clickElementCarefully(FILTER_INPUT);
        text.enterText(FILTER_INPUT, docType);

        waitForPageLoad();

        click.clickElementCarefully(APPLY_BUTTON);
    }

    /**
     * Click on dropdown of 'Created' to sort from 'New to Old'
     */
    public void sortLogs() {
        wait.waitForElementDisplayed(CREATED_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(CREATED_DROPDOWN);

        wait.waitForElementDisplayed(SORT_NEWER);
        click.clickElementIgnoreExceptionAndRetry(SORT_NEWER);
    }

    /**
     * Open first log wih specified docID in XML table and return the xml
     * @param docID
     * @return xml
     */
    public String getFirstXMLLog(String docID) {
        waitForLoadingScreen();

        sortLogs();

        waitForPageLoad();

        By docLoc = By.xpath(String.format("//a[contains(@aria-label, '%s')]", docID));
        click.clickElementCarefully(docLoc);

        waitForPageLoad();

        wait.waitForElementDisplayed(XML_MESSAGE_TAB);
        click.clickElementCarefully(XML_MESSAGE_TAB);

        waitForPageLoad();

        wait.waitForElementDisplayed(XML_MESSAGE);
        String xml = text.getElementText(XML_MESSAGE);
        return xml;
    }

    /**
     * Returns count of document
     * @param docID
     * @param expectedCount
     * @return boolean
     */
    public int getDocumentCount(String docID, int expectedCount) {
        By docLoc = By.xpath(String.format("//a[contains(@aria-label, '%s')]", docID));

        List<WebElement> docs = wait.waitForAllElementsPresent(docLoc);

        int docCount = docs.size();

        return docCount;
    }

    /**
     * Creates a new flex field with given parameters
     * @param source Flex Field Source
     * @param type Flex Field Type
     * @param id Flex Field ID
     * @param value Flex FIeld Value
     */
    public void createFlexField(String source, String type, String id, String value) {
        WebElement sourceDropdown = wait.waitForElementDisplayed(FIELD_SOURCE_DROPDOWN);
        dropdown.selectDropdownByValue(sourceDropdown, source);

        WebElement typeDropdown = wait.waitForElementDisplayed(FIELD_TYPE_DROPDOWN);
        dropdown.selectDropdownByValue(typeDropdown, type);

        WebElement idInput = wait.waitForElementDisplayed(FIELD_ID_INPUT);
        text.enterText(idInput, id);

        WebElement valueInput = wait.waitForElementDisplayed(FIELD_VALUE_INPUT);
        action.click(valueInput).perform();

        By dropdownEl = By.xpath(String.format("//span[text()='%s']", value));
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }
    /**
     * Clicks on Settings in top right
     */
    public void clickSettings() {
        waitForLoadingScreen();
        wait.waitForElementDisplayed(SETTINGS_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SETTINGS_BUTTON);
        waitForPageLoad();
    }

    /**
     * Clicks on About under Settings
     */
    public void clickAbout() {
        waitForLoadingScreen();
        wait.waitForElementDisplayed(ABOUT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(ABOUT_BUTTON);
        waitForPageLoad();
    }

    /**
     * Returns the version of Vertex Server
     * @return serverVersion
     */
    public String getVertexServerVersion() {
        WebElement versionField = wait.waitForElementDisplayed(SERVER_VERSION);
        String serverVersion = removeTextBeforeColon(versionField.getText());
        return serverVersion;
    }

    /**
     * Returns the version of Vertex Client
     * @return serverVersion
     */
    public String getVertexClientVersion() {
        WebElement versionField = wait.waitForElementDisplayed(CLIENT_VERSION);
        String clientVersion = removeTextBeforeColon(versionField.getText());
        return clientVersion;
    }



}
