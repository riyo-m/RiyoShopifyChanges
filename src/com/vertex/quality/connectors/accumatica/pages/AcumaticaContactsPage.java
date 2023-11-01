package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Contacts page actions/methods
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaContactsPage extends AcumaticaBasePage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By CONTACT_ID = By.id("ctl00_phF_form_edContactID_text");
	protected By CONTACT = By.xpath("//input//following-sibling::span[contains(text(),'Contact')]");
	protected By STATUS = By.xpath("//table[@id='ctl00_phF_form_edStatus_dd']//td[text()='Active']");
	protected By STATUS_DROPDOWN = By.cssSelector("[id*='ctl00_phF_form_edStatus'] div[icon='DropDownN']");
	protected By DEFAULT_STATUS = By.cssSelector("[id=ctl00_phF_form_edStatus_text]+span+span");
	protected By FIRST_NAME = By.id("ctl00_phG_tab_t0_FirstName");
	protected By SUR_NAME = By.xpath("//input[@id='ctl00_phG_tab_t0_Title_text']/..");
	protected By SUR_NAME_OPTION = By.xpath("//table[@id='ctl00_phG_tab_t0_Title_dd']//td[text()='Prof.']");
	protected By LAST_NAME = By.id("ctl00_phG_tab_t0_edLastName");
	protected By BUSINESS_ACCOUNT_PLUS_ICON = By.className("[id='ctl00_phG_tab_t0_edBAccountID'] [icon='EditN']");

	public AcumaticaContactsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * click on 'New Record Icon'
	 */
	public void clickOnNewRecordIcon( )
	{
		wait.waitForElementDisplayed(NEW_RECORD_PLUS_ICON);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
	}

	/**
	 * Get contact id
	 *
	 * @return
	 */
	public String getContactId( )
	{
		wait.waitForElementDisplayed(CONTACT_ID);
		String contactId = attribute.getElementAttribute(CONTACT_ID, "value");
		return contactId;
	}

	/**
	 * Get Contacts
	 *
	 * @return
	 */
	public String getContacts( )
	{
		wait.waitForElementDisplayed(CONTACT);
		String contact = attribute.getElementAttribute(CONTACT, "value");
		return contact;
	}

	/***
	 * Select active check box status
	 * @param status
	 */
	public void selectStatus( final String status )
	{
		wait.waitForElementDisplayed(STATUS_DROPDOWN);
		String actualStatus = attribute.getElementAttribute(DEFAULT_STATUS, "text");
		if ( status.equalsIgnoreCase(actualStatus) )
		{
			VertexLogger.log(String.format("It is in Active status"));
		}
		else
		{
			click.clickElement(STATUS_DROPDOWN);
			wait.waitForElementDisplayed(STATUS);
			click.clickElement(STATUS);
		}
	}

	/**
	 * Enter first name
	 *
	 * @param firstName
	 */
	public void setFirstName( String firstName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(FIRST_NAME);
		text.enterText(FIRST_NAME, firstName);
		waitForPageLoad();
	}

	/**
	 * Set last name
	 *
	 * @param lastName
	 */
	public void setLastName( String lastName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(LAST_NAME);
		text.enterText(LAST_NAME, lastName);
		waitForPageLoad();
	}

	/**
	 * Select sur name
	 *
	 * @param surName
	 */
	public void setSurName( String surName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SUR_NAME);
		click.clickElement(SUR_NAME);
		waitForPageLoad();
		wait.waitForElementDisplayed(SUR_NAME_OPTION);
		click.clickElement(SUR_NAME_OPTION);
		waitForPageLoad();
	}

	/**
	 * click pencil icon
	 */
	public void clickPencilIcon( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BUSINESS_ACCOUNT_PLUS_ICON);
		click.clickElement(BUSINESS_ACCOUNT_PLUS_ICON);
		waitForPageLoad();
	}

	/**
	 * switch to business account window
	 */
	public void switchToBusinessAccountsWindow( String pageName )
	{
		window.switchToWindowTextInTitle(pageName);
	}
}
