package com.vertex.quality.connectors.ariba.portal.pages.config;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * contains all the necessary methods to interact with the integration configuration page
 *
 * @author osabha
 */
public class AribaPortalIntegrationConfigurationPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalIntegrationConfigurationPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * enters text into the search field looking for tax config task
	 * and then clicks on search button
	 */
	public void searchForTaxTask( )
	{
		WebElement searchBoxContainer = wait.waitForElementPresent(By.className("w-box-search"));
		List<WebElement> searchFieldConts = wait.waitForAllElementsPresent(By.tagName("tr"), searchBoxContainer);
		WebElement searchFieldCont = element.selectElementByText(searchFieldConts, "Task Name:");
		WebElement searchField = wait.waitForElementEnabled(By.tagName("input"), searchFieldCont);
		text.enterText(searchField, "Document Data for External Tax Calculation");
		WebElement searchButton = wait.waitForElementEnabled(By.className("w-btn-primary"), searchBoxContainer);
		click.clickElementCarefully(searchButton);
		waitForPageLoad();
	}

	/**
	 * clicks on the action button and then selects edit from the list
	 * for the first tax task listed after the search
	 *
	 * @return new instance of the edit config task page
	 */
	public AribaPortalEditConfigTaskPage editTaxTask( )
	{
		waitForPageLoad();
		WebElement actionButtonCont = wait.waitForElementDisplayed(By.className("firstRow"));
		WebElement actionButton = wait.waitForElementPresent(By.tagName("button"), actionButtonCont);
		click.clickElementCarefully(actionButton);
		List<WebElement> parentList = wait.waitForAllElementsPresent(By.className("w-pmi-item"));
		WebElement editOption = element.selectElementByText(parentList, "Edit");
		click.clickElement(editOption);
		return initializePageObject(AribaPortalEditConfigTaskPage.class);
	}
}
