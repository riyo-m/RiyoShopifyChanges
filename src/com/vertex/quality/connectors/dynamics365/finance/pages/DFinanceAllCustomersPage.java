package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * All Customers page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceAllCustomersPage extends DFinanceBasePage
{
	protected By SEARCH_INPUT = By.cssSelector("input[id*='custtablelistpage'][id*='QuickFilterControl']");
	protected By DELETE_BUTTON = By.cssSelector(".button-label[id*='DeleteButton']");
	protected By SELECT_CHECKBOX = By.xpath("//div[contains(@class, 'dyn-hoverMarkingColumn')]");

	public DFinanceAllCustomersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on new purchase order button
	 */
	public DFinanceCreateCustomerPage clickNewCustomerButton( )
	{
		//TODO create component
		wait.waitForElementEnabled(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);

		//TODO redundant
		waitForPageLoad();

		DFinanceCreateCustomerPage createCustomerPage = initializePageObject(DFinanceCreateCustomerPage.class);

		return createCustomerPage;
	}

	/**
	 * Searches for and finds a customer account
	 * @param customerAccount
	 * @return result - whether the account is found or not
	 */
	public boolean searchCustomerAccount( String customerAccount )
	{
		wait.waitForElementDisplayed(SEARCH_INPUT);
		text.selectAllAndInputText(SEARCH_INPUT, customerAccount + Keys.ARROW_UP + Keys.ENTER);
		waitForPageLoad();

		By searchResultBy = getCustomerAccountBy(customerAccount);
		boolean result = element.isElementDisplayed(searchResultBy);
		click.clickElementIgnoreExceptionAndRetry(searchResultBy);
		text.pressEnter(searchResultBy);

		return result;
	}

	/**
	 * Gets the customer account based on the account number
	 * @param customerAccount
	 * @return customerAccountBy - finds the customer account
	 */
	private By getCustomerAccountBy( String customerAccount )
	{
		By customerAccountBy = By.cssSelector(
			String.format("[id*='CustTable_AccountNum'][value*='%s']", customerAccount));
		return customerAccountBy;
	}

	/**
	 * Click on the selected customer account checkbox
	 */
	public void clickSelectedCustomerCheckBox(){
		wait.waitForElementPresent(SELECT_CHECKBOX);
		click.clickElementCarefully(SELECT_CHECKBOX);
	}

	/**
	 * Click on delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		waitForPageLoad();
	}
}
