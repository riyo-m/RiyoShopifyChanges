package com.vertex.quality.connectors.ariba.portal.components.procurment;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.components.base.AribaPortalComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the line view tab in the invoice reconciliation page in the Buyer site
 * contains all the methods necessary to interact with it
 *
 * @author osabha
 */
public class AribaReconciliationLineViewTab extends AribaPortalComponent
{
	public AribaReconciliationLineViewTab( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By searchMoreButtonLoc = By.className("w-chSearchLink");
	protected final By dialogContainerLoc = By.className("w-dlg-inner-wrapper");
	protected final By dialogSearchFieldLoc = By.cssSelector("input[aria-label='Search for a specific value']");
	protected final By dialogSearchButtonLoc = By.cssSelector(
		"button[aria-label='Search for a specific value in the list']");
	protected final By dialogSelectButtonLoc = By.cssSelector("button[title='Select this value for the field']");
	protected final By remitToFieldContLoc = By.className("a-ir-hdrinfo-addr");
	protected final By remitToFieldLoc = By.cssSelector("div[title='Select from the list']");

	/**
	 * interacts with the remit to field and dialog to search and select a remit to location
	 *
	 * @param location location name to select
	 */
	public void changeRemitToLocation( String location )
	{
		WebElement remitToField = findRemitToField();
		click.clickElement(remitToField);
		WebElement searchMoreButton = wait.waitForElementEnabled(searchMoreButtonLoc);
		click.clickElement(searchMoreButton);
		WebElement dialogContainer = wait.waitForElementDisplayed(dialogContainerLoc);
		WebElement dialogSearchField = wait.waitForElementEnabled(dialogSearchFieldLoc, dialogContainer);
		WebElement searchButton = wait.waitForElementEnabled(dialogSearchButtonLoc, dialogContainer);
		text.enterText(dialogSearchField, location);
		click.clickElement(searchButton);
		WebElement selectButton = wait.waitForElementEnabled(dialogSelectButtonLoc, dialogContainer);
		click.clickElement(selectButton);
	}

	public WebElement findRemitToField( )
	{
		WebElement field = null;
		WebElement fieldContainer = null;
		List<WebElement> potentialContainers = wait.waitForAllElementsDisplayed(remitToFieldContLoc);
		for ( WebElement container : potentialContainers )
		{
			boolean isPresent = element.isElementPresent(remitToFieldLoc, container);
			if ( isPresent )
			{
				fieldContainer = container;
				field = wait.waitForElementEnabled(remitToFieldLoc, fieldContainer);
			}
		}
		return field;
	}
}
