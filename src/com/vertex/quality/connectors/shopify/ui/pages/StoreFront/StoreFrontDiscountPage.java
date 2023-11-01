package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontDiscountPage extends ShopifyPage
{
	protected By addDiscountOnStoreFront = By.xpath("//input[@placeholder='Discount code or gift card']");

	protected By clickApplyDiscount = By.xpath(
		"//input[@placeholder='Discount code or gift card']/parent::div/parent::div/parent::div/following-sibling::button");

//	10DollorDiscount
//	AMOUNT_50_ORDER_OFF
//	FREESHIP2023
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontDiscountPage( final WebDriver driver )
	{
		super(driver);
	}

	public void addDiscountOnStoreFrontAndApply( String discount )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(addDiscountOnStoreFront), discount);
		click.clickElement(clickApplyDiscount);
	}
}
