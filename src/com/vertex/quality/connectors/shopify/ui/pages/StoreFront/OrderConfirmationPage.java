package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderConfirmationPage extends ShopifyPage
{
	protected By orderConfirmation = By.xpath(".//h2[contains(text(),'Thank you')]");
	protected By orderNoField = By.xpath("//h2[contains(text(),'Thank you')]/parent::div/child::span");
	protected String orderIdInsideOrderDetails = "//span[text()='Paid']/parent::span/ancestor::div/child::h1";

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public OrderConfirmationPage( final WebDriver driver )
	{
		super(driver);
	}

	public void getOrderConFromUI( )
	{
//		waitForPageLoad();
			wait.waitForElementPresent(orderConfirmation);
		String orderConfirm = text.getElementText(orderConfirmation);
		System.out.println(orderConfirm);

		refreshPage();
	}
	public void orderIdFromUi(){
		waitForPageLoad();
		WebElement order = wait.waitForElementPresent(orderNoField);
		String orderId = text.getElementText(orderNoField);
		System.out.println(orderId);
	}
	public String getOrderIdFromUIForAPICall( String orderNo )
		{
			waitForPageLoad();
//			wait.waitForElementPresent(By.xpath(orderIdInsideOrderDetails.replace("<<order_id>>", orderNo)));
			String url = getCurrentUrl();
			int index = url.lastIndexOf("/");
			return url.substring(index + 1);
		}
}

