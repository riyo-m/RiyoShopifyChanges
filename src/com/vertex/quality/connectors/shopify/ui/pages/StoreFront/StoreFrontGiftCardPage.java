package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontGiftCardPage extends ShopifyPage
{
	protected By giftCardStoreFront = By.xpath("//input[@placeholder='Discount code or gift card']");

	protected By clickApplyDiscount = By.xpath(
		"//input[@placeholder='Discount code or gift card']/parent::div/parent::div/parent::div/following-sibling::button");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontGiftCardPage( final WebDriver driver )
	{
		super(driver);
	}

	public void enterGiftCardStoreFrontAndApply(String giftCard){
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(giftCardStoreFront),giftCard);
		click.clickElement(clickApplyDiscount);

	}
}
