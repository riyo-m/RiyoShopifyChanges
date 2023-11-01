package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the Manual setup page and all the necessary methods to interact with it
 *
 * @author osabha, cgajes
 */
public class BusinessManualSetupPage extends BusinessBasePage
{
	public BusinessCompanyInfoDialog companyInfoDialog;

	public BusinessManualSetupPage( WebDriver driver )
	{
		super(driver);
		this.companyInfoDialog = new BusinessCompanyInfoDialog(driver, this);
	}

	protected By categoryButtonLocs = By.className("thm-cont-u1-color-3--mintint--list-control-hovered");
	protected By categoryTintedButtonLoc = By.className("thm-cont-u1-color-3--medtint--list-control-hovered");
	protected By companyButtonLoc = By.cssSelector("a[aria-label='Name, Company']");
	protected By SearchCronus = By.xpath("//div/i[@data-icon-name='Search']//..//..//input[@aria-label='Search Edit - Manual Setup']");
	protected By searchInputElement = By.xpath("//input[@aria-label='Search Edit - Manual Setup']");
	protected By customersSearch = By.xpath("//div/i[@data-icon-name='Search']");
	protected By customerSearch = By.xpath("(//div/i[@data-icon-name='Search'])[2]");
	protected By clearCustomerfield = By.xpath("//span/i[@data-icon-name='Clear']");

	/**
	 * Selects Company from manual setup button
	 * @param searchButtonElement
	 *
	 * @author bhikshapathi
	 */
	public BusinessCompanyInfoDialog searchForCompany(String searchButtonElement)
	{   waitForPageLoad();
		WebElement search = wait.waitForElementEnabled(By.xpath("(//div/i[@data-icon-name='Search'])["+searchButtonElement+"]"));
		try {

			click.clickElementCarefully(search);
			wait.waitForElementDisplayed(searchInputElement);
		}
		catch(Exception ex) {
			VertexLogger.log(ex.toString());
			click.javascriptClick(search);
		}
		WebElement searchField = wait.waitForElementEnabled(searchInputElement);
		click.clickElementCarefully(searchInputElement);
		text.clearText(searchField);
		text.enterText(searchField,"Company");
		String itemRow = String.format("//div/div[contains(text(),'%s')]", "Company");
		WebElement openCompany = wait.waitForElementEnabled(By.xpath(itemRow));
		click.clickElementCarefully(openCompany);
		click.performDoubleClick(openCompany);
		waitForPageLoad();
		return companyInfoDialog;
	}
	/**
	 * Selects Company from manual setup button
	 *
	 * @author bhikshapathi
	 */
	public BusinessCompanyInfoDialog searchingForCompany()
	{   waitForPageLoad();
		WebElement search = wait.waitForElementEnabled(customersSearch);
		click.javascriptClick(search);
		WebElement searchField = wait.waitForElementEnabled(SearchCronus);
			text.enterText(searchField,"Company");

		String itemRow = String.format("//div[contains(text(),'%s')]", "Company");
		WebElement selectItem = wait.waitForElementDisplayed(By.xpath(itemRow));
		click.performDoubleClick(selectItem);
		waitForPageLoad();
		return companyInfoDialog;
	}

	/**
	 * Selects Locations from manual setup button
	 *
	 * @author bhikshapathi
	 */
	public BusinessCompanyInfoDialog navigateToLocations()
	{   waitForPageLoad();
		WebElement search = wait.waitForElementEnabled(customerSearch);
		click.javascriptClick(search);
		WebElement searchField = wait.waitForElementEnabled(searchInputElement);
		text.clearText(searchField);
		text.enterText(searchField,"Locations");
		String itemRow = String.format("//td/a[contains(text(),'%s')]", "Locations");
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
		return companyInfoDialog;
	}
	/**
	 * Selects Locations from manual setup button
	 *
	 * @author bhikshapathi
	 */
	public BusinessCompanyInfoDialog navigatingToLocations()
	{   waitForPageLoad();
		WebElement search = wait.waitForElementDisplayed(customersSearch);
		click.javascriptClick(search);
		WebElement searchField = wait.waitForElementDisplayed(searchInputElement);
		text.clearText(searchField);
		text.enterText(searchField,"Locations");
		By itemRow = By.xpath("//div/div[contains(text(),'Locations')]");
		waitForPageLoad();
		wait.waitForElementDisplayed(itemRow);
		click.clickElementIgnoreExceptionAndRetry(itemRow);
		waitForPageLoad();
		WebElement locationItem = wait.waitForElementDisplayed(itemRow);
		click.performDoubleClick(locationItem);
		return companyInfoDialog;
	}
	/**
	 * clears a customer displayed on the list page
	 * @author bhikshapathi
	 *
	 */
	public void  clearSearchField()
	{
		waitForPageLoad();
		WebElement clear = wait.waitForElementEnabled(clearCustomerfield);
		click.javascriptClick(clear);
		waitForPageLoad();

	}

}
