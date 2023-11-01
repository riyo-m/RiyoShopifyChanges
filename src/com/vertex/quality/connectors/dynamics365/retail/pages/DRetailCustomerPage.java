package com.vertex.quality.connectors.dynamics365.retail.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DRetailCustomerPage extends DFinanceBasePage
{
	public DRetailCustomerPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ADD_CUSTOMER = By.id("CustomerSearch_addSelectedCustomerToCartCommand");
	protected By SELECT_CUSTOMER = By.xpath("//div[@class='column5']/div[@aria-label='CUSTOMER ID 004021']");
	protected By CUSTOMERS_BUTTON = By.xpath("//button[text()='Customers']");
	protected By SEARCH_BOX = By.xpath("//input[@type='search']");
	protected By HOME_BUTTON = By.xpath("//button[@aria-label='Home']");
	protected By FIND_CUSTOMER = By.cssSelector("button[id='addCustomerButton']");
	protected By FIND_ORDER = By.xpath("//div[@id='WelcomeScreenButtonGrid-0']//button[@title='Find an order']");
	protected By SELECT_CUSTOMER_NAME = By.xpath("//div[contains(text(), 'Customer name')]");
	protected By INPUT_CUSTOMER_NAME = By.xpath("//input[@aria-label='Add filter Customer name ']");
	protected By FIRST_OK = By.xpath("//button[@class='primaryButton']");
	protected By APPLY_BUTTON = By.xpath("//button[@class='okButton primaryButton']");
	protected By SELECT_ORDER = By.xpath("//div[@class='win-container']//div[@aria-posinset='1']");
	protected By RETURN_ORDER = By.xpath("//button[@aria-label='Return order']");
	protected By RETURN_BUTTON = By.id("returnButton");
	protected By RETURN_OK = By.className("primaryButton");
	protected By CUSTOMER = By.xpath("//button[text()='Customers']");



	/**
	 * navigate to home page
	 */
	public void clickHomeButton( )
	{
		wait.waitForElementDisplayed(HOME_BUTTON);
		click.clickElementCarefully(HOME_BUTTON);

	}

	/**
	 * Click Find a customer button
	 */
	public void clickFindCustomerButton( )
	{
		wait.waitForElementPresent(FIND_CUSTOMER);
		click.javascriptClick(FIND_CUSTOMER);

	}

	/**
	 * Search customer by name
	 */
	public void searchCustomer( )
	{
		wait.waitForElementDisplayed(CUSTOMERS_BUTTON);
		click.clickElementCarefully(CUSTOMERS_BUTTON);
		wait.waitForElementDisplayed(SEARCH_BOX);
		text.enterText(SEARCH_BOX, "test");
		text.pressEnter(SEARCH_BOX);
	}

	/**
	 * Move to Customer Tab
	 */
	public void moveToCustomerTab()
	{
		WebElement customerTab = wait.waitForElementDisplayed(CUSTOMER);
		click.javascriptClick(customerTab);
	}

	/**
	 * It clicks on the "Auto Test"customer
	 */
	public void clickOnCustomer()
	{
		WebElement customer = wait.waitForElementDisplayed(SELECT_CUSTOMER);
		click.clickElementCarefully(customer);
		waitForPageLoad();
	}

	/**
	 * Add customer to order
	 */
	public void addCustomer( )
	{
		wait.waitForElementDisplayed(SELECT_CUSTOMER);
		click.clickElementCarefully(ADD_CUSTOMER);
		waitForPageLoad();
	}

	/**
	 * Click find order
	 */
	public void findOrder(String salesOrder )
	{
		String orderXpath = String.format("//div[text()='%s']/ancestor::div[@class='win-item']",salesOrder);
		WebElement orderEle = wait.waitForElementDisplayed(By.xpath(orderXpath));
		click.clickElementCarefully(orderEle);
		waitForPageLoad();
	}

	/**
	 * Enter customer name
	 */
	public void enterCustomerName(String custName )
	{
		wait.waitForElementDisplayed(SELECT_CUSTOMER_NAME);
		click.clickElementCarefully(SELECT_CUSTOMER_NAME);
		wait.waitForElementDisplayed(INPUT_CUSTOMER_NAME);
		text.enterText(INPUT_CUSTOMER_NAME, custName);
		click.clickElementCarefully(FIRST_OK);
		wait.waitForElementDisplayed(APPLY_BUTTON);
		click.clickElementCarefully(APPLY_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Select the sales order and enter return reason
	 */
	public void selectSalesOrder( )
	{
		wait.waitForElementDisplayed(SELECT_ORDER);
		click.clickElementCarefully(SELECT_ORDER);
		wait.waitForElementDisplayed(RETURN_ORDER);
		click.clickElementCarefully(RETURN_ORDER);
		wait.waitForElementDisplayed(RETURN_BUTTON);
		click.clickElementCarefully(RETURN_BUTTON);
		wait.waitForElementDisplayed(RETURN_BUTTON);
		click.clickElementCarefully(RETURN_BUTTON);
		wait.waitForElementDisplayed(RETURN_OK);
		click.clickElementCarefully(RETURN_OK);
		waitForPageLoad();
	}


}
