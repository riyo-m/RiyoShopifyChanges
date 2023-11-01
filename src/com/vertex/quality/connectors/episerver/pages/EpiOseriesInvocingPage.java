package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Episerver Administration Oseries Company Invoicing page - contains all
 * re-usable methods specific to this page.
 */
public class EpiOseriesInvocingPage extends VertexPage
{
	public EpiOseriesInvocingPage( WebDriver driver )
	{
		super(driver);
	}

	protected By AUTOMATIC_INVOICING_CHECKBOX = By.id("FullRegion_AutomaticInvoicingCheckbox");
	protected By INVOICING_SAVE_BUTTON = By.id("FullRegion_SaveInvoicingButton");
	protected By SUCCESS_MESSAGE = By.id("FullRegion_SystemMessage");
	protected By SHIP_TO_MULTIPLE_ADDRESS_BUTTON = By.xpath("//*[contains(text(), 'Ship to multiple addresses')]");
	protected By SHIP_TO_MULTIPLE_ADDRESS_HEADING = By.xpath("//h1[contains(text(), 'Ship to multiple addresses')]");
	protected By ADD_NEW_ADDRESS_HEADING = By.xpath("//h2[contains(text(), 'Add a new address')]");
	protected By SHIP_MULTI_ADDRESS1_ADD_BUTTON = By.xpath(
		"//select[@id='CartItems_0__AddressId']/following-sibling::button");
	protected By SHIP_MULTI_ADDRESS2_ADD_BUTTON = By.xpath(
		"//select[@id='CartItems_1__AddressId']/following-sibling::button");
	protected By SHIP_MULTI_ADDRESS3_ADD_BUTTON = By.xpath(
		"//select[@id='CartItems_2__AddressId']/following-sibling::button");
	protected By CONTINUE_BUTTON = By.cssSelector("[class='btn btn-primary'][type='submit']");

	/**
	 * This method is used to switch to Region frame
	 */
	public void switchToRegionFrame( )
	{
		driver
			.switchTo()
			.defaultContent();
		By region = By.id("FullRegion_InfoFrame");
		wait.waitForElementPresent(region, 30);
		window.switchToFrame(region, 60);
	}

	/**
	 * This method is used to check/uncheck the invoice checkbox
	 */
	public void checkInvoiceAutomatically( Boolean flag )
	{
		this.switchToRegionFrame();
		wait.waitForElementDisplayed(AUTOMATIC_INVOICING_CHECKBOX);
		if ( flag == true )
		{
			checkbox.setCheckbox(AUTOMATIC_INVOICING_CHECKBOX, true);
		}
		else
		{
			checkbox.setCheckbox(AUTOMATIC_INVOICING_CHECKBOX, false);
		}
	}

	/**
	 * This method is used to save invoicing changes
	 */
	public void clickInvoicingSaveButton( )
	{
		this.switchToRegionFrame();
		wait.waitForElementDisplayed(INVOICING_SAVE_BUTTON);
		String is_disabled = attribute.getElementAttribute(INVOICING_SAVE_BUTTON, "disabled");
		VertexLogger.log(String.format("is_disabled : '%s'", is_disabled));
		if ( is_disabled == "disabled" || is_disabled == "true" )
		{
			click.clickElement(INVOICING_SAVE_BUTTON);
			wait.waitForElementDisplayed(SUCCESS_MESSAGE);
			String msg = attribute.getElementAttribute(SUCCESS_MESSAGE, "text");
			if ( msg == "Invoicing settings saved" )
			{
				VertexLogger.log(String.format("%s", msg));
			}
			else
			{
				VertexLogger.log(msg, VertexLogLevel.WARN);
			}
		}
		else
		{
			VertexLogger.log("Save button is disabled that means, no changes made");
		}
	}

	public EpiAddAddressPage clickShipMultipleAddressButton( )
	{
		EpiAddAddressPage addaddresspage = new EpiAddAddressPage(driver);
		element.isElementDisplayed(SHIP_TO_MULTIPLE_ADDRESS_BUTTON);
		click.clickElement(SHIP_TO_MULTIPLE_ADDRESS_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIP_TO_MULTIPLE_ADDRESS_HEADING);
		return addaddresspage;
	}

	public void clickMultiAddressAddButton( String productSequenceNumber )
	{
		By SHIP_MULTI_ADDRESS1_ADD_BUTTON = By.xpath(String.format(
			"//*[@class='row\'][*[strong[text()='Item']]/*[contains(text(), '%')]]//button[text()='Add new address']",
			productSequenceNumber));
		wait.waitForElementDisplayed(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		click.clickElement(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_HEADING);
	}

	public void clickMultiAddress1AddButton( )
	{
		wait.waitForElementPresent(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		click.clickElement(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_HEADING);
	}

	public void clickMultiAddress2AddButton( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(SHIP_MULTI_ADDRESS2_ADD_BUTTON);
		click.clickElement(SHIP_MULTI_ADDRESS2_ADD_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_HEADING);
	}

	public void clickMultiAddress3AddButton( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(SHIP_MULTI_ADDRESS3_ADD_BUTTON);
		click.clickElement(SHIP_MULTI_ADDRESS3_ADD_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_HEADING);
	}

	public void clickContinueButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CONTINUE_BUTTON);
		click.clickElement(CONTINUE_BUTTON);
		waitForPageLoad();
	}
}
