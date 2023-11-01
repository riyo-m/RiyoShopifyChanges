package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * This class represents the Integration attributes page from which user can modify them
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author dPatel
 */

public class WorkdayIntegrationAttributesPage extends VertexPage {

    public WorkdayIntegrationAttributesPage(WebDriver driver) {
        super(driver);
    }

    protected By intSys = By.xpath("(//input[@placeholder='Search'])[2]");
    protected By batchPost = By.cssSelector("p[title='VTX-BatchPostCustomerInvoiceBP']");
    protected By batchQuote = By.cssSelector("p[title='VTX-BatchQuoteCustomerInvoiceBP']");
    protected By ok = By.cssSelector("button[title='OK']");
    protected By title = By.cssSelector("span[data-automation-id='pageHeaderTitleText']");
    protected By actionTab = By.xpath("//button[text()='Actions']");
    protected By intSystemAction = By.xpath("//div[@data-automation-label='Integration System']");
    protected By intAtt = By.xpath("//div[@data-automation-label='Configure Integration Attributes']");
    protected By taxCalcURL = By.xpath("(//div[@data-automation-id='textInput'])[3]");
    protected By trustedID = By.xpath("(//div[@data-automation-id='textInput'])[2]");
    protected By inputTrust = By.cssSelector("input[data-automation-id='textInputBox']");
    protected By inputTaxCalc = By.cssSelector("input[data-automation-id='textInputBox'][type='text']");
    protected By custInvBPButton = By.xpath("(//div[text()='VTX-CustomerInvoiceBP'])[1]");
    protected By suplInvBPButton = By.xpath("(//div[text()='VTX-SupplierInvoiceBP'])[1]");
    protected By suplPreProcessInt = By.cssSelector("p[title='VTX-SupplierInvoicePreProcessBP']");
    protected By viewTrustedID = By.xpath("(//div[@data-automation-id='textView'])[13]");
    protected By viewTaxCalcURL = By.xpath("(//div[@data-automation-id='textView'])[15]");
    protected By basicDetails = By.cssSelector("span[data-automation-id='fieldSetLegendLabel'][title='Basic Details']");
    protected By launchParam = By.xpath("(//div[text()='Launch Parameters'])[1]");
    protected By gridTitle= By.xpath("(//span[@data-automation-id='gridTitleLabel'])[4]");
    protected By versionText = By.xpath("//div[text()='Workday2020R1v1.1.0']");
    protected By versionParam = By.xpath("//div[@data-automation-id='textView'and text()='Version']");
    protected By maximize = By.xpath("(//div[@title='Toggle Fullscreen Viewing Mode'])[4]");
    protected By doNotShow = By.xpath("//div[text()='Do Not Show']");
    protected By configureIntAtt = By.cssSelector("span[title='Configure Integration Attributes for Integration System']");




    /**
     * This method enters integration system in the field and dynamically wait till it populate
     *
     * @param integration name of the integration for ex. "VTX-BatchPostCustomerInvoiceBP"
     */
    public void enterIntegrationSystem(String integration)
    {
        WebElement textBox = wait.waitForElementEnabled(intSys);
        text.enterText(textBox,integration);
        textBox.sendKeys(Keys.ENTER);
        switch (integration) {
            case "VTX-BatchPostCustomerInvoiceBP":
                wait.waitForElementDisplayed(batchPost);
                break;
            case "VTX-CustomerInvoiceBP":
                click.clickElementCarefully(custInvBPButton);
                break;
            case "VTX-SupplierInvoiceBP":
                click.clickElementCarefully(suplInvBPButton);
                break;
            case "VTX-SupplierInvoicePreProcessBP":
                click.clickElementCarefully(suplPreProcessInt);
                break;
            default:
                wait.waitForElementDisplayed(batchQuote);
                break;
        }
        clickOk();
    }

    /**
     * This method clicks on "Ok" button and wait till next page loads
     */
    public void clickOk()
    {
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(basicDetails);
    }

    /**
     * This method navigate to Edit attributes page
     */
    public void getEditAttributePage()
    {
        click.clickElement(wait.waitForElementDisplayed(actionTab));
        wait.waitForElementDisplayed(intSystemAction);
        hover.hoverOverElement(intSystemAction);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(intAtt)).build().perform();
        click.clickElementCarefully(intAtt);
        wait.waitForElementDisplayed(configureIntAtt);
    }

    /**
     * This method enters tax calc URL and wait till it populates in the field
     *
     * @param URL taxCalcURL to enter
     */
    public void enterTaxCalURL(String URL)
    {
        hover.hoverOverElement(taxCalcURL);
        wait.waitForElementDisplayed(taxCalcURL).click();
        wait.waitForElementDisplayed(inputTaxCalc).clear();
        wait.waitForElementDisplayed(inputTaxCalc).click();
        text.enterText(inputTaxCalc,URL);
    }

    /**
     * This method enters trusted ID and wait till it populates in the field
     *
     * @param trustedId trustedID
     */
    public void enterTrustedID(String trustedId)
    {
        hover.hoverOverElement(trustedID);
        wait.waitForElementDisplayed(trustedID).click();
        wait.waitForElementDisplayed(inputTrust).clear();
        text.enterText(inputTrust,trustedId);
    }

    /**
     * This method validates Trusted ID
     *
     * @param expectedTrustedID trustedID
     */
    public boolean validateTrustedID(String expectedTrustedID)
    {
        return text.getElementText(viewTrustedID).equals(expectedTrustedID);
    }

    /**
     * This method validates Tax Calculation URL
     *
     * @param expectedTaxCalcURL taxCalcURL
     */
    public boolean validateTaxCalcURL(String expectedTaxCalcURL)
    {
        return text.getElementText(viewTaxCalcURL).equals(expectedTaxCalcURL);
    }

    /**
     * This method switches To required Tab
     *
     * @param tabToSwitch for ex. "Launch Parameters"
     */
    public void switchTab(String tabToSwitch)
    {
        click.clickElementCarefully(launchParam);
        wait.waitForTextInElement(gridTitle,tabToSwitch);
    }

    /**
     * This method validate that version parameter exist in lauch parameter
     *
     * @return true if Version exist in parameters
     */
    public boolean isVersionParamExist()
    {
        return element.isElementPresent(versionParam);
    }

    /**
     * This method validate correct Version number in the Launch Paramters
     * It also validates that it will not show while launching
     *
     * @return true if correct version and in "Do Not Show" mode
     */
    public boolean validateVersionParameter(String expectedValue)
    {
        click.clickElementCarefully(maximize);
        waitForPageLoad();
        scroll.scrollElementIntoView(versionText);
        return (text.getElementText(versionText).equals(expectedValue)) && (element.isElementPresent(doNotShow));
    }

    /**
     * This method validate that given integration parameters are present in integration system
     */
    public boolean validateIntAttributePresent(String attribute)
    {
        String intParameterXpath = String.format("//div[text()='%s']",attribute);
        return element.isElementPresent(By.xpath(intParameterXpath));
    }

}
