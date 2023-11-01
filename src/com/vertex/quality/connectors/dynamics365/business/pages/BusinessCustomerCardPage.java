package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * representation of the customer card page
 *
 * @author cgajes
 */
public class BusinessCustomerCardPage extends BusinessSalesBasePage {
	protected By dialogBoxLoc = By.className("ms-nav-content-box");

	protected By sectionExpandCon = By.className("ms-nav-columns-caption");

	protected By customerTitleLoc = By.cssSelector("div[class*='title--']");
	protected By customerNumberLoc = By.cssSelector("[aria-label*='No.,']");
	protected By customerNameInputLoc = By.xpath("//div[@controlname='Name']//input");
	protected By vertexCustomerCodeLoc = By.xpath("//div[@controlname='VER_Customer Class']//input");
	protected By addressLineOneFieldLoc = By.xpath("//div[@controlname='Address']//input");
	protected By addressLineTwoFieldLoc = By.cssSelector("[aria-label*='Address 2,']");
	protected By countryFieldLoc = By.xpath("//div[@controlname='Country/Region Code']//input");
	protected By cityFieldLoc = By.xpath("//div[@controlname='City']//input");
	protected By stateFieldLoc = By.xpath("//div[@controlname='County']//input");
	protected By zipCodeFieldLoc =  By.xpath("//div[@controlname='Post Code']//input");
	protected By taxAreaCodeLoc = By.xpath("//div[@controlname='Tax Area Code']//input");
	protected By bToBCustomer = By.xpath("//a[@title='Select record \"COMPANY\"']");
	protected By customerNameField = By.cssSelector("input[aria-label='Name, (Blank)']");
	protected By newButtonLoc = By.xpath("//*[@aria-label='New' and @title='Create a new entry.']");
	protected By taxGroupCode = By.cssSelector("input[aria-label*='Code,']");
	protected By taxGroupDescription= By.cssSelector("input[aria-label*='Description,']");
	protected By okayButton= By.xpath("//span[contains(.,'Yes')]");
	protected By customerCodeField= By.xpath("(//input[@aria-label='No., '])[1]");
	protected By invoicingTab = By.xpath("//a[text()='Tax Registration No.']");
	protected By zipCodeLoc= By.xpath("//div[contains(@controlname,'Post Code')]//input");
	protected By zipCodeForCustomerCard=By.xpath("//div[@controlname='Post Code']//span");
	protected By customerLink = By.xpath("(//button[@aria-label='Customer'])[2]");
	protected By shipToAddress = By.xpath("//button[@aria-label=\"Ship-to Addresses\"]");
	protected By zipCodeField = By.xpath("(//div[@controlname='Post Code'])[2]//input");
	protected By zipCodeFieldValue = By.xpath("(//div[@controlname='Post Code'])[2]//span");


	public BusinessCustomerCardPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * finds the zip code field and returns the value
	 *
	 * @return zip code as a string
	 */
	public String getZipCode() {

		WebElement field = wait.waitForElementPresent(zipCodeLoc);
		hover.hoverOverElement(field);
		String zip = field.getAttribute("value");
		if (zip == null)
		{
			zip = text.getElementText(field);
		}
		return zip;
	}

