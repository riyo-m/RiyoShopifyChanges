package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontShippingDiscountPage extends ShopifyPage
{
	protected By shippingDiscount = By.xpath("//input[@placeholder='Discount code or gift card']");
	protected By applyShippingDiscount = By.xpath("//input[@placeholder='Discount code or gift card']/parent::div/parent::div/parent::div/following-sibling::button");
	protected By standardShipping = By.xpath("protected By applyShippingDiscount ");
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontShippingDiscountPage( final WebDriver driver )
	{
		super(driver);
	}

	public void enterShippingDiscountAndApply(String shipping){
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(shippingDiscount),shipping);
		click.clickElement(applyShippingDiscount);
	}
	public void clicksStandardShipping(){
		waitForPageLoad();
		scroll.scrollElementIntoView(standardShipping);
		click.clickElement(standardShipping);
	}

}
