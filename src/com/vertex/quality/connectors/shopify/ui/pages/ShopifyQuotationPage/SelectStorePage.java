package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SelectStorePage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public SelectStorePage( final WebDriver driver )
	{
		super(driver);
	}
	protected By searchStore = By.id(":r2:");
	protected By selectStore = By.xpath("//h6/parent::div");
	public void searchNewStore( String store )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchStore), store);
	}

	public void selectNewStore( )
	{
		waitForPageLoad();
		click.clickElement(selectStore);
	}
}
