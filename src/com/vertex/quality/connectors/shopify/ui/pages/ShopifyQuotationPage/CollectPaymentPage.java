package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class CollectPaymentPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public CollectPaymentPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By collectPayment = By.xpath("//span[text()='Collect payment']");
	protected By markAsPaid = By.xpath("//span[text()='Mark as paid']");
	protected By createPaidOrder = By.xpath("//span[text()='Create order']");

	public void clickCollectPayment( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		scroll.scrollElementIntoView(collectPayment);
		click.clickElement(collectPayment);
	}

	public void clickMarkPaid( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(markAsPaid);
	}

	public void clickCreatePaidOrder( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(5000);
		click.clickElement(createPaidOrder);
	}

	public void collectPaymentPage( ) throws InterruptedException
	{
		clickCollectPayment();
		clickMarkPaid();
		clickCreatePaidOrder();
	}
}
