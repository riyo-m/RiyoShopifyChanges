package com.vertex.quality.connectors.sitecore.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.List;

public abstract class SitecoreBasePage extends VertexPage
{

	protected By iframeOuter = By.id("jqueryModalDialogsFrame");
	protected By productName = By.className("cart-info");
	public SoftAssert softAssertion;

	public SitecoreBasePage( WebDriver driver )
	{
		super(driver);

		softAssertion = new SoftAssert();

		waitForPageLoad();
	}

	/**
	 * switch frame to outer frame
	 */
	public void switchToOuterFrame( )
	{
		window.switchToDefaultContent();
		window.waitForAndSwitchToFrame(iframeOuter);
	}

	/**
	 * get index of product
	 *
	 * @param itemName name of item to get index of
	 *
	 * @return index of product
	 */
	protected int getProductIndex( final String itemName )
	{
		List<WebElement> productsElements = wait.waitForAllElementsDisplayed(productName);
		int productIndex = element.findElementPositionByText(productsElements, itemName);
		return productIndex;
	}
}
