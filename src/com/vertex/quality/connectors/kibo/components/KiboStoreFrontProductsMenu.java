package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.enums.KiboProductCategory;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This class represents the ProductNames tabs in the live Maxine front store
 * contains methods to interact with each tab and  to select various ProductNames
 *
 * @author osabha
 */
public class KiboStoreFrontProductsMenu extends VertexComponent
{
	protected By categoryTabLoc = By.className("mz-sitenav-item-inner");
	protected By productContainerLoc = By.className("mz-productlist-item");
	protected By productLoc = By.tagName("img");
	String attributeName = "data-mz-product";

	public KiboStoreFrontProductsMenu( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * This is a getter methods used to get elements of the product tabs to interact with them
	 *
	 * @param productTabText String that determines which tab to grab
	 *
	 * @return desired tab element to interact with
	 */
	protected WebElement getProductTab( String productTabText )
	{
		WebElement productCategoryButton = null;

		List<WebElement> categoryButtonContainers = wait.waitForAllElementsPresent(categoryTabLoc);
		productCategoryButton = element.selectElementByText(categoryButtonContainers, productTabText);
		return productCategoryButton;
	}

	/**
	 * Uses the getter method to locate the desired tab based on the parameter passed
	 * and then clicks on it
	 *
	 * @param category String to pass to the getter method
	 */
	public void selectProductTab( KiboProductCategory category )
	{
		String categoryName = category.value;
		WebElement categoryButton = getProductTab(categoryName);
		categoryButton.click();
		waitForPageLoad();
	}

	/**
	 * This is a getter method used to locate the desired product from the list
	 *
	 * @param productName String
	 *
	 * @return element of the desired product
	 */
	protected WebElement getProduct( String productName )
	{
		WebElement product = null;

		List<WebElement> productContainers = wait.waitForAllElementsPresent(productContainerLoc);
		WebElement thisProductContainer = element.selectElementByAttribute(productContainers, productName,
			attributeName);
		if ( thisProductContainer != null )
		{
			product = wait.waitForElementPresent(productLoc, thisProductContainer);
		}

		return product;
	}

	/**
	 * Uses a getter method to locate the  desired product and then clicks on it
	 *
	 * @param productName String gets passed to the getter method to locate the desired product from the
	 *                    list
	 */
	public void selectProduct( KiboProductNames productName )
	{
		String name = productName.value;
		WebElement product = getProduct(name);
		product.click();
	}
}
