package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Saidulu Kodadala
 * Leads page actions /methods
 */
public class AcumaticaLeadsPage extends AcumaticaBasePage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By LEAD_ID = By.id("ctl00_phF_form_edContactID_text");
	protected By STATUS = By.xpath("//input//following-sibling::span[contains(text(),'New')]");
	protected By REASON = By.xpath("//input//following-sibling::span[contains(text(),'Assign')]");
	protected By SUR_NAME = By.xpath("//input[@id='ctl00_phG_tab_t0_Title_text']/..");
	protected By SUR_NAME_OPTION = By.xpath("//table[@id='ctl00_phG_tab_t0_Title_dd']//td[text()='Dr.']");
	protected By LAST_NAME = By.id("ctl00_phG_tab_t0_edLastName");
	protected By FIRST_NAME = By.id("ctl00_phG_tab_t0_FirstName");
	protected By BUSINESS_ACCOUNT_PLUS_ICON = By.className("[id='ctl00_phG_tab_t0_edBAccountID'] [icon='EditN']");

	public AcumaticaLeadsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * click on plus icon
	 */
	public void clickOnNewRecordIcon( )
	{
		wait.waitForElementDisplayed(NEW_RECORD_PLUS_ICON);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
	}

	/**
	 * Get lead id
	 *
	 * @return
	 */
	public String getLeadId( )
	{
		wait.waitForElementDisplayed(LEAD_ID);
		String leadId = attribute.getElementAttribute(LEAD_ID, "value");
		waitForPageLoad();
		return leadId;
	}

	/**
	 * Get Status
	 *
	 * @return
	 */
	public String getStatus( )
	{
		wait.waitForElementDisplayed(STATUS);
		String status = attribute.getElementAttribute(STATUS, "value");
		waitForPageLoad();
		return status;
	}

	/**
	 * Get Reason
	 *
	 * @return
	 */
	public String getReason( )
	{
		wait.waitForElementDisplayed(REASON);
		String reason = attribute.getElementAttribute(REASON, "value");
		waitForPageLoad();
		return reason;
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
	 * Enter Last name
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
