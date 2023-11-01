package com.vertex.quality.connectors.dynamics365.retail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DRetailHomePage extends DRetailPOSRegisterPage
{

	protected By ORDERS_TO_PICK_UP = By.xpath("//button[@alt='Orders to pick up']");
	protected By ORDERS_TO_SHIP = By.xpath("//button[@alt='Orders to ship']");
	protected By RETURN_TRANSACTION = By.xpath("//button[@alt='Return transaction']");
	protected By LOADING_NOTIF = By.xpath("//div[text()='Loading...']");
	protected By WORKING_NOTIF = By.xpath("//div[text()='Still working on your request...']");

	public DRetailHomePage( WebDriver driver ) { super(driver); }

	/**
	 * Click on button 'Orders to pick up'
	 * @return DRetailOrderFulfillmentPage
	 */
	public DRetailOrderFulfillmentPage goToOrdersToPickUpPage() {
		wait.waitForElementDisplayed(ORDERS_TO_PICK_UP);
		click.clickElementIgnoreExceptionAndRetry(ORDERS_TO_PICK_UP);

		waitForDRetailPageLoad();

		DRetailOrderFulfillmentPage ordersPage = new DRetailOrderFulfillmentPage(driver);
		return ordersPage;
	}

	/**
	 * Click on button 'Orders to ship'
	 * @return DRetailOrderFulfillmentPage
	 */
	public DRetailOrderFulfillmentPage goToOrdersToShipPage() {
		wait.waitForElementDisplayed(ORDERS_TO_SHIP);
		click.clickElementIgnoreExceptionAndRetry(ORDERS_TO_SHIP);

		waitForDRetailPageLoad();

		DRetailOrderFulfillmentPage ordersPage = new DRetailOrderFulfillmentPage(driver);
		return ordersPage;
	}

	public void waitForDRetailPageLoad() {
		waitForPageLoad();

		wait.waitForElementNotDisplayed(WORKING_NOTIF, 100);
		wait.waitForElementNotDisplayed(LOADING_NOTIF, 100);
	}

	/**
	 * Click on button 'Return transaction'
	 * @return DRetailReturnTransactionPage
	 */
	public DRetailReturnTransactionPage goToReturnTransactionPage() {
		wait.waitForElementDisplayed(RETURN_TRANSACTION);
		click.clickElementIgnoreExceptionAndRetry(RETURN_TRANSACTION);

		waitForDRetailPageLoad();

		DRetailReturnTransactionPage returnPage = new DRetailReturnTransactionPage(driver);
		return returnPage;
	}
}
