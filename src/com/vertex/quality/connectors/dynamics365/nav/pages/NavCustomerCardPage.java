package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * representation of the customer card page
 *
 * @author bhikshapathi
 */
public class NavCustomerCardPage extends NavSalesBasePage
{
    protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By sectionExpandCon = By.className("ms-nav-columns-caption");
    protected By mainFrameLoc = By.className("designer-client-frame");
    protected By customerTitleLoc = By.xpath("//div[@class='ms-nav-scrollable']/h1");
    protected By customerNumberLoc = By.cssSelector("[aria-label*='No.,']");
    protected By customerNameInputLoc = By.cssSelector("input[aria-label*='Name,']");
    protected By vertexCustomerCodeLoc = By.cssSelector("[aria-label*='Vertex Customer Class,']");
    protected By addressLineOneFieldLoc = By.xpath("(//div/a[contains(.,'Address')]/../div/input)[1]");
    protected By addressLineTwoFieldLoc = By.xpath("(//div/a[contains(.,'Address 2')]/../div/input)[1]");
    protected By countryFieldLoc = By.xpath("(//div/a[contains(.,'Country/Region Code')]/../div/input)[1]");
    protected By cityFieldLoc = By.xpath("(//div/a[contains(.,'City')]/../div/input)[1]");
    protected By stateFieldLoc = By.xpath("(//div/a[contains(.,'State')]/../div/input)[1]");
    protected By zipCodeFieldLoc = By.xpath("(//div/a[contains(.,'ZIP Code')]/../div/input)[1]");
    protected By taxAreaCodeLoc = By.xpath("(//div/a[contains(.,'Tax Area Code')]/../div/input)[1]");
    protected By locationCodeLoc = By.xpath("(//div/a[contains(.,'Location Code')]/../div/input)[1]");
    protected By domesticCustomer = By.xpath("//td/a[contains(.,'Customer DOMESTIC')]");
    protected By taxGroupCode = By.xpath("(//input[@class='cursorinherit stringcontrol-edit'])[2]");
    protected By taxGroupDescription = By.xpath("(//input[@class='cursorinherit stringcontrol-edit'])[3]");
    protected By customerNameField = By.xpath("(//div/a[contains(.,'Name')]/../div/input[@class='cursorinherit stringcontrol-edit'])[1]");
    protected By newButtonLoc = By.xpath("//div/a[@title='Create a new entry.']");
    protected By secondNewButtonLoc = By.xpath("(//span[contains(.,'New')])[6]");
    protected By errorMessage=By.xpath("//div[@class='ms-nav-validationmessage'][2]");
    protected By revertLink=By.linkText("revert the change");
    public NavCustomerCardPage( WebDriver driver ) { super(driver); }

    /**
     * Get the customer number from the current page
     *
     * @return customer number as string
     */
    public String getCurrentCustomerNumber( )
    {
        WebElement title = wait.waitForElementPresent(customerTitleLoc);
        String number = title.getText().trim();
        return number;
    }

    /**
     * finds the zip code field and returns the value
     *
     * @return zip code as a string
     */
    public String getZipCode( )
    {
        wait.waitForElementPresent(zipCodeFieldLoc);
        String zip = attribute.getElementAttribute(zipCodeFieldLoc,"value");

        return zip;
    }

    /**
     * click the invoicing section toggle to expand those fields
     */
    public void expandInvoicingSection( )
    {
        List<WebElement> conList = wait.waitForAllElementsPresent(sectionExpandCon);
        for ( WebElement con : conList )
        {
            String txt = text.getElementText(con);
            if ( txt.contains("Invoicing") )
            {
                click.clickElement(con);
                break;
            }
        }
    }

    /**
     * enters the customer name
     *
     * @param name
     */
    public void enterCustomerName( String name )
    {
        WebElement field = wait.waitForElementDisplayed(customerNameInputLoc);
        text.enterText(field, name);
    }

    /**
     * enters the customer number
     *
     * @param number
     */
    public void enterCustomerNumber( String number )
    {
        WebElement field = wait.waitForElementDisplayed(customerNumberLoc);
        text.enterText(field, number);
    }

    /**
     * enters the customer code
     *
     * @param code
     */
    public void enterVertexCustomerCode( String code )
    {
        WebElement field = wait.waitForElementDisplayed(vertexCustomerCodeLoc);
        text.enterText(field, code);
    }

    /**
     * enters the first line of the street address
     *
     * @param addressLine
     */
    public void enterAddressLineOne( String addressLine )
    {
        WebElement field = wait.waitForElementDisplayed(addressLineOneFieldLoc);
        text.enterText(field, addressLine);
    }
    /**
     * get the first line of the street address
     *@author bhikshapathi
     */
    public String  enterAddressLineOne(  )
    {
        WebElement field = wait.waitForElementDisplayed(addressLineOneFieldLoc);
        String address=field.getText();
        return address;
    }

    /**
     * enters the second line of the street address
     *
     * @param addressLine
     */
    public void enterAddressLineTwo( String addressLine )
    {
        WebElement field = wait.waitForElementDisplayed(addressLineOneFieldLoc);
        text.enterText(field, addressLine);
    }

