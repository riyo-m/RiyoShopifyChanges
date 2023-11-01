package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EpiOseriesCommercePage extends VertexPage
{
	public EpiOseriesCommercePage( WebDriver driver )
	{
		super(driver);
	}

	protected By QUICK_SILVER_ROW = By.cssSelector("[class='dgrid-row-table']:not([id*='header'])");
	protected By OPTIONS_LINK = By.xpath("//td[contains(@class, 'contextMenu')]//span[@title='Options']");
	protected By EDIT_LINK = By.xpath("//td[contains(text(), 'Edit')]");
	protected By CAMPAIGN_NAME_INPUT = By.cssSelector("[title='Name'][type='text']");
	protected By SCHEDULE_FROM_DATE = By.cssSelector("[title='Available from'][type='text']");
	protected By SCHEDULE_EXPIRES_DATE = By.cssSelector("[title='Expires on'][type='text']");
	protected By ACTIVE_CHECKBOX = By.xpath("//*[label[text()='Active']]//input[@type='checkbox']");
	protected By TARGET_MARKET_DROPDOWN = By.xpath(
		"//li[*[text()='Target market']]//table[@title='Target market']//*[@role='option']");
	protected By VISITOR_GROUPS = By.xpath("//li[*[text()='Visitor groups']]//*[contains(@class, 'message')]");
	protected By CLOSE_BUTTON = By.xpath("//*[contains(text(),'Close') and contains(@id, 'Button')]");
	protected By DOWN_ARROW = By.xpath("//span[@class='epi-navigation-expandcollapseIcon']");

	public void clickEditOptionsLink( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(QUICK_SILVER_ROW);
		click.clickElement(QUICK_SILVER_ROW);
		wait.waitForElementDisplayed(OPTIONS_LINK);
		click.moveToElementAndClick(OPTIONS_LINK);
		wait.waitForElementDisplayed(EDIT_LINK);
		hover.hoverOverElement(EDIT_LINK);
		click.clickElement(EDIT_LINK);
		waitForPageLoad();
	}

	public String getCompaignName( )
	{
		wait.waitForElementDisplayed(CAMPAIGN_NAME_INPUT);
		String name = attribute.getElementAttribute(CAMPAIGN_NAME_INPUT, "value");
		return name.trim();
	}

	public String getSchedulingAndStatusAvailableFromDate( )
	{
		wait.waitForElementDisplayed(SCHEDULE_FROM_DATE);
		String date = attribute.getElementAttribute(SCHEDULE_FROM_DATE, "value");
		VertexLogger.log(String.format("Scheduling and Status Available From Date: %s", date));
		String date1 = date.split(",")[0];
		return date1.trim();
	}

	public String getSchedulingAndStatusExpiresOnDate( )
	{
		wait.waitForElementDisplayed(SCHEDULE_FROM_DATE);
		String date = attribute.getElementAttribute(SCHEDULE_EXPIRES_DATE, "value");
		String date1 = date.split(",")[0];
		VertexLogger.log(String.format("Scheduling and Status Available From Date: %s", date));
		return date1.trim();
	}

	public boolean getSchedulingAndStatusActiveStatus( )
	{
		wait.waitForElementDisplayed(ACTIVE_CHECKBOX);
		boolean status = checkbox.isCheckboxChecked(ACTIVE_CHECKBOX);
		return status;
	}

	public String getSelectedTargetMarket( )
	{
		wait.waitForElementDisplayed(TARGET_MARKET_DROPDOWN);
		String actualMarket = attribute.getElementAttribute(TARGET_MARKET_DROPDOWN, "textContent");
		return actualMarket.trim();
	}

	public String getSelectedVisitorGroups( )
	{
		wait.waitForElementDisplayed(VISITOR_GROUPS);
		String visitorGroup = attribute.getElementAttribute(VISITOR_GROUPS, "textContent");
		return visitorGroup.trim();
	}

	public void clickOnCloseButton( )
	{
		wait.waitForElementDisplayed(CLOSE_BUTTON);
		click.clickElement(CLOSE_BUTTON);
		waitForPageLoad();
	}

	public void clickOnDownArrow( )
	{
		window.switchToDefaultContent();
		wait.waitForElementPresent(DOWN_ARROW);
		click.clickElement(DOWN_ARROW);
		waitForPageLoad();
	}
}
