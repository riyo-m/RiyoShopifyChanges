package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver CMS Edit O-Series page - contains all re-usable methods specific to this page.
 *
 * @author Shivam.Soni
 */
public class EpiOSeriesPage extends EpiServer324Page {

    protected String VERTEX_O_SERIES_TABS = ".//a[text()='<<text_replace>>']";
    protected By CORE_VERSION_NUMBER = By.xpath(".//label[@for='CoreVersionNumber']");
    protected By ADAPTER_VERSION_NUMBER = By.xpath(".//label[@for='AdapterVersionNumber']");
    protected By COMPANY_NAME_TEXT_BOX = By.xpath(".//input[@name='SettingsModel.CompanyName']");
    protected By GENERATE_INVOICE_CHECKBOX = By.id("VertexInvoice_AutomaticInvoicing");
    protected By SAVE_BUTTON = By.xpath(".//input[@value='Save']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiOSeriesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects or go to the tab from Vertex O-Series page tabs option
     *
     * @param tabName name of the Tab
     */
    public void goToTab(String tabName) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(VERTEX_O_SERIES_TABS.replace("<<text_replace>>", tabName))));
        VertexLogger.log("Navigated to Vertex O Series -> " + tabName);
    }


    /**
     * @param versionType type of version
     * @return Corresponding Connector's "Core/Adapter" Version
     */
    public String getVersion(String versionType) {
        String version = null;
        if (versionType.equalsIgnoreCase("Core Version")) {
            wait.waitForElementDisplayed(CORE_VERSION_NUMBER);
            version = text.getElementText(CORE_VERSION_NUMBER);
            VertexLogger.log("Core Version is: " + version);
        } else if (versionType.equalsIgnoreCase("Adapter Version")) {
            wait.waitForElementDisplayed(ADAPTER_VERSION_NUMBER);
            version = text.getElementText(ADAPTER_VERSION_NUMBER);
            VertexLogger.log("Adapter Version is: " + version);
        } else {
            VertexLogger.log(String.format("Provided Version Type %s is not valid", versionType));
        }
        return version;
    }

    /**
     * Changes the Company Name
     *
     * @param companyName Company which is to be set
     */
    public void changeCompanyName(String companyName) {
        waitForSpinnerToBeDisappeared();
        WebElement company = wait.waitForElementPresent(COMPANY_NAME_TEXT_BOX);
        text.enterText(company, companyName);
        text.pressTab(company);
        VertexLogger.log("Changed Tax Payer's or Company's name: " + companyName);
    }

    /**
     * Enable or Disable Generate Invoicing for Epi-Server
     *
     * @param isEnabled pass true to enable & pass false to disable
     */
    public void enableOrDisableInvoicing(boolean isEnabled) {
        waitForPageLoad();
        WebElement invoice = wait.waitForElementPresent(GENERATE_INVOICE_CHECKBOX);
        if (isEnabled && !checkbox.isCheckboxChecked(invoice)) {
            click.moveToElementAndClick(invoice);
        }
        if (!isEnabled && checkbox.isCheckboxChecked(invoice)) {
            click.moveToElementAndClick(invoice);
        }
    }

    /**
     * Saves the company details for Epi-server
     */
    public void saveVertexOSeriesChanges() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(SAVE_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Saved Vertex O-Series settings");
    }
}