    /**
     * enters the country for the address
     *
     * @param country
     */
    public void enterAddressCountry( String country )
    {
        WebElement field = wait.waitForElementDisplayed(countryFieldLoc);
        text.enterText(field, country);
        text.pressTab(field);
    }

    /**
     * enters the city for the address
     * @param city
     * @author bhikshapathi
     */
    public void enterAddressCity( String city )
    {   waitForPageLoad();
        WebElement field = wait.waitForElementDisplayed(cityFieldLoc);
        click.clickElementCarefully(field);
        text.enterText(field, city);
    }
    /**
     * get the city for the address
     * @author bhikshapathi
     */
    public String getAddressCity()
    {
        WebElement field = wait.waitForElementDisplayed(cityFieldLoc);
        String city=field.getText();
        return city;
    }

    /**
     * enters the state for the address
     *
     * @param state
     */
    public void enterAddressState( String state )
    {   waitForPageLoad();
        WebElement field = wait.waitForElementDisplayed(stateFieldLoc);
        click.clickElementCarefully(field);
        text.enterText(field, state);
    }
    /**
     * get the state for the address
     * @author bhikshapathi
     */
    public String getAddressState()
    {
        WebElement field = wait.waitForElementDisplayed(stateFieldLoc);
        String state=field.getText();
        return state;
    }

    /**
     * enters the zip code for the address
     *
     * @param zipCode
     */
    public void enterAddressZip( String zipCode )
    {   waitForPageLoad();
        WebElement field = wait.waitForElementDisplayed(zipCodeFieldLoc);
        click.clickElementCarefully(field);
        text.enterText(field, zipCode);
    }
    /**
     * get the state for the address
     * @author bhikshapathi
     */
    public String getAddressZip()
    {
        WebElement field = wait.waitForElementDisplayed(zipCodeFieldLoc);
        String zip=field.getText();
        return zip;
    }

    /**
     * enters the tax area code
     *
     * @param code
     */
    public void enterTaxAreaCode( String code )
    {
        WebElement field = wait.waitForElementDisplayed(taxAreaCodeLoc);
        click.javascriptClick(field);
        text.enterText(field, code);
    }
    /**
     * enters the location code
     *
     * @param code
     */
    public void enterLocationCode( String code )
    {
        WebElement field = wait.waitForElementDisplayed(locationCodeLoc);
        click.javascriptClick(field);
        text.enterText(field, code);
        text.pressTab(field);
    }
    /**
     * Select Domestic customer Link
     *
     * @author bhikshapathi
     */
    public void selectDomesticCustomer() {
        waitForPageLoad();
        WebElement domesticLink = wait.waitForElementDisplayed(domesticCustomer);
        click.javascriptClick(domesticLink);
        waitForPageLoad();
    }
    /**
     * clicks on the +New button
     *
     * @author bhikshapathi
     */
    public void clickNew() {
        window.switchToFrame(mainFrameLoc,30);
        WebElement element=wait.waitForElementDisplayed(newButtonLoc);
        click.javascriptClick(element);
        waitForPageLoad();
    }
    /**
     * clicks on the +New button
     *
     * @author bhikshapathi
     */
    public void clickSecondNew() {
        waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(secondNewButtonLoc);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }
    /**
     * enter Tax Group code
     *
     * @author bhikshapathi
     */
    public void enterTaxGCode(String name) {
        waitForPageLoad();
        WebElement cName = wait.waitForElementDisplayed(taxGroupCode);
        click.clickElementCarefully(cName);
        text.enterText(cName, name);
        text.pressTab(cName);
        waitForPageLoad();
    }
    /**
     *  enter Tax group code Description
     *
     * @author bhikshapathi
     */
    public void enterDescription(String description) {
        waitForPageLoad();
        WebElement des = wait.waitForElementDisplayed(taxGroupDescription);
        click.clickElementCarefully(des);
        text.enterText(des, description);
        waitForPageLoad();
    }
    /**
     * Select Business-to-Business Customer (Bank)
     *
     * @author bhikshapathi
     */
    public void enterCustName(String name) {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement cName = wait.waitForElementEnabled(customerNameField);
        click.javascriptClick(cName);
        text.enterText(cName, name);
        text.pressTab(cName);
        waitForPageLoad();
    }

    /**
     * Clicks the header for the Invoicing category
     * to open the section
     * @author bhikshapathi
     */
    public void openInvoicingCategory( )
    {   waitForPageLoad();
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement xmlLog = element.selectElementByText(categoriesList, "Invoicing");
        click.clickElement(xmlLog);
    }
    /**
     * Clicks the header for the Invoicing category
     * to open the section
     * @author bhikshapathi
     */
    public void openShippingCategory( )
    {
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement xmlLog = element.selectElementByText(categoriesList, "Shipping");
        click.clickElement(xmlLog);
    }
    /**
     * Gets the error popup text
     */
    public String getErrorMessage(){
        waitForPageLoad();
        WebElement errorValidation=wait.waitForElementDisplayed(errorMessage);
        String message=text.getElementText(errorValidation);
        waitForPageLoad();
        return message;
    }
    /**
     * Reverts the error on page by clicking revert the change
     */
    public void revertChanges(){
        waitForPageLoad();
        WebElement revertChange=wait.waitForElementDisplayed(revertLink);
        click.clickElementCarefully(revertChange);
        waitForPageLoad();
    }


}
