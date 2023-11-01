package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontProductExemptPage extends ShopifyPage
{
	protected By discountCodeOrDiscountField = By.xpath("//input[@placeholder='Discount code or gift card']");
	protected By applyButton = By.xpath("//span[text()='Apply']/parent::button");
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontProductExemptPage( final WebDriver driver )
	{
		super(driver);
	}
	public void addDiscountCodeOrDiscountBtn(String giftCard){
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(discountCodeOrDiscountField),giftCard);
	}
	public void clickApplyButton(){
		waitForPageLoad();
		click.clickElement(applyButton);
	}
	public void giftCardOffer(){
		waitForPageLoad();
		addDiscountCodeOrDiscountBtn("gift card");
		clickApplyButton();
	}
}
