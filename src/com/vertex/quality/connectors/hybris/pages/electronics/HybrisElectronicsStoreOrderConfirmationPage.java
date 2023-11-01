package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the functionality of Electronics Store Order Confirmation page and get the Order
 * Number
 *
 * @author Nagaraju Gampa
 */
public class HybrisElectronicsStoreOrderConfirmationPage extends HybrisBasePage
{
	public HybrisElectronicsStoreOrderConfirmationPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ORDER_NUMBER = By.cssSelector("[class='checkout-success__body']> div + p > b");

	/***
	 * Method to get Order Number from Order Confirmation Page
	 *
	 * @return order number
	 */
	public String getOrderNumber( )
	{
		wait.waitForElementDisplayed(ORDER_NUMBER);
		final String orderNumber = attribute.getElementAttribute(ORDER_NUMBER, "textcontent");
		return orderNumber;
	}
}
