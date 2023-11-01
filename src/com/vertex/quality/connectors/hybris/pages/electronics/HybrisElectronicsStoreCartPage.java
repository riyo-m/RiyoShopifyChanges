package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the functionalities of Electronics Store Cart Page
 *
 * @author Nagaraju Gampa
 */
public class HybrisElectronicsStoreCartPage extends HybrisBasePage
{
	public HybrisElectronicsStoreCartPage( WebDriver driver )
	{
		super(driver);
	}

	protected By REMOVE_ITEM = By.cssSelector("[class='glyphicon glyphicon-option-vertical']");
	protected By REMOVE_BUTTON = By.className("js-execute-entry-action-button");
	protected By CART_EMPTY_MSG = By.cssSelector("[class$='content__empty']");

	/***
	 * method to remove products from cart
	 */
	public void removeItemsFromCart( )
	{
		List<WebElement> remove_elements_list = element.getWebElements(REMOVE_ITEM);
		for ( WebElement remove_element : remove_elements_list )
		{
			wait.waitForElementDisplayed(REMOVE_ITEM);
			remove_element.click();
			wait.waitForElementDisplayed(REMOVE_BUTTON);
			click.clickElement(REMOVE_BUTTON);
			waitForPageLoad();
			remove_elements_list = element.getWebElements(REMOVE_ITEM);
		}
		wait.waitForElementDisplayed(CART_EMPTY_MSG);
	}
}