	/**
	 * click the invoicing section toggle to expand those fields
	 */
	public void expandInvoicingSection() {
		List<WebElement> conList = wait.waitForAllElementsPresent(sectionExpandCon);
		for (WebElement con : conList) {
			String txt = text.getElementText(con);
			if (txt.contains("Invoicing")) {
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
	public void enterCustomerName(String name) {
		WebElement field = wait.waitForElementDisplayed(customerNameInputLoc);
		text.enterText(field, name);
	}

	/**
	 * enters the customer code
	 *
	 * @param code
	 */
	public void enterVertexCustomerCode(String code) {
		WebElement field = wait.waitForElementDisplayed(vertexCustomerCodeLoc);
		text.enterText(field, code);
	}

	/**
	 * Select Business-to-Business Customer (Bank)
	 *
	 * @author bhikshapathi
	 */
	public void selectBTOBCustomer() {
		WebElement bToBLink = wait.waitForElementDisplayed(bToBCustomer);
		try{
			click.clickElementCarefully(bToBLink);
		}catch(Exception e){
			VertexLogger.log(e.toString());
			click.javascriptClick(bToBLink);
		}
		waitForPageLoad();
	}
	/**
	 * clicks on the +New button
	 *
	 * @author bhikshapathi
	 */
	public void clickNew() {
		List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
		WebElement newButton = buttonList.get(buttonList.size() - 1);
		click.javascriptClick(newButton);
		waitForPageLoad();
	}
	/**
	 * Select Business-to-Business Customer (Bank)
	 *
	 * @author bhikshapathi
	 */
	public void enterCustName(String name) {
		handlePopUpMessage(alertOkButtonLoc, shortTimeout);
		WebElement cName = wait.waitForElementDisplayed(customerNameField);
		click.clickElementCarefully(cName);
		text.enterText(cName, name);
		text.pressTab(cName);
		waitForPageLoad();
	}
	/**
	 * enter Tax Group code
	 *
	 * @author bhikshapathi
	 */
	public void enterTaxGCode(String name) {
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
		WebElement des = wait.waitForElementDisplayed(taxGroupDescription);
		click.clickElementCarefully(des);
		text.enterText(des, description);
		waitForPageLoad();
	}
	/**
	 * enters the first line of the street address
	 *
	 * @param addressLine
	 */
	public void enterAddressLineOne(String addressLine) {
		waitForPageLoad();
		WebElement field = wait.waitForElementDisplayed(addressLineOneFieldLoc);
		click.clickElementIgnoreExceptionAndRetry(field);
		text.enterText(field, addressLine);
	}

	/**
	 * enters the second line of the street address
	 *
	 * @param addressLine
	 */
	public void enterAddressLineTwo(String addressLine) {
		WebElement field = wait.waitForElementDisplayed(addressLineOneFieldLoc);
		text.enterText(field, addressLine);
	}
	/**
	 * enters the country for the address
	 *
	 * @param country
	 */
	public void enterAddressCountry(String country) {
		WebElement field = wait.waitForElementDisplayed(countryFieldLoc);
		text.enterText(field, country);
	}
	/**
	 * enters the city for the address
	 *
	 * @param city
	 */
	public void enterAddressCity(String city) {
		WebElement field = wait.waitForElementDisplayed(cityFieldLoc);
		text.enterText(field, city);
	}

	/**
	 * enters the state for the address
	 *
	 * @param state
	 */
	public void enterAddressState(String state) {
		WebElement field = wait.waitForElementDisplayed(stateFieldLoc);
		text.clearText(field);
		waitForPageLoad();
		text.enterText(field, state);
	}

	/**
	 * enters the zip code for the address
	 *
	 * @param zipCode
	 */
	public void enterAddressZip(String zipCode) {
		WebElement field = wait.waitForElementDisplayed(zipCodeFieldLoc);
		text.enterText(field, zipCode);
	}
	/**
	 * get the state for the address
	 * @author bhikshapathi
	 */
	public String getAddressZip() {
		waitForPageLoad();
		WebElement field = wait.waitForElementDisplayed(zipCodeForCustomerCard);
		String zip = field.getText();
		return zip;
	}
	/**
	 * enters the tax area code
	 *
	 * @param code
	 */
	public void enterTaxAreaCode(String code) {
		WebElement field = wait.waitForElementDisplayed(taxAreaCodeLoc);
		text.enterText(field, code);
		text.pressTab(field);
	}
	/**
	 * enters the tax area code
	 *
	 * @param code
	 */
	public void updateCustomerCode(String code) {
		WebElement field = wait.waitForElementDisplayed(customerCodeField);
		text.clearText(field);
		text.enterText(field, code);
		text.pressTab(field);
	}
	/**
	 * Clicks ok button to accept the code Update
	 * @author bhikshapathi
	 */
	public void acceptTheChange()
	{
		wait.waitForElementDisplayed(okayButton);
		click.clickElementCarefully(okayButton);
		waitForPageLoad();
	}
	/**
	 * Clicks the header for the Invoicing category
	 * to open the section
	 * @author bhikshapathi
	 */
	public void openInvoicingCategory( )
	{
		if (!element.isElementDisplayed(invoicingTab))
		{
			List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
			WebElement invoicing = element.selectElementByText(categoriesList, "Invoicing");
			click.clickElement(invoicing);
		}
	}

	/**
	 * This method is to get Tax group code Alert Message from statistics Tab
	 * @return String of the error
	 * @author dpatel
	 */
	public String getAddressValidPopupMessage()
	{
		String alertText ;
		WebElement addValidPopup = wait.waitForElementPresent(taxGroupAlert);
		alertText = text.getElementText(addValidPopup);
		click.clickElementCarefully(ok);
		return alertText;
	}

	/**
	 * Clicks the Customer link
	 * @author Mario Saint-Fleur
	 */
	public void clickCustomerLink(){
		wait.waitForElementDisplayed(customerLink);
		click.clickElementCarefully(customerLink);
	}

	/**
	 * Navigates to the Ship-To Address link
	 * @author Mario Saint-Fleur
	 */
	public void navigateToShipToAddress(){
		clickCustomerLink();
		wait.waitForElementDisplayed(shipToAddress);
		click.clickElementCarefully(shipToAddress);
	}

	/**
	 * Navigates to the Customer Code for Ship-To Address
	 * @param customerCode
	 */
	public void navigateToShipToAddressCustomerCode(String customerCode){
		navigateToShipToAddress();
		WebElement selectCustomerCode = wait.waitForElementDisplayed(By.xpath("//a[text()='"+customerCode+"']"));
		click.clickElementCarefully(selectCustomerCode);
	}

	/**
	 * Enters the Zip Code
	 * @param zipCode
	 */
	public void enterZipCode(String zipCode){
		wait.waitForElementDisplayed(zipCodeField);
		text.enterText(zipCodeField, zipCode);
	}

	/**
	 * Get the Zip Code
	 */
	public String getZipCodeShipToAddress(){
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(zipCodeFieldValue);
		String zipCodeValue = attribute.getElementAttribute(zipCodeFieldValue, "innerHTML");
		System.out.println("Zip code value is: " + zipCodeValue);
		return zipCodeValue;
	}
}