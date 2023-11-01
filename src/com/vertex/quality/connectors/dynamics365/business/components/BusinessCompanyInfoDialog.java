package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessManualSetupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the company info dialog from the manual setup page
 * contains all the necessary methods to interact with the dialog
 *
 * @author osabha
 */
public class BusinessCompanyInfoDialog extends VertexComponent

{
	public BusinessCompanyInfoDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By zipCodeFieldLoc = By.xpath("//div[@controlname='Post Code']//input");
	protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
	protected By closeLocCard = By.xpath("(//span/i[@data-icon-name='Back'])[2]");
	protected By errortext = By.xpath("(//div[@class='ms-nav-validationpanel-text'])[2]");
	protected By revertLink = By.linkText("revert the change");
	protected By searchButton = By.xpath("(//div/i[@data-icon-name='Search'])[2]");
	protected By searchLocation = By.xpath("(//div/i[@data-icon-name='Search'])[3]");
	protected By locSearchButton = By.cssSelector("input[aria-label*='Search View - Locations']");
	protected By okaybutton = By.xpath("//button/span[contains(.,'Yes')]");
	protected By noButtonField = By.xpath("//span[text()='No']");
	protected By dialogBoxLoc = By.className("ms-nav-content");
	protected By dialogTextLoc = By.className("dialog-content");

	/**
	 * locates the company zip code field and enters zip code into it
	 */
	public void enterWrongZip( )
	{
		WebElement zipCodeField = wait.waitForElementDisplayed(zipCodeFieldLoc);
		click.clickElement(zipCodeField);
		text.enterText(zipCodeField, "19015");
	}
	/**
	 * locates the company zip code field and returns zip code value
	 */
	public String getZipCode( )
	{
		wait.waitForElementPresent(zipCodeFieldLoc);
		String zip = attribute.getElementAttribute(zipCodeFieldLoc,"value");

		return zip;
	}

	/**
	 * locates the clicks on the back and save arrow button
	 *
	 * @return instance of the manual setup page
	 */
	public BusinessManualSetupPage closeDialog( )
	{
		WebElement backArrow = wait.waitForElementDisplayed(backAndSaveArrowButtonLoc);
		click.javascriptClick(backArrow);
		return new BusinessManualSetupPage(driver);
	}
	/**
	 * locates the clicks on the back and save arrow button in Location card page
	 *
	 * @author bhikshapathi
	 */
	public void closeLocationCard( )
	{
		WebElement backArrow = wait.waitForElementDisplayed(closeLocCard);
		click.javascriptClick(backArrow);
		waitForPageLoad();
	}
	/**
	 * get The Error Text
	 *
	 * @author bhikshapathi
	 */
	public String  getTheErrorText( )
	{   waitForPageLoad();
		WebElement backArrow = wait.waitForElementDisplayed(errortext);
		String error=text.getElementText(backArrow);
		waitForPageLoad();
		 return error;
	}
	/**
	 * The page has an error. Correct the error or try to revert Back The Change
	 *
	 * @author bhikshapathi
	 */

	public  void revertBackTheChange()
	{
		waitForPageLoad();
		WebElement element=wait.waitForElementDisplayed(revertLink);
		click.clickElementCarefully(element);
	}
	/**
	 * locates the clicks on the back and save arrow button
	 *
	 * @author bhikshapathi
	 */
	public void closeConfidenceIndicatorPopUp()
	{   waitForPageLoad();
		WebElement element=wait.waitForElementDisplayed(okaybutton);
		click.clickElementCarefully(element);
		waitForPageLoad();
	}

	/**
	 * locates the zip code field and extracts the test from it
	 *
	 * @return text in the zip code field
	 */
	public String getZipCodeFromField( )
	{
		WebElement zipCodeField = wait.waitForElementDisplayed(zipCodeFieldLoc);
		String zipCode = zipCodeField.getAttribute("value");

		return zipCode;
	}
	/**
	 * locates the location field and search for particular location
	 * @param location
	 * @author bhikshapathi
	 */
	public void searchForLocation(String location)
	{   waitForPageLoad();
		WebElement element=wait.waitForElementEnabled(searchButton);
		click.clickElementIgnoreExceptionAndRetry(element);
		waitForPageLoad();
		WebElement locField=wait.waitForElementDisplayed(locSearchButton);
		text.selectAllAndInputText(locField,location);
		String itemRow = String.format("//td/a[contains(text(),'%s')]", location);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();

	}
	/**
	 * locates the location field and search for particular location
	 * @param location
	 * @author bhikshapathi
	 */
	public void updateBackLocation(String location)
	{   waitForPageLoad();
		WebElement element=wait.waitForElementEnabled(searchLocation);
		click.clickElementCarefully(element);
		waitForPageLoad();
		WebElement locField=wait.waitForElementDisplayed(locSearchButton);
		text.clearText(locField);
		text.enterText(locField,location);
		String itemRow = String.format("//td/a[contains(text(),'%s')]", location);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();

	}
/**
 * locates the No button and clicks on it
 */
	public void clickNoOnPopup(){
		WebElement noButton=driver.findElement(noButtonField);
		noButton.click();
	}
	/**
	 * Gets the text from dialog box
	 */
	public String getDialogBoxText( )
	{
		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement dialogText = wait.waitForElementDisplayed(dialogTextLoc, dialogBox);
		String dialogtxt = dialogText.getText();
		return dialogtxt;
	}
}